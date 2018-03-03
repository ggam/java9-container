package eu.ggam.servlet.impl.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author guillermo
 */
public class ServletWorkerThreadFactory implements ThreadFactory {

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("servlet-worker");
    private static final AtomicInteger THREAD_COUNT = new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r, "http-worker-" + THREAD_COUNT.incrementAndGet());

        return thread;
    }
}