package es.guillermogonzalezdeaguero.container.impl.internal.webxml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
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

    public static Map<String, String> getServletMappings(String contextPath, Path webXmlPath) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().
                    newDocumentBuilder().
                    parse(webXmlPath.toFile());

            NodeList servletList = doc.getElementsByTagName("servlet");

            Map<String, String> servletClasses = new HashMap<>();
            for (int i = 0; i < servletList.getLength(); i++) {

                Element servletElement = (Element) servletList.item(i);
                String servletName = servletElement.getElementsByTagName("servlet-name").item(0).getTextContent();
                String servletClass = servletElement.getElementsByTagName("servlet-class").item(0).getTextContent();

                servletClasses.put(servletName, servletClass);
            }

            Map<String, String> servletMappings = new HashMap<>();

            NodeList mappingList = doc.getElementsByTagName("servlet-mapping");
            for (int i = 0; i < mappingList.getLength(); i++) {

                Element servletMappingElement = (Element) mappingList.item(i);
                String servletName = servletMappingElement.getElementsByTagName("servlet-name").item(0).getTextContent();

                NodeList urlPatterns = servletMappingElement.getElementsByTagName("url-pattern");
                for (int j = 0; j < urlPatterns.getLength(); j++) {
                    String servletClass = servletClasses.get(servletName);
                    if (servletClass == null) {
                        throw new IllegalStateException("No Servlet class for mapping: " + servletName);
                    }

                    String urlPattern = urlPatterns.item(j).getTextContent();
                    servletMappings.put(contextPath + urlPattern, servletClass);
                }

            }

            return servletMappings;

        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new WebXmlParsingException(e);
        }
    }
}
