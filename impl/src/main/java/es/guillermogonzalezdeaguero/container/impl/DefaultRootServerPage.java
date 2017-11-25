package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.api.ServerPage;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public class DefaultRootServerPage implements ServerPage {

    private final String responseBody;

    public DefaultRootServerPage(Set<String> validUrls) {
        StringBuilder sb = new StringBuilder();
        sb.append("Valid urls:").append("\n<ul>");
        for (String validUrl : validUrls) {
            sb.append("<li><a href=\"").append(validUrl).append("\">").append(validUrl).append("</a></li>\n");
        }
        sb.append("</ul>");

        responseBody = sb.toString();
    }

    @Override
    public String getUrl() {
        return "/";
    }

    @Override
    public String process() {
        return responseBody;
    }

}
