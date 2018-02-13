package eu.ggam.container.impl;

import eu.ggam.container.api.Deployment;
import eu.ggam.container.api.deployment.DeploymentRegistry;
import eu.ggam.container.api.http.HttpMessageExchange;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class RequestHandler {

    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());
    
    private final DeploymentRegistry deploymentRegistry;

    public RequestHandler(DeploymentRegistry deploymentRegistry) {
        this.deploymentRegistry = deploymentRegistry;
    }

    public void handleRequest(HttpMessageExchange httpMessage) throws IOException {
        try {
            Deployment deployment = deploymentRegistry.getDeployments().
                    stream().
                    filter(app -> app.matches(httpMessage.getRequestUri().getPath())).
                    max(Comparator.comparingInt((app) -> app.getContextPath().length())).
                    get();

            deployment.process(httpMessage);
        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "Error processing request: ", e);
            StringWriter stackTrace = new StringWriter();
            PrintWriter printer = new PrintWriter(stackTrace);
            e.printStackTrace(printer);
            httpMessage.setResponseStatus(500);
            httpMessage.getResponseHeaders().put("Content-Type", List.of("text/html"));
            httpMessage.getResponseHeaders().put("Server-name", List.of("localhost"));
            httpMessage.getOutputStream().write(("Error processing request: " + stackTrace.toString().replaceAll("\n", "<br>")).getBytes());
        }
    }
}
