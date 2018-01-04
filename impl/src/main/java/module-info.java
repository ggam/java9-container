module es.guillermogonzalezdeaguero.container.Impl {
    requires java.activation;
    requires java.logging;
    requires java.xml.bind;
    requires java.servlet;
    requires java.prefs;
    requires es.guillermogonzalezdeaguero.container.SystemWebappLib;

    opens com.sun.java.xml.ns.javaee to java.xml.bind;
}
