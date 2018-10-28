package eu.ggam.container.impl.servletcontainer.core;

import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.impl.servletcontainer.deployer.ServletDeployment;
import eu.ggam.container.impl.util.NamedThreadFactory;
import eu.ggam.jlink.launcher.spi.WebAppModule;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletEngine {

    private ExecutorService servletInitializerExecutor;
    private ExecutorService requestExecutor;

    private final WebAppModule module;
    private ServletDeployment deployment;
    private ServletRequestHandler requestHandler;

    public ServletEngine(WebAppModule module) {
        this.module = module;
    }

    public CompletableFuture<HttpRequestHandler> start() {
        servletInitializerExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("servlet-initializer-pool"));
        requestExecutor = new ServletRequestExecutorService();

        return CompletableFuture.supplyAsync(() -> {
            deployment = new ServletDeployment.ServletDeploymentBuilder(module).
                    deploy();
            requestHandler = new ServletRequestHandler(requestExecutor, deployment);
            return requestHandler;
        }, servletInitializerExecutor);
    }

    public HttpRequestHandler getRequestHandler() {
        return requestHandler;
    }

    public void stop() {
        requestExecutor.shutdown();
        if (deployment != null) {
            // Deployment might have failed
            deployment.undeploy();
        }
    }
}
