module es.guillermogonzalezdeaguero.container.Impl {
    requires java.logging;
    requires java.servlet;
    requires es.guillermogonzalezdeaguero.container.Api;
    
    uses es.guillermogonzalezdeaguero.container.api.event.ServerLifeCycleListener;
    uses es.guillermogonzalezdeaguero.container.api.RequestProcessor;
}
