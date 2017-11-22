module es.guillermogonzalezdeaguero.container.ExampleModule {
    requires es.guillermogonzalezdeaguero.container.Api;

    provides es.guillermogonzalezdeaguero.container.api.Plugin
            with es.guillermogonzalezdeaguero.container.exampleplugin.ExamplePlugin;
}
