package eu.ggam.container.api;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author guillermo
 */
public interface Deployment {

    void deploy();

    boolean matches(String url);

    void process(InputStream input, OutputStream output) throws Exception;
    
    String getContextPath();
}
