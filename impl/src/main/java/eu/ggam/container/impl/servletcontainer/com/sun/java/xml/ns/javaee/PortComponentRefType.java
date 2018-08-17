//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 
 * 	The port-component-ref element declares a client dependency
 * 	on the container for resolving a Service Endpoint Interface
 * 	to a WSDL port. It optionally associates the Service Endpoint
 * 	Interface with a particular port-component. This is only used
 * 	by the container for a Service.getPort(Class) method call.
 * 
 *       
 * 
 * <p>Clase Java para port-component-refType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="port-component-refType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="service-endpoint-interface" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType"/&gt;
 *         &lt;element name="enable-mtom" type="{http://java.sun.com/xml/ns/javaee}true-falseType" minOccurs="0"/&gt;
 *         &lt;element name="port-component-link" type="{http://java.sun.com/xml/ns/javaee}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "port-component-refType", propOrder = {
    "serviceEndpointInterface",
    "enableMtom",
    "portComponentLink"
})
public class PortComponentRefType {

    @XmlElement(name = "service-endpoint-interface", required = true)
    protected FullyQualifiedClassType serviceEndpointInterface;
    @XmlElement(name = "enable-mtom")
    protected TrueFalseType enableMtom;
    @XmlElement(name = "port-component-link")
    protected eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.String portComponentLink;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Obtiene el valor de la propiedad serviceEndpointInterface.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getServiceEndpointInterface() {
        return serviceEndpointInterface;
    }

    /**
     * Define el valor de la propiedad serviceEndpointInterface.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setServiceEndpointInterface(FullyQualifiedClassType value) {
        this.serviceEndpointInterface = value;
    }

    /**
     * Obtiene el valor de la propiedad enableMtom.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseType }
     *     
     */
    public TrueFalseType getEnableMtom() {
        return enableMtom;
    }

    /**
     * Define el valor de la propiedad enableMtom.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseType }
     *     
     */
    public void setEnableMtom(TrueFalseType value) {
        this.enableMtom = value;
    }

    /**
     * Obtiene el valor de la propiedad portComponentLink.
     * 
     * @return
     *     possible object is
     *     {@link eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.String getPortComponentLink() {
        return portComponentLink;
    }

    /**
     * Define el valor de la propiedad portComponentLink.
     * 
     * @param value
     *     allowed object is
     *     {@link eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public void setPortComponentLink(eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.String value) {
        this.portComponentLink = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setId(java.lang.String value) {
        this.id = value;
    }

}
