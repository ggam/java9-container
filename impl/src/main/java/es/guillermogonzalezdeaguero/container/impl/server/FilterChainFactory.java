package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.api.ServletDeployment;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;

/**
 *
 * @author guillermo
 */
public class FilterChainFactory {

    private final Set<ServletDeployment> webApps;

    public FilterChainFactory(Set<ServletDeployment> webApps) {
        this.webApps = new HashSet<>(webApps);
    }

    public FilterChain match(String url) {
        // There will always be at least a root application
        return webApps.stream().
                filter(app -> app.matches(url)).
                findAny().
                get().createFilterChain(url);
    }
}
