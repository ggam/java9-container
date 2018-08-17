module eu.ggam.container.Impl {
    requires java.logging;
    requires eu.ggam.container.Api;
    requires java.activation;
    requires java.servlet;
    requires java.xml.bind;

    opens eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee to java.xml.bind;

    uses eu.ggam.container.api.event.ServerLifeCycleListener;
    uses eu.ggam.container.api.http.HttpRequestHandler;

    provides eu.ggam.container.api.event.ServerLifeCycleListener
            with eu.ggam.container.impl.servletcontainer.core.ServletContainer;
    provides eu.ggam.container.api.http.HttpRequestHandler
            with eu.ggam.container.impl.servletcontainer.core.ServletContainer;
}
