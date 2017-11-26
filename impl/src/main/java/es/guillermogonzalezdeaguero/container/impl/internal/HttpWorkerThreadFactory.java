package es.guillermogonzalezdeaguero.container.impl.internal;

import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class HttpWorkerThreadFactory implements ThreadFactory {

    private static final Logger LOGGER = Logger.getLogger(HttpWorkerThreadFactory.class.getName());

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "http-worker");
        thread.setUncaughtExceptionHandler((Thread t, Throwable e) -> LOGGER.log(Level.SEVERE, "Error on thread thread {0}: {1}", new Object[]{t, e}));

        return thread;
    }

}
