module eu.ggam.container.integrationtests.Impl {
    requires eu.ggam.container.Api;

    provides eu.ggam.container.api.http.HttpRequestHandler
            with eu.ggam.container.integrationtests.impl.BrokenRequestHandler;
}
