module eu.ggam.container.Impl {
    requires java.logging;
    requires eu.ggam.container.Api;
    requires java.servlet;
    requires eu.ggam.jlink.launcher;
    requires java.xml;

    exports eu.ggam.container.impl;
    
    provides eu.ggam.jlink.launcher.spi.ServerLauncher
            with eu.ggam.container.impl.Launcher;
}
