package es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml;

/**
 *
 * @author guillermo
 */
public class WebXmlProcessingException extends RuntimeException {

    public WebXmlProcessingException(String message) {
        super(message);
    }
    public WebXmlProcessingException(Throwable t) {
        super(t);
    }

}
