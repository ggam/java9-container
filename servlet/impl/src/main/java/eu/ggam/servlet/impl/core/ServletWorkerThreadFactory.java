package eu.ggam.servlet.impl.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class ServletWorkerThreadFactory implements ThreadFactory {

    private static final Thread.UncaughtExceptionHandler EXCEPTION_HANDLER = new UncaughtExceptionHandler();
    
    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("servlet-worker");
    private static final AtomicInteger THREAD_COUNT= new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r, "http-worker-" + THREAD_COUNT.incrementAndGet());
        thread.setUncaughtExceptionHandler(EXCEPTION_HANDLER);

        return thread;
    }

    public static class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        private static final Logger LOGGER = Logger.getLogger(UncaughtExceptionHandler.class.getName());

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            LOGGER.log(Level.SEVERE, String.format("Error on thread thread %s", t.getName()), e);
        }
    }

}
