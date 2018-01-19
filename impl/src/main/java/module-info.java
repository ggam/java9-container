module es.guillermogonzalezdeaguero.container.Impl {
    requires java.logging;
    requires java.servlet;
    requires es.guillermogonzalezdeaguero.container.Api;
    requires java.corba;
    
    uses es.guillermogonzalezdeaguero.container.api.event.ServerLifeCycleListener;
}
