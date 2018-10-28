package eu.ggam.container.impl.servletcontainer.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletWorkerThreadFactory implements ThreadFactory {

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("servlet-worker");
    private static final AtomicInteger THREAD_COUNT = new AtomicInteger();

    private static final ServletWorkerThreadFactory INSTANCE = new ServletWorkerThreadFactory();

    private ServletWorkerThreadFactory() {
    }

    public static ServletWorkerThreadFactory instance() {
        return INSTANCE;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r, "http-worker-" + THREAD_COUNT.incrementAndGet());

        return thread;
    }
}
