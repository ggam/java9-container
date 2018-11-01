package eu.ggam.container.impl.servletcontainer.core;

import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.impl.servletcontainer.deployer.ServletDeployment;
import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.WebXml;
import eu.ggam.container.impl.util.NamedThreadFactory;
import eu.ggam.jlink.launcher.spi.WebAppModule;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletEngine {

    private static final String WEB_XML_LOCATION = "_ROOT_/WEB-INF/web.xml";

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
            WebXml parsedWebXml = parseWebXml(module);

            deployment = new ServletDeployment(module, parsedWebXml);
            deployment.deploy();
            requestHandler = new ServletRequestHandler(requestExecutor, deployment);
            return requestHandler;
        }, servletInitializerExecutor);
    }

    public HttpRequestHandler getRequestHandler() {
        return requestHandler;
    }

    public void stop() {
        if (requestExecutor != null) {
            requestExecutor.shutdown();
        }

        if (servletInitializerExecutor != null) {
            servletInitializerExecutor.shutdown();
        }

        if (deployment != null) {
            // Deployment might have failed
            deployment.undeploy();
        }
    }

    private WebXml parseWebXml(WebAppModule webModule) {
        try (ModuleReader reader = webModule.getModule().getLayer().configuration().findModule(webModule.getModule().getName()).get().reference().open();
                InputStream is = reader.open(WEB_XML_LOCATION).get();
                BufferedInputStream bis = new BufferedInputStream(is)) {

            return new WebXml(bis);
        } catch (IOException | XMLStreamException e) {
            throw new WebXmlProcessingException(e);
        }
    }
}
