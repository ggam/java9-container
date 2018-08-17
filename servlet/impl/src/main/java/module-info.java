module eu.ggam.container.servlet.Impl {
    requires java.activation;
    requires java.servlet;
    requires java.logging;
    requires java.xml.bind;
    requires eu.ggam.container.Api;

    opens eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee to java.xml.bind;

    provides eu.ggam.container.api.event.ServerLifeCycleListener
            with eu.ggam.servlet.impl.deployer.DeploymentScanner;
    provides eu.ggam.container.api.http.HttpRequestHandler
            with eu.ggam.servlet.impl.core.ServletContainer;
}
