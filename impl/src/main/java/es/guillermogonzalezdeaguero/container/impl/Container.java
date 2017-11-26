package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.impl.internal.WebApplication;
import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletRequestImpl;
import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletResponseImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.util.stream.Collectors.toSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author guillermo
 */
public class Container {

    private static final int PORT = 8282;
    private final Set<WebApplication> webApps;
    //private final Map<String, ServerPage> pages;

    public Container(ModuleLayer parentLayer, Path pluginPath) {
        try {
            webApps = Files.list(pluginPath).
                    filter(Files::isDirectory).
                    map(p -> new WebApplication(parentLayer, p)).
                    collect(toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n=============");
        System.out.println("Available applications:");
        webApps.stream().map(WebApplication::getContextPath).forEach(System.out::println);
        System.out.println("=============\n");

        //pages = resolvePages(wars.values());
    }

    public void startServer() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Started server at port " + PORT);
        while (true) {
            Socket request = serverSocket.accept();
            handleRequest(request);
        }
    }

    private void handleRequest(Socket request) {
        try (request;
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                PrintWriter out = new PrintWriter(request.getOutputStream(), true)) {

            String url = in.readLine().split("GET ")[1].split(" HTTP")[0];

            Optional<HttpServlet> findAny = webApps.stream().
                    map(webApp -> webApp.getServlet(url)).
                    filter(servlet -> servlet != null).
                    findAny();

            if (!findAny.isPresent()) {
                out.print(createResponse(404, "404 - Not found"));
                System.out.println("Requested URL " + url + " - Status: 404");
            } else {
                HttpServletResponseImpl response = new HttpServletResponseImpl(out);
                findAny.get().service(new HttpServletRequestImpl(), response);
                System.out.println("Requested URL " + url + " - Status: " + response.getStatus());
            }

        } catch (IOException | ServletException e) {
            System.err.println("Error responding to request");
            e.printStackTrace();
        }
    }

    private String createResponse(int statusCode, String responseBody) {
        return "HTTP/1.1 " + statusCode + "\n"
                + "Content-type: text/html\n"
                + "Server-name: localhost\n"
                + "Content-length: " + responseBody.length() + "\n"
                + "\n"
                + responseBody;
    }
}
