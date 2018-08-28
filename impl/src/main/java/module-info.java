module eu.ggam.container.Impl {
    requires java.logging;
    requires eu.ggam.container.Api;
    requires java.activation;
    requires java.servlet;
    requires java.xml.bind;
    requires eu.ggam.jlink.launcher;

    opens eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee to java.xml.bind;

    exports eu.ggam.container.impl;
    
    provides eu.ggam.jlink.launcher.spi.ServerLauncher
            with eu.ggam.container.impl.Launcher;
}
