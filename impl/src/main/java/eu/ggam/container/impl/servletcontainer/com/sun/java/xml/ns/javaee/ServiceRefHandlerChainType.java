//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 
 *       The handler-chain element defines the handlerchain.
 *       Handlerchain can be defined such that the handlers in the
 *       handlerchain operate,all ports of a service, on a specific
 *       port or on a list of protocol-bindings. The choice of elements
 *       service-name-pattern, port-name-pattern and protocol-bindings
 *       are used to specify whether the handlers in handler-chain are
 *       for a service, port or protocol binding. If none of these
 *       choices are specified with the handler-chain element then the
 *       handlers specified in the handler-chain will be applied on
 *       everything.
 * 
 *       
 * 
 * <p>Clase Java para service-ref_handler-chainType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="service-ref_handler-chainType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice minOccurs="0"&gt;
 *           &lt;element name="service-name-pattern" type="{http://java.sun.com/xml/ns/javaee}service-ref_qname-pattern"/&gt;
 *           &lt;element name="port-name-pattern" type="{http://java.sun.com/xml/ns/javaee}service-ref_qname-pattern"/&gt;
 *           &lt;element name="protocol-bindings" type="{http://java.sun.com/xml/ns/javaee}service-ref_protocol-bindingListType"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="handler" type="{http://java.sun.com/xml/ns/javaee}service-ref_handlerType" maxOccurs="unbounded"/&gt;
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
@XmlType(name = "service-ref_handler-chainType", propOrder = {
    "protocolBindings",
    "portNamePattern",
    "serviceNamePattern",
    "handlers"
})
public class ServiceRefHandlerChainType {

    @XmlList
    @XmlElement(name = "protocol-bindings")
    protected List<java.lang.String> protocolBindings;
    @XmlElement(name = "port-name-pattern")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected java.lang.String portNamePattern;
    @XmlElement(name = "service-name-pattern")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected java.lang.String serviceNamePattern;
    @XmlElement(name = "handler", required = true)
    protected List<ServiceRefHandlerType> handlers;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Gets the value of the protocolBindings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protocolBindings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolBindings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String }
     * 
     * 
     */
    public List<java.lang.String> getProtocolBindings() {
        if (protocolBindings == null) {
            protocolBindings = new ArrayList<java.lang.String>();
        }
        return this.protocolBindings;
    }

    /**
     * Obtiene el valor de la propiedad portNamePattern.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getPortNamePattern() {
        return portNamePattern;
    }

    /**
     * Define el valor de la propiedad portNamePattern.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setPortNamePattern(java.lang.String value) {
        this.portNamePattern = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceNamePattern.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getServiceNamePattern() {
        return serviceNamePattern;
    }

    /**
     * Define el valor de la propiedad serviceNamePattern.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setServiceNamePattern(java.lang.String value) {
        this.serviceNamePattern = value;
    }

    /**
     * Gets the value of the handlers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the handlers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHandlers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceRefHandlerType }
     * 
     * 
     */
    public List<ServiceRefHandlerType> getHandlers() {
        if (handlers == null) {
            handlers = new ArrayList<ServiceRefHandlerType>();
        }
        return this.handlers;
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
