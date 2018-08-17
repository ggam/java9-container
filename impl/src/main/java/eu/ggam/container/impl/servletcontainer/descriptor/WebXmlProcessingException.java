package eu.ggam.container.impl.servletcontainer.descriptor;

/**
 *
 * @author Guillermo González de Agüero
 */
public class WebXmlProcessingException extends RuntimeException {

    public WebXmlProcessingException(String message, Throwable t) {
        super(message, t);
    }

    public WebXmlProcessingException(String message) {
        super(message);
    }

    public WebXmlProcessingException(Throwable t) {
        super(t);
    }

}
