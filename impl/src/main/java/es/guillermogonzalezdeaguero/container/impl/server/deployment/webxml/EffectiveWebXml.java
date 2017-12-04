package es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml;

import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.ServletDescriptor;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public class EffectiveWebXml {

    public Set<ServletDescriptor> servletDescriptors;
    public Set<FilterDescriptor> filterDescriptors;

    public EffectiveWebXml(Set<ServletDescriptor> servletDescriptors, Set<FilterDescriptor> filterDescriptors) {
        this.servletDescriptors = servletDescriptors;
        this.filterDescriptors = filterDescriptors;
    }

    public Set<ServletDescriptor> getServletDescriptors() {
        return servletDescriptors;
    }

    public Set<FilterDescriptor> getFilterDescriptors() {
        return filterDescriptors;
    }

}
