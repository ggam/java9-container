package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.deployment.DeploymentRegistry;
import es.guillermogonzalezdeaguero.container.impl.servlet.FilterChainFactory;
import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletResponseImpl;
import es.guillermogonzalezdeaguero.container.impl.servlet.PreMatchingHttpServletRequestImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.ArrayList;
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
public class RequestProcessor {

    private static final Logger LOGGER = Logger.getLogger(RequestProcessor.class.getName());

    private final DeploymentRegistry deploymentRegistry;
    private final FilterChainFactory filterChainFactory;

    public RequestProcessor(DeploymentRegistry deploymentRegistry) {
        this.deploymentRegistry = deploymentRegistry;
        this.filterChainFactory = new FilterChainFactory(deploymentRegistry);
    }

    public void process(InputStream input, OutputStream output) throws IOException, ServletException {;
        PreMatchingHttpServletRequestImpl servletRequest = createServletRequest(input);

        FilterChain filterChain = filterChainFactory.match(servletRequest.getRequestURI());

        HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();
        filterChain.doFilter(servletRequest, servletResponse);

        sendResponse(servletResponse, output);

        LOGGER.log(Level.INFO, "Requested URL {0} - Status: {1}", new Object[]{servletRequest.getRequestURI(), servletResponse.getStatus()});
    }
    
    
    private PreMatchingHttpServletRequestImpl createServletRequest(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
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

            return new PreMatchingHttpServletRequestImpl(method, uri, headers);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

    }
    
    public void sendResponse(HttpServletResponseImpl response, OutputStream output) {
        PrintWriter writer = new PrintWriter(output, true);
        writer.append("HTTP/1.1 " + response.getStatus() + " Unknown\r\n\r\n");
        if (response.isErrorSent() && response.getStatusMessage() != null) {
            writer.append(response.getStatusMessage());
        } else {
            writer.append(response.getStringWriter().toString());
        }

    }
}
