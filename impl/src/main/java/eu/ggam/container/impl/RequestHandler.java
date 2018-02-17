package eu.ggam.container.impl;

import eu.ggam.container.api.Deployment;
import eu.ggam.container.api.deployment.DeploymentRegistry;
import eu.ggam.container.impl.http.HttpRequestImpl;
import eu.ggam.container.impl.http.HttpResponseImpl;
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

    public void handle(HttpRequestImpl request, HttpResponseImpl response) throws IOException {
        try {
            Deployment deployment = deploymentRegistry.getDeployments().
                    stream().
                    filter(app -> app.matches(request.getUri().getPath())).
                    max(Comparator.comparingInt((app) -> app.getContextPath().length())).
                    get();

            deployment.process(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request: ", e);

            StringWriter stackTrace = new StringWriter();
            PrintWriter printer = new PrintWriter(stackTrace);
            e.printStackTrace(printer);
            response.setStatus(500);

            response.getHeaders().put("Content-Type", List.of("text/html"));
            response.getHeaders().put("Server-name", List.of("localhost"));

            byte[] body = ("Error processing request: " + stackTrace.toString().replaceAll("\n", "<br>")).getBytes();
            response.getHeaders().put("Content-Length", List.of(String.valueOf(body.length)));

            response.getOutputStream().write(body);
        }
        
        response.complete(); // Move from the intermediate buffer to the connection one
    }
}
