package eu.ggam.container.impl.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class NamedThreadFactory implements ThreadFactory {

    private final String namePrefix;
    private final AtomicInteger THREAD_COUNT = new AtomicInteger();

    public NamedThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread newThread = Executors.defaultThreadFactory().newThread(r);
        newThread.setName(namePrefix + THREAD_COUNT.incrementAndGet());
        
        return newThread;
    }

}
