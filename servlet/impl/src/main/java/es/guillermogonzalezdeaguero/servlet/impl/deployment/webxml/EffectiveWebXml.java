package es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author guillermo
 */
public class EffectiveWebXml {

    public Set<ServletDescriptor> servletDescriptors;
    public TreeSet<FilterDescriptor> filterDescriptors;

    public EffectiveWebXml(Set<ServletDescriptor> servletDescriptors, TreeSet<FilterDescriptor> filterDescriptors) {
        this.servletDescriptors = servletDescriptors;
        this.filterDescriptors = filterDescriptors;
    }

    public Set<ServletDescriptor> getServletDescriptors() {
        return servletDescriptors;
    }

    public TreeSet<FilterDescriptor> getFilterDescriptors() {
        return filterDescriptors;
    }

}
