package eu.ggam.servlet.impl.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletRequestExecutorService extends ThreadPoolExecutor {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final Logger LOGGER = Logger.getLogger(ServletRequestExecutorService.class.getName());

    public ServletRequestExecutorService() {
        super(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ServletWorkerThreadFactory());
    }

}
