module eu.ggam.container.Impl {
    requires java.logging;
    requires eu.ggam.container.Api;
    
    uses eu.ggam.container.api.event.ServerLifeCycleListener;
    uses eu.ggam.container.api.http.HttpRequestHandler;
}
