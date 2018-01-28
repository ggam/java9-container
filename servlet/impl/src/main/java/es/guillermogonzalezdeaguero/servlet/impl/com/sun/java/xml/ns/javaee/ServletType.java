//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee;

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
 * 	The servletType is used to declare a servlet.
 * 	It contains the declarative data of a
 * 	servlet. If a jsp-file is specified and the load-on-startup
 * 	element is present, then the JSP should be precompiled and
 * 	loaded.
 * 
 * 	Used in: web-app
 * 
 *       
 * 
 * <p>Clase Java para servletType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="servletType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}descriptionGroup"/&gt;
 *         &lt;element name="servlet-name" type="{http://java.sun.com/xml/ns/javaee}servlet-nameType"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="servlet-class" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType"/&gt;
 *           &lt;element name="jsp-file" type="{http://java.sun.com/xml/ns/javaee}jsp-fileType"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="init-param" type="{http://java.sun.com/xml/ns/javaee}param-valueType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="load-on-startup" type="{http://java.sun.com/xml/ns/javaee}load-on-startupType" minOccurs="0"/&gt;
 *         &lt;element name="run-as" type="{http://java.sun.com/xml/ns/javaee}run-asType" minOccurs="0"/&gt;
 *         &lt;element name="security-role-ref" type="{http://java.sun.com/xml/ns/javaee}security-role-refType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "servletType", propOrder = {
    "descriptions",
    "displayNames",
    "icons",
    "servletName",
    "jspFile",
    "servletClass",
    "initParams",
    "loadOnStartup",
    "runAs",
    "securityRoleReves"
})
public class ServletType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "display-name")
    protected List<DisplayNameType> displayNames;
    @XmlElement(name = "icon")
    protected List<IconType> icons;
    @XmlElement(name = "servlet-name", required = true)
    protected ServletNameType servletName;
    @XmlElement(name = "jsp-file")
    protected JspFileType jspFile;
    @XmlElement(name = "servlet-class")
    protected FullyQualifiedClassType servletClass;
    @XmlElement(name = "init-param")
    protected List<ParamValueType> initParams;
    @XmlElement(name = "load-on-startup")
    protected java.lang.String loadOnStartup;
    @XmlElement(name = "run-as")
    protected RunAsType runAs;
    @XmlElement(name = "security-role-ref")
    protected List<SecurityRoleRefType> securityRoleReves;
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
     * Obtiene el valor de la propiedad servletName.
     * 
     * @return
     *     possible object is
     *     {@link ServletNameType }
     *     
     */
    public ServletNameType getServletName() {
        return servletName;
    }

    /**
     * Define el valor de la propiedad servletName.
     * 
     * @param value
     *     allowed object is
     *     {@link ServletNameType }
     *     
     */
    public void setServletName(ServletNameType value) {
        this.servletName = value;
    }

    /**
     * Obtiene el valor de la propiedad jspFile.
     * 
     * @return
     *     possible object is
     *     {@link JspFileType }
     *     
     */
    public JspFileType getJspFile() {
        return jspFile;
    }

    /**
     * Define el valor de la propiedad jspFile.
     * 
     * @param value
     *     allowed object is
     *     {@link JspFileType }
     *     
     */
    public void setJspFile(JspFileType value) {
        this.jspFile = value;
    }

    /**
     * Obtiene el valor de la propiedad servletClass.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getServletClass() {
        return servletClass;
    }

    /**
     * Define el valor de la propiedad servletClass.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setServletClass(FullyQualifiedClassType value) {
        this.servletClass = value;
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
     * Obtiene el valor de la propiedad loadOnStartup.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getLoadOnStartup() {
        return loadOnStartup;
    }

    /**
     * Define el valor de la propiedad loadOnStartup.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setLoadOnStartup(java.lang.String value) {
        this.loadOnStartup = value;
    }

    /**
     * Obtiene el valor de la propiedad runAs.
     * 
     * @return
     *     possible object is
     *     {@link RunAsType }
     *     
     */
    public RunAsType getRunAs() {
        return runAs;
    }

    /**
     * Define el valor de la propiedad runAs.
     * 
     * @param value
     *     allowed object is
     *     {@link RunAsType }
     *     
     */
    public void setRunAs(RunAsType value) {
        this.runAs = value;
    }

    /**
     * Gets the value of the securityRoleReves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the securityRoleReves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecurityRoleReves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SecurityRoleRefType }
     * 
     * 
     */
    public List<SecurityRoleRefType> getSecurityRoleReves() {
        if (securityRoleReves == null) {
            securityRoleReves = new ArrayList<SecurityRoleRefType>();
        }
        return this.securityRoleReves;
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
