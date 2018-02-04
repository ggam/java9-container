package eu.ggam.container.impl.server;

import eu.ggam.container.api.Deployment;
import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.impl.deployment.DeploymentRegistryImpl;
import eu.ggam.container.impl.internal.ByteBufferInputStream;
import eu.ggam.container.impl.internal.ByteBufferOutputStream;
import eu.ggam.container.impl.server.event.ServerStartedEventImpl;
import eu.ggam.container.impl.server.event.ServerStartingEventImpl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private ServerState state = ServerState.STOPPED;
    private final int port;
    private final List<ServerLifeCycleListener> lifeCycleListeners;

    // Server already running
    private final DeploymentRegistryImpl deploymentRegistry = new DeploymentRegistryImpl();

    public Server(int port) {
        this.port = port;

        this.lifeCycleListeners = new ArrayList<>();
        ServiceLoader.load(ServerLifeCycleListener.class).
                iterator().
                forEachRemaining(lifeCycleListeners::add);
    }

    private synchronized void changeState(ServerState newState) {
        this.state = newState;

        switch (newState) {
            case STARTING:
                ServerStartingEvent startingEvent = new ServerStartingEventImpl(this, deploymentRegistry);
                lifeCycleListeners.forEach(listener -> listener.serverStarting(startingEvent));
                break;
            case RUNNING:
                ServerStartedEvent startedEvent = new ServerStartedEventImpl(this);
                lifeCycleListeners.forEach(listener -> listener.serverStarted(startedEvent));
                break;
        }

        LOGGER.log(Level.INFO, "Server state changed from {0} to {1}", new Object[]{this.state, newState});
    }

    public void start() throws IOException {
        changeState(ServerState.STARTING);

        //ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new HttpWorkerThreadFactory());

        deploymentRegistry.deployAll();

        changeState(ServerState.RUNNING);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        try (final Selector selector = Selector.open();
                ServerSocketChannel serverSocket = ServerSocketChannel.open()) {

            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            LOGGER.log(Level.INFO, "Listening on port {0}", String.valueOf(port));
            while (true) {
                if (selector.select() != 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (key.isAcceptable()) {
                            SocketChannel clientSocket = ((ServerSocketChannel) key.channel()).accept();
                            clientSocket.configureBlocking(false);
                            clientSocket.register(selector, SelectionKey.OP_READ, new ArrayList<ByteBuffer>());
                        } else if (key.isReadable()) {
                            SocketChannel clientSocket = (SocketChannel) key.channel();
                            int inputBufferSize = clientSocket.socket().getReceiveBufferSize();
                            int outputBufferSize = clientSocket.socket().getSendBufferSize();
                            ByteBuffer allocate = ByteBuffer.allocate(clientSocket.socket().getReceiveBufferSize());
                            int read = clientSocket.read(allocate);

                            List<ByteBuffer> list = (List<ByteBuffer>) key.attachment();
                            list.add(allocate);

                            if (read < inputBufferSize) {
                                String lastRead = new String(allocate.array());
                                int length = lastRead.length();
                                if ("".equals(lastRead.substring(length - 3, length - 1).trim())) {
                                    ByteBufferOutputStream output = new ByteBufferOutputStream(outputBufferSize);
                                    ByteBufferInputStream input = new ByteBufferInputStream(list);

                                    handleRequest(input, output);
                                    clientSocket.register(selector, SelectionKey.OP_WRITE, output);
                                }
                            }
                        } else if (key.isWritable()) {
                            SocketChannel clientSocket = (SocketChannel) key.channel();

                            ByteBufferOutputStream response = (ByteBufferOutputStream) key.attachment();

                            ByteBuffer orElse = response.getNextBuffer().orElse(null);
                            if (orElse != null) {
                                clientSocket.write(orElse);
                            } else {
                                clientSocket.close();
                                response.close();
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleRequest(InputStream input, OutputStream outputStream) throws IOException {
        PushbackInputStream inputStream = new PushbackInputStream(input, 2000); // 2000 character buffer for URL

        try {
            String readString = "";
            byte[] buffer = new byte[16];
            while (inputStream.read(buffer, 0, buffer.length) != -1) {
                readString += new String(buffer);
                if (readString.contains("\n")) {
                    break;
                }
            }

            inputStream.unread(readString.getBytes());
            String uri = readString.split("\n")[0].split(" ")[1].split("\\?")[0];

            Deployment deployment = deploymentRegistry.getDeployments().
                    stream().
                    filter(app -> app.matches(uri)).
                    max(Comparator.comparingInt((app) -> app.getContextPath().length())).
                    get();

            deployment.process(inputStream, outputStream);
        } catch (Exception e) {
            StringWriter stackTrace = new StringWriter();
            PrintWriter printer = new PrintWriter(stackTrace);
            e.printStackTrace(printer);

            outputStream.write(("HTTP/1.1 500\n"
                    + "Content-type: text/html\n"
                    + "Server-name: localhost\n"
                    + "\n"
                    + "Error processing request: " + stackTrace.toString().replaceAll("\n", "<br>")).getBytes());

            LOGGER.log(Level.SEVERE, "Error processing request: ", e);
        }
    }
}
