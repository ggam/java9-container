package es.guillermogonzalezdeaguero.container.api;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;

/**
 *
 * @author guillermo
 */
public interface ServletDeployment {

    void deploy();

    boolean matches(String url);
    
    FilterChain createFilterChain(String url);
    
    ServletContext getServletContext();
}
