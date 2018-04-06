package eu.ggam.servlet.impl.jsr154;

import eu.ggam.servlet.impl.descriptor.MatchingPattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guillermo
 */
public class HttpServletRequestImpl implements HttpServletRequest {

    private final ServletContext servletContext;
    private String authType;
    private Cookie[] cookies;
    private final String method;
    private String pathInfo;
    private String queryString;
    private Map<String, List<String>> queryParams = Collections.emptyMap();
    private String remoteUser;
    private Principal userPrincipal;
    private String requestedSessionId;
    private final String requestURI;
    private StringBuffer requestURL;
    private String servletPath;
    private HttpSession httpSession;
    private final Map<String, List<String>> headers;

    private Map<String, Object> attributes;
    private final int port;
    private final String scheme;
    
    public HttpServletRequestImpl(ServletContext servletContext, String method, URI uri, MatchingPattern.UriMatch uriMatch, Map<String, List<String>> headers) {
        this.servletContext = servletContext;
        this.method = method;
        this.requestURI = uri.getPath();
        this.headers = new HashMap<>(headers);
        this.servletPath = uriMatch.getServletPath();
        this.pathInfo = uriMatch.getPathInfo();
        this.queryString = uri.getQuery();

        // https://stackoverflow.com/a/37368660
        if (queryString != null) {
            queryParams = Pattern.compile("&").splitAsStream(queryString)
                    .map(s -> Arrays.copyOf(s.split("="), 2))
                    .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));
        }
        
        this.attributes = new ConcurrentHashMap<>();
        this.port = uri.getPort();
        this.scheme = uri.getScheme();
    }

    private String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Impossible: UTF-8 is a required encoding", e);
        }
    }

    @Override
    public String getAuthType() {
        return authType;
    }

    @Override
    public Cookie[] getCookies() {
        return cookies;
    }

    @Override
    public long getDateHeader(String name) {
        try {
            return ZonedDateTime.parse(getHeader(name), DateTimeFormatter.RFC_1123_DATE_TIME).toEpochSecond();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("\"" + name + "\" header could not be parsed as date", e);
        }
    }

    @Override
    public String getHeader(String name) {
        List<String> values = headers.get(name);
        if (values == null || values.isEmpty()) {
            return null;
        }

        return values.get(0);
    }

    @Override
    public Enumeration getHeaders(String name) {
        List<String> values = headers.get(name);
        if (values == null || values.isEmpty()) {
            return Collections.emptyEnumeration();
        }

        return Collections.enumeration(values);
    }

    @Override
    public Enumeration getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    @Override
    public int getIntHeader(String name) {
        String value = getHeader(name);

        if (value == null) {
            return -1;
        }

        return Integer.valueOf(value);
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return servletContext.getContextPath();
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getRemoteUser() {
        return remoteUser;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    @Override
    public String getRequestedSessionId() {
        return requestedSessionId;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return requestURL;
    }

    @Override
    public String getServletPath() {
        return servletPath;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return httpSession;
    }

    @Override
    public HttpSession getSession() {
        return getSession(false);
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public String getCharacterEncoding() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getContentLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getParameter(String name) {
        String[] parameterValues = getParameterValues(name);
        if (parameterValues != null && parameterValues.length > 0) {
            return parameterValues[0];
        }

        return null;
    }

    @Override
    public Enumeration getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        // TODO: check for POST parameters
        List<String> parameters = (List<String>) getParameterMap().get(name);
        return parameters != null ? parameters.toArray(new String[parameters.size()]) : null;
    }

    @Override
    public Map getParameterMap() {
        return new HashMap<>(queryParams);
    }

    @Override
    public String getProtocol() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public String getServerName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getServerPort() {
        return port;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRemoteAddr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRemoteHost() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAttribute(String name, Object o) {
        attributes.put(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enumeration getLocales() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSecure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRealPath(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getRemotePort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLocalName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLocalAddr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getLocalPort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
