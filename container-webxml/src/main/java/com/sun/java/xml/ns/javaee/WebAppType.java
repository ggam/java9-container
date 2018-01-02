//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.02 a las 07:48:19 PM CET 
//


package com.sun.java.xml.ns.javaee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Clase Java para web-appType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="web-appType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}descriptionGroup"/&gt;
 *         &lt;element name="distributable" type="{http://java.sun.com/xml/ns/javaee}emptyType"/&gt;
 *         &lt;element name="context-param" type="{http://java.sun.com/xml/ns/javaee}param-valueType"/&gt;
 *         &lt;element name="filter" type="{http://java.sun.com/xml/ns/javaee}filterType"/&gt;
 *         &lt;element name="filter-mapping" type="{http://java.sun.com/xml/ns/javaee}filter-mappingType"/&gt;
 *         &lt;element name="listener" type="{http://java.sun.com/xml/ns/javaee}listenerType"/&gt;
 *         &lt;element name="servlet" type="{http://java.sun.com/xml/ns/javaee}servletType"/&gt;
 *         &lt;element name="servlet-mapping" type="{http://java.sun.com/xml/ns/javaee}servlet-mappingType"/&gt;
 *         &lt;element name="session-config" type="{http://java.sun.com/xml/ns/javaee}session-configType"/&gt;
 *         &lt;element name="mime-mapping" type="{http://java.sun.com/xml/ns/javaee}mime-mappingType"/&gt;
 *         &lt;element name="welcome-file-list" type="{http://java.sun.com/xml/ns/javaee}welcome-file-listType"/&gt;
 *         &lt;element name="error-page" type="{http://java.sun.com/xml/ns/javaee}error-pageType"/&gt;
 *         &lt;element name="jsp-config" type="{http://java.sun.com/xml/ns/javaee}jsp-configType"/&gt;
 *         &lt;element name="security-constraint" type="{http://java.sun.com/xml/ns/javaee}security-constraintType"/&gt;
 *         &lt;element name="login-config" type="{http://java.sun.com/xml/ns/javaee}login-configType"/&gt;
 *         &lt;element name="security-role" type="{http://java.sun.com/xml/ns/javaee}security-roleType"/&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}jndiEnvironmentRefsGroup"/&gt;
 *         &lt;element name="message-destination" type="{http://java.sun.com/xml/ns/javaee}message-destinationType"/&gt;
 *         &lt;element name="locale-encoding-mapping-list" type="{http://java.sun.com/xml/ns/javaee}locale-encoding-mapping-listType"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="version" use="required" type="{http://java.sun.com/xml/ns/javaee}web-app-versionType" /&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="metadata-complete" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "web-appType", propOrder = {
    "descriptionAndDisplayNameAndIcon"
})
public class WebAppType {

    @XmlElementRefs({
        @XmlElementRef(name = "description", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "display-name", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "icon", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "distributable", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "context-param", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "filter", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "filter-mapping", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "listener", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "servlet", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "servlet-mapping", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "session-config", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "mime-mapping", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "welcome-file-list", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "error-page", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "jsp-config", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "security-constraint", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "login-config", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "security-role", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "env-entry", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ejb-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ejb-local-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "service-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "resource-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "resource-env-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "message-destination-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "persistence-context-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "persistence-unit-ref", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "post-construct", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "pre-destroy", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "message-destination", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "locale-encoding-mapping-list", namespace = "http://java.sun.com/xml/ns/javaee", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> descriptionAndDisplayNameAndIcon;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected java.lang.String version;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;
    @XmlAttribute(name = "metadata-complete")
    protected Boolean metadataComplete;

    /**
     * Gets the value of the descriptionAndDisplayNameAndIcon property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descriptionAndDisplayNameAndIcon property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescriptionAndDisplayNameAndIcon().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link DescriptionType }{@code >}
     * {@link JAXBElement }{@code <}{@link DisplayNameType }{@code >}
     * {@link JAXBElement }{@code <}{@link IconType }{@code >}
     * {@link JAXBElement }{@code <}{@link EmptyType }{@code >}
     * {@link JAXBElement }{@code <}{@link ParamValueType }{@code >}
     * {@link JAXBElement }{@code <}{@link FilterType }{@code >}
     * {@link JAXBElement }{@code <}{@link FilterMappingType }{@code >}
     * {@link JAXBElement }{@code <}{@link ListenerType }{@code >}
     * {@link JAXBElement }{@code <}{@link ServletType }{@code >}
     * {@link JAXBElement }{@code <}{@link ServletMappingType }{@code >}
     * {@link JAXBElement }{@code <}{@link SessionConfigType }{@code >}
     * {@link JAXBElement }{@code <}{@link MimeMappingType }{@code >}
     * {@link JAXBElement }{@code <}{@link WelcomeFileListType }{@code >}
     * {@link JAXBElement }{@code <}{@link ErrorPageType }{@code >}
     * {@link JAXBElement }{@code <}{@link JspConfigType }{@code >}
     * {@link JAXBElement }{@code <}{@link SecurityConstraintType }{@code >}
     * {@link JAXBElement }{@code <}{@link LoginConfigType }{@code >}
     * {@link JAXBElement }{@code <}{@link SecurityRoleType }{@code >}
     * {@link JAXBElement }{@code <}{@link EnvEntryType }{@code >}
     * {@link JAXBElement }{@code <}{@link EjbRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link EjbLocalRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link ServiceRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link ResourceRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link ResourceEnvRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link MessageDestinationRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link PersistenceContextRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link PersistenceUnitRefType }{@code >}
     * {@link JAXBElement }{@code <}{@link LifecycleCallbackType }{@code >}
     * {@link JAXBElement }{@code <}{@link LifecycleCallbackType }{@code >}
     * {@link JAXBElement }{@code <}{@link MessageDestinationType }{@code >}
     * {@link JAXBElement }{@code <}{@link LocaleEncodingMappingListType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getDescriptionAndDisplayNameAndIcon() {
        if (descriptionAndDisplayNameAndIcon == null) {
            descriptionAndDisplayNameAndIcon = new ArrayList<JAXBElement<?>>();
        }
        return this.descriptionAndDisplayNameAndIcon;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setVersion(java.lang.String value) {
        this.version = value;
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

    /**
     * Obtiene el valor de la propiedad metadataComplete.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMetadataComplete() {
        return metadataComplete;
    }

    /**
     * Define el valor de la propiedad metadataComplete.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMetadataComplete(Boolean value) {
        this.metadataComplete = value;
    }

}
