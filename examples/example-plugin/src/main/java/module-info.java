module es.guillermogonzalezdeaguero.container.ExamplePlugin {
    requires es.guillermogonzalezdeaguero.container.Api;

    provides es.guillermogonzalezdeaguero.container.api.Plugin
            with es.guillermogonzalezdeaguero.container.exampleplugin.ExamplePlugin;
}
