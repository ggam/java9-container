module es.guillermogonzalezdeaguero.container.ExamplePlugin {
    requires es.guillermogonzalezdeaguero.container.Api;

    provides es.guillermogonzalezdeaguero.container.api.ServerPage
            with es.guillermogonzalezdeaguero.container.exampleplugin.ExamplePage;
}
