//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee;

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
 * 	Declares the handler for a port-component. Handlers can access the
 * 	init-param name/value pairs using the HandlerInfo interface. If
 * 	port-name is not specified, the handler is assumed to be associated
 * 	with all ports of the service.
 * 
 * 	Used in: service-ref
 * 
 *       
 * 
 * <p>Clase Java para service-ref_handlerType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="service-ref_handlerType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}descriptionGroup"/&gt;
 *         &lt;element name="handler-name" type="{http://java.sun.com/xml/ns/javaee}string"/&gt;
 *         &lt;element name="handler-class" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType"/&gt;
 *         &lt;element name="init-param" type="{http://java.sun.com/xml/ns/javaee}param-valueType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="soap-header" type="{http://java.sun.com/xml/ns/javaee}xsdQNameType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="soap-role" type="{http://java.sun.com/xml/ns/javaee}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="port-name" type="{http://java.sun.com/xml/ns/javaee}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "service-ref_handlerType", propOrder = {
    "descriptions",
    "displayNames",
    "icons",
    "handlerName",
    "handlerClass",
    "initParams",
    "soapHeaders",
    "soapRoles",
    "portNames"
})
public class ServiceRefHandlerType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "display-name")
    protected List<DisplayNameType> displayNames;
    @XmlElement(name = "icon")
    protected List<IconType> icons;
    @XmlElement(name = "handler-name", required = true)
    protected es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String handlerName;
    @XmlElement(name = "handler-class", required = true)
    protected FullyQualifiedClassType handlerClass;
    @XmlElement(name = "init-param")
    protected List<ParamValueType> initParams;
    @XmlElement(name = "soap-header")
    protected List<XsdQNameType> soapHeaders;
    @XmlElement(name = "soap-role")
    protected List<es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String> soapRoles;
    @XmlElement(name = "port-name")
    protected List<es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String> portNames;
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
     * Obtiene el valor de la propiedad handlerName.
     * 
     * @return
     *     possible object is
     *     {@link es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String getHandlerName() {
        return handlerName;
    }

    /**
     * Define el valor de la propiedad handlerName.
     * 
     * @param value
     *     allowed object is
     *     {@link es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public void setHandlerName(es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String value) {
        this.handlerName = value;
    }

    /**
     * Obtiene el valor de la propiedad handlerClass.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getHandlerClass() {
        return handlerClass;
    }

    /**
     * Define el valor de la propiedad handlerClass.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setHandlerClass(FullyQualifiedClassType value) {
        this.handlerClass = value;
    }

    /**
     * Gets the value of the initParams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the initParams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInitParams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParamValueType }
     * 
     * 
     */
    public List<ParamValueType> getInitParams() {
        if (initParams == null) {
            initParams = new ArrayList<ParamValueType>();
        }
        return this.initParams;
    }

    /**
     * Gets the value of the soapHeaders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soapHeaders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoapHeaders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XsdQNameType }
     * 
     * 
     */
    public List<XsdQNameType> getSoapHeaders() {
        if (soapHeaders == null) {
            soapHeaders = new ArrayList<XsdQNameType>();
        }
        return this.soapHeaders;
    }

    /**
     * Gets the value of the soapRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set<p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoapRoles().add(newItem);
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String }
     * 
     * 
     */
    public List<es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String> getSoapRoles() {
        if (soapRoles == null) {
            soapRoles = new ArrayList<es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String>();
        }
        return this.soapRoles;
    }

    /**
     * Gets the value of the portNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set<p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortNames().add(newItem);
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String }
     * 
     * 
     */
    public List<es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String> getPortNames() {
        if (portNames == null) {
            portNames = new ArrayList<es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String>();
        }
        return this.portNames;
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
