module es.guillermogonzalezdeaguero.container.ConflictivePlugin {
    requires es.guillermogonzalezdeaguero.container.Api;

    provides es.guillermogonzalezdeaguero.container.api.Plugin
            with es.guillermogonzalezdeaguero.container.conflictiveplugin.ConflictivePlugin;
}
