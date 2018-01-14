package es.guillermogonzalezdeaguero.container.api;

import javax.servlet.FilterChain;

/**
 *
 * @author guillermo
 */
public interface ServletDeployment {

    void deploy();

    boolean matches(String url);
    
    FilterChain createFilterChain(String url);
}
