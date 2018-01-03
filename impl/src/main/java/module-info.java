module es.guillermogonzalezdeaguero.container.Impl {
    requires java.activation;
    requires java.logging;
    requires java.xml.bind;
    requires java.servlet;
    
    opens com.sun.java.xml.ns.javaee to java.xml.bind;
}
