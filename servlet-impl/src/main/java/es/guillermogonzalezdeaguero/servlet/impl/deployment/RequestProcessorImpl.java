package es.guillermogonzalezdeaguero.servlet.impl.deployment;

import es.guillermogonzalezdeaguero.container.api.RequestProcessor;
import es.guillermogonzalezdeaguero.container.api.ServletDeployment;
import es.guillermogonzalezdeaguero.container.api.deployment.DeploymentRegistry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class RequestProcessorImpl implements RequestProcessor {

    private static final Logger LOGGER = Logger.getLogger(RequestProcessorImpl.class.getName());

    private DeploymentRegistry deploymentRegistry;

    @Override
    public void process(InputStream input, OutputStream output) throws IOException, ServletException {
        HttpServletRequestImpl servletRequest = createServletRequest(input);

        FilterChain filterChain = servletRequest.getServletDeployment().
                createFilterChain(servletRequest.getRequestURI());

        HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();
        filterChain.doFilter(servletRequest, servletResponse);

        sendResponse(servletResponse, output);

        LOGGER.log(Level.INFO, "Requested URL {0} - Status: {1}", new Object[]{servletRequest.getRequestURI(), servletResponse.getStatus()});
    }

    private HttpServletRequestImpl createServletRequest(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String[] requestLine = reader.readLine().split(" ");

        String method = requestLine[0];

        String[] uriQueryParams = requestLine[1].split("\\?");
        String uri = uriQueryParams[0];
        if (uriQueryParams.length == 2) {
            String queryParams = uriQueryParams[1]; // TODO: support query params
        }

        String httpVersion = requestLine[2];

        Map<String, List<String>> headers = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            if ("".equals(line)) {
                // Headers are done.
                break;
            }

            String[] header = line.split(":");
            List<String> headerValues = headers.computeIfAbsent(header[0], k -> new ArrayList<>());
            //System.out.println(line);
            headerValues.add(header[1]);
        }

        // TODO: process entity body

        ServletDeployment servletDeployment = deploymentRegistry.getDeployments().
                stream().
                filter(app -> app.matches(uri)).
                max(Comparator.comparingInt((app) -> app.getServletContext().getContextPath().length())).
                get();


        return new HttpServletRequestImpl(servletDeployment, method, uri, headers);
    }

    public void sendResponse(HttpServletResponseImpl response, OutputStream output) throws IOException {
        output.write(("HTTP/1.1 " + response.getStatus() + " Unknown\r\n\r\n").getBytes());
        if (response.isErrorSent() && response.getStatusMessage() != null) {
            output.write(response.getStatusMessage().getBytes());
        } else {
            output.write(response.getStringWriter().toString().getBytes());
        }

    }

    @Override
    public void setDeploymentRegistry(DeploymentRegistry deploymentRegistry) {
        this.deploymentRegistry = deploymentRegistry;
    }
}
