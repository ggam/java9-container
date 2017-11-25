module es.guillermogonzalezdeaguero.container.ConflictivePlugin {
    requires es.guillermogonzalezdeaguero.container.Api;

    provides es.guillermogonzalezdeaguero.container.api.ServerPage
            with es.guillermogonzalezdeaguero.container.conflictiveplugin.ConflictivePage;
}
