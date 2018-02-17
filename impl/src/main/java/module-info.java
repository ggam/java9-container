module eu.ggam.container.Impl {
    requires java.logging;
    requires java.servlet;
    requires eu.ggam.container.Api;
    requires java.xml.ws;
    
    uses eu.ggam.container.api.event.ServerLifeCycleListener;
}
