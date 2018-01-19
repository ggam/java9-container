package es.guillermogonzalezdeaguero.container.impl.servlet;

import es.guillermogonzalezdeaguero.container.impl.deployment.DeploymentRegistry;
import javax.servlet.FilterChain;

/**
 *
 * @author guillermo
 */
public class FilterChainFactory {

    private final DeploymentRegistry deploymentRegistry;

    public FilterChainFactory(DeploymentRegistry deploymentRegistry) {
        this.deploymentRegistry = deploymentRegistry;
    }

    public FilterChain match(String url) {
        // There will always be at least a root application
        return deploymentRegistry.getDeployments().
                stream().
                filter(app -> app.matches(url)).
                findAny().
                get().createFilterChain(url);
    }
}
