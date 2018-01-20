module es.guillermogonzalezdeaguero.container.servlet.Impl {
    requires java.activation;
    requires java.servlet;
    requires java.logging;
    requires java.xml.bind;
    requires es.guillermogonzalezdeaguero.container.Api;

    opens com.sun.java.xml.ns.javaee to java.xml.bind;

    provides es.guillermogonzalezdeaguero.container.api.event.ServerLifeCycleListener
            with es.guillermogonzalezdeaguero.servlet.impl.deployment.deployment.DeploymentScanner;
}
