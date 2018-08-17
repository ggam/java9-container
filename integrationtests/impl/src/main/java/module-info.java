module eu.ggam.container.integrationtests.Impl {
    requires eu.ggam.container.Api;
    requires java.logging;

    /*provides eu.ggam.container.api.http.HttpRequestHandler
            with eu.ggam.container.integrationtests.impl.BrokenRequestHandler;*/
    provides eu.ggam.container.api.event.ServerLifeCycleListener
            with eu.ggam.container.integrationtests.impl.DummyLifeCycleListener;
}
