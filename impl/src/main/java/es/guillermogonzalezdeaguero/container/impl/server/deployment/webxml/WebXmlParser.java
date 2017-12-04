package es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml;

import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.ServletDescriptor;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author guillermo
 */
public class WebXmlParser {

    public static EffectiveWebXml parse(Path webXmlPath) {

        try {
            Document doc = DocumentBuilderFactory.newInstance().
                    newDocumentBuilder().
                    parse(webXmlPath.toFile());

            return new EffectiveWebXml(findServlets(doc), findFilters(doc));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new WebXmlParsingException(e);
        }

    }

    private static Set<ServletDescriptor> findServlets(Document doc) {
        NodeList servletList = doc.getElementsByTagName("servlet");

        Map<String, String> namesToClasses = new HashMap<>();
        for (int i = 0; i < servletList.getLength(); i++) {

            Element servletElement = (Element) servletList.item(i);
            String servletName = servletElement.getElementsByTagName("servlet-name").item(0).getTextContent();
            String servletClass = servletElement.getElementsByTagName("servlet-class").item(0).getTextContent();

            namesToClasses.put(servletName, servletClass);
        }

        Map<String, Set<String>> namesToUrlPatterns = new HashMap<>();

        NodeList mappingList = doc.getElementsByTagName("servlet-mapping");
        for (int i = 0; i < mappingList.getLength(); i++) {

            Element servletMappingElement = (Element) mappingList.item(i);
            String servletName = servletMappingElement.getElementsByTagName("servlet-name").item(0).getTextContent();

            NodeList urlPatterns = servletMappingElement.getElementsByTagName("url-pattern");
            for (int j = 0; j < urlPatterns.getLength(); j++) {
                String servletClass = namesToClasses.get(servletName);
                if (servletClass == null) {
                    throw new IllegalStateException("No Servlet class for mapping: " + servletName);
                }

                String urlPattern = urlPatterns.item(j).getTextContent();

                Set<String> urlPatternsForServlet = namesToUrlPatterns.computeIfAbsent(servletName, (s) -> new HashSet<>());
                urlPatternsForServlet.add(urlPattern);
            }

        }

        Set<ServletDescriptor> servletDescriptors = new HashSet<>();
        for (Entry<String, String> entry : namesToClasses.entrySet()) {
            String servletName = entry.getKey();
            String servletClass = entry.getValue();

            servletDescriptors.add(new ServletDescriptor(servletName, servletClass, namesToUrlPatterns.getOrDefault(servletName, Collections.emptySet())));
        }

        return servletDescriptors;
    }

    private static Set<FilterDescriptor> findFilters(Document doc) {
        NodeList servletList = doc.getElementsByTagName("filter");

        Map<String, String> namesToClasses = new HashMap<>();
        for (int i = 0; i < servletList.getLength(); i++) {

            Element servletElement = (Element) servletList.item(i);
            String servletName = servletElement.getElementsByTagName("filter-name").item(0).getTextContent();
            String servletClass = servletElement.getElementsByTagName("filter-class").item(0).getTextContent();

            namesToClasses.put(servletName, servletClass);
        }

        Map<String, Set<String>> namesToUrlPatterns = new HashMap<>();

        NodeList mappingList = doc.getElementsByTagName("filter-mapping");
        for (int i = 0; i < mappingList.getLength(); i++) {

            Element servletMappingElement = (Element) mappingList.item(i);
            String servletName = servletMappingElement.getElementsByTagName("filter-name").item(0).getTextContent();

            NodeList urlPatterns = servletMappingElement.getElementsByTagName("url-pattern");
            for (int j = 0; j < urlPatterns.getLength(); j++) {
                String servletClass = namesToClasses.get(servletName);
                if (servletClass == null) {
                    throw new IllegalStateException("No Filter class for mapping: " + servletName);
                }

                String urlPattern = urlPatterns.item(j).getTextContent();

                Set<String> urlPatternsForServlet = namesToUrlPatterns.computeIfAbsent(servletName, (s) -> new HashSet<>());
                urlPatternsForServlet.add(urlPattern);
            }

        }

        Set<FilterDescriptor> filterDescriptors = new HashSet<>();
        for (Entry<String, String> entry : namesToClasses.entrySet()) {
            String filterName = entry.getKey();
            String filterClass = entry.getValue();

            filterDescriptors.add(new FilterDescriptor(filterName, filterClass, namesToUrlPatterns.getOrDefault(filterName, Collections.emptySet()), Collections.emptySet()));
        }

        return filterDescriptors;
    }

}
