//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package com.sun.java.xml.ns.javaee;

import java.util.ArrayList;
import java.util.List;
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
 * 	The service-ref element declares a reference to a Web
 * 	service. It contains optional description, display name and
 * 	icons, a declaration of the required Service interface,
 * 	an optional WSDL document location, an optional set
 * 	of JAX-RPC mappings, an optional QName for the service element,
 * 	an optional set of Service Endpoint Interfaces to be resolved
 * 	by the container to a WSDL port, and an optional set of handlers.
 * 
 *       
 * 
 * <p>Clase Java para service-refType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="service-refType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}descriptionGroup"/&gt;
 *         &lt;element name="service-ref-name" type="{http://java.sun.com/xml/ns/javaee}jndi-nameType"/&gt;
 *         &lt;element name="service-interface" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType"/&gt;
 *         &lt;element name="service-ref-type" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType" minOccurs="0"/&gt;
 *         &lt;element name="wsdl-file" type="{http://java.sun.com/xml/ns/javaee}xsdAnyURIType" minOccurs="0"/&gt;
 *         &lt;element name="jaxrpc-mapping-file" type="{http://java.sun.com/xml/ns/javaee}pathType" minOccurs="0"/&gt;
 *         &lt;element name="service-qname" type="{http://java.sun.com/xml/ns/javaee}xsdQNameType" minOccurs="0"/&gt;
 *         &lt;element name="port-component-ref" type="{http://java.sun.com/xml/ns/javaee}port-component-refType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="handler" type="{http://java.sun.com/xml/ns/javaee}service-ref_handlerType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="handler-chains" type="{http://java.sun.com/xml/ns/javaee}service-ref_handler-chainsType" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}resourceGroup"/&gt;
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
@XmlType(name = "service-refType", propOrder = {
    "descriptions",
    "displayNames",
    "icons",
    "serviceRefName",
    "serviceInterface",
    "serviceRefType",
    "wsdlFile",
    "jaxrpcMappingFile",
    "serviceQname",
    "portComponentReves",
    "handlerChains",
    "handlers",
    "mappedName",
    "injectionTargets"
})
public class ServiceRefType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "display-name")
    protected List<DisplayNameType> displayNames;
    @XmlElement(name = "icon")
    protected List<IconType> icons;
    @XmlElement(name = "service-ref-name", required = true)
    protected JndiNameType serviceRefName;
    @XmlElement(name = "service-interface", required = true)
    protected FullyQualifiedClassType serviceInterface;
    @XmlElement(name = "service-ref-type")
    protected FullyQualifiedClassType serviceRefType;
    @XmlElement(name = "wsdl-file")
    protected XsdAnyURIType wsdlFile;
    @XmlElement(name = "jaxrpc-mapping-file")
    protected PathType jaxrpcMappingFile;
    @XmlElement(name = "service-qname")
    protected XsdQNameType serviceQname;
    @XmlElement(name = "port-component-ref")
    protected List<PortComponentRefType> portComponentReves;
    @XmlElement(name = "handler-chains")
    protected ServiceRefHandlerChainsType handlerChains;
    @XmlElement(name = "handler")
    protected List<ServiceRefHandlerType> handlers;
    @XmlElement(name = "mapped-name")
    protected XsdStringType mappedName;
    @XmlElement(name = "injection-target")
    protected List<InjectionTargetType> injectionTargets;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Gets the value of the descriptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descriptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescriptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescriptions() {
        if (descriptions == null) {
            descriptions = new ArrayList<DescriptionType>();
        }
        return this.descriptions;
    }

    /**
     * Gets the value of the displayNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DisplayNameType }
     * 
     * 
     */
    public List<DisplayNameType> getDisplayNames() {
        if (displayNames == null) {
            displayNames = new ArrayList<DisplayNameType>();
        }
        return this.displayNames;
    }

    /**
     * Gets the value of the icons property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the icons property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIcons().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IconType }
     * 
     * 
     */
    public List<IconType> getIcons() {
        if (icons == null) {
            icons = new ArrayList<IconType>();
        }
        return this.icons;
    }

    /**
     * Obtiene el valor de la propiedad serviceRefName.
     * 
     * @return
     *     possible object is
     *     {@link JndiNameType }
     *     
     */
    public JndiNameType getServiceRefName() {
        return serviceRefName;
    }

    /**
     * Define el valor de la propiedad serviceRefName.
     * 
     * @param value
     *     allowed object is
     *     {@link JndiNameType }
     *     
     */
    public void setServiceRefName(JndiNameType value) {
        this.serviceRefName = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceInterface.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getServiceInterface() {
        return serviceInterface;
    }

    /**
     * Define el valor de la propiedad serviceInterface.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setServiceInterface(FullyQualifiedClassType value) {
        this.serviceInterface = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceRefType.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getServiceRefType() {
        return serviceRefType;
    }

    /**
     * Define el valor de la propiedad serviceRefType.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setServiceRefType(FullyQualifiedClassType value) {
        this.serviceRefType = value;
    }

    /**
     * Obtiene el valor de la propiedad wsdlFile.
     * 
     * @return
     *     possible object is
     *     {@link XsdAnyURIType }
     *     
     */
    public XsdAnyURIType getWsdlFile() {
        return wsdlFile;
    }

    /**
     * Define el valor de la propiedad wsdlFile.
     * 
     * @param value
     *     allowed object is
     *     {@link XsdAnyURIType }
     *     
     */
    public void setWsdlFile(XsdAnyURIType value) {
        this.wsdlFile = value;
    }

    /**
     * Obtiene el valor de la propiedad jaxrpcMappingFile.
     * 
     * @return
     *     possible object is
     *     {@link PathType }
     *     
     */
    public PathType getJaxrpcMappingFile() {
        return jaxrpcMappingFile;
    }

    /**
     * Define el valor de la propiedad jaxrpcMappingFile.
     * 
     * @param value
     *     allowed object is
     *     {@link PathType }
     *     
     */
    public void setJaxrpcMappingFile(PathType value) {
        this.jaxrpcMappingFile = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceQname.
     * 
     * @return
     *     possible object is
     *     {@link XsdQNameType }
     *     
     */
    public XsdQNameType getServiceQname() {
        return serviceQname;
    }

    /**
     * Define el valor de la propiedad serviceQname.
     * 
     * @param value
     *     allowed object is
     *     {@link XsdQNameType }
     *     
     */
    public void setServiceQname(XsdQNameType value) {
        this.serviceQname = value;
    }

    /**
     * Gets the value of the portComponentReves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the portComponentReves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortComponentReves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PortComponentRefType }
     * 
     * 
     */
    public List<PortComponentRefType> getPortComponentReves() {
        if (portComponentReves == null) {
            portComponentReves = new ArrayList<PortComponentRefType>();
        }
        return this.portComponentReves;
    }

    /**
     * Obtiene el valor de la propiedad handlerChains.
     * 
     * @return
     *     possible object is
     *     {@link ServiceRefHandlerChainsType }
     *     
     */
    public ServiceRefHandlerChainsType getHandlerChains() {
        return handlerChains;
    }

    /**
     * Define el valor de la propiedad handlerChains.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceRefHandlerChainsType }
     *     
     */
    public void setHandlerChains(ServiceRefHandlerChainsType value) {
        this.handlerChains = value;
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
     * Obtiene el valor de la propiedad mappedName.
     * 
     * @return
     *     possible object is
     *     {@link XsdStringType }
     *     
     */
    public XsdStringType getMappedName() {
        return mappedName;
    }

    /**
     * Define el valor de la propiedad mappedName.
     * 
     * @param value
     *     allowed object is
     *     {@link XsdStringType }
     *     
     */
    public void setMappedName(XsdStringType value) {
        this.mappedName = value;
    }

    /**
     * Gets the value of the injectionTargets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the injectionTargets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInjectionTargets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InjectionTargetType }
     * 
     * 
     */
    public List<InjectionTargetType> getInjectionTargets() {
        if (injectionTargets == null) {
            injectionTargets = new ArrayList<InjectionTargetType>();
        }
        return this.injectionTargets;
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
