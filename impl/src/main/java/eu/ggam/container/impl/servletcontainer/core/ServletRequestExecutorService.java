package eu.ggam.container.impl.servletcontainer.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletRequestExecutorService extends ThreadPoolExecutor {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public ServletRequestExecutorService() {
        super(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), ServletWorkerThreadFactory.instance());
    }

}
