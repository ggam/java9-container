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
 * 	The jsp-property-groupType is used to group a number of
 * 	files so they can be given global property information.
 * 	All files so described are deemed to be JSP files.  The
 * 	following additional properties can be described:
 * 
 * 	    - Control whether EL is ignored.
 * 	    - Control whether scripting elements are invalid.
 * 	    - Indicate pageEncoding information.
 * 	    - Indicate that a resource is a JSP document (XML).
 * 	    - Prelude and Coda automatic includes.
 *             - Control whether the character sequence #{ is allowed
 *               when used as a String literal.
 *             - Control whether template text containing only
 *               whitespaces must be removed from the response output.
 * 
 *       
 * 
 * <p>Clase Java para jsp-property-groupType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="jsp-property-groupType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}descriptionGroup"/&gt;
 *         &lt;element name="url-pattern" type="{http://java.sun.com/xml/ns/javaee}url-patternType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="el-ignored" type="{http://java.sun.com/xml/ns/javaee}true-falseType" minOccurs="0"/&gt;
 *         &lt;element name="page-encoding" type="{http://java.sun.com/xml/ns/javaee}string" minOccurs="0"/&gt;
 *         &lt;element name="scripting-invalid" type="{http://java.sun.com/xml/ns/javaee}true-falseType" minOccurs="0"/&gt;
 *         &lt;element name="is-xml" type="{http://java.sun.com/xml/ns/javaee}true-falseType" minOccurs="0"/&gt;
 *         &lt;element name="include-prelude" type="{http://java.sun.com/xml/ns/javaee}pathType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="include-coda" type="{http://java.sun.com/xml/ns/javaee}pathType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="deferred-syntax-allowed-as-literal" type="{http://java.sun.com/xml/ns/javaee}true-falseType" minOccurs="0"/&gt;
 *         &lt;element name="trim-directive-whitespaces" type="{http://java.sun.com/xml/ns/javaee}true-falseType" minOccurs="0"/&gt;
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
@XmlType(name = "jsp-property-groupType", propOrder = {
    "descriptions",
    "displayNames",
    "icons",
    "urlPatterns",
    "elIgnored",
    "pageEncoding",
    "scriptingInvalid",
    "isXml",
    "includePreludes",
    "includeCodas",
    "deferredSyntaxAllowedAsLiteral",
    "trimDirectiveWhitespaces"
})
public class JspPropertyGroupType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "display-name")
    protected List<DisplayNameType> displayNames;
    @XmlElement(name = "icon")
    protected List<IconType> icons;
    @XmlElement(name = "url-pattern", required = true)
    protected List<UrlPatternType> urlPatterns;
    @XmlElement(name = "el-ignored")
    protected TrueFalseType elIgnored;
    @XmlElement(name = "page-encoding")
    protected com.sun.java.xml.ns.javaee.String pageEncoding;
    @XmlElement(name = "scripting-invalid")
    protected TrueFalseType scriptingInvalid;
    @XmlElement(name = "is-xml")
    protected TrueFalseType isXml;
    @XmlElement(name = "include-prelude")
    protected List<PathType> includePreludes;
    @XmlElement(name = "include-coda")
    protected List<PathType> includeCodas;
    @XmlElement(name = "deferred-syntax-allowed-as-literal")
    protected TrueFalseType deferredSyntaxAllowedAsLiteral;
    @XmlElement(name = "trim-directive-whitespaces")
    protected TrueFalseType trimDirectiveWhitespaces;
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
     * Gets the value of the urlPatterns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the urlPatterns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUrlPatterns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UrlPatternType }
     * 
     * 
     */
    public List<UrlPatternType> getUrlPatterns() {
        if (urlPatterns == null) {
            urlPatterns = new ArrayList<UrlPatternType>();
        }
        return this.urlPatterns;
    }

    /**
     * Obtiene el valor de la propiedad elIgnored.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseType }
     *     
     */
    public TrueFalseType getElIgnored() {
        return elIgnored;
    }

    /**
     * Define el valor de la propiedad elIgnored.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseType }
     *     
     */
    public void setElIgnored(TrueFalseType value) {
        this.elIgnored = value;
    }

    /**
     * Obtiene el valor de la propiedad pageEncoding.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.java.xml.ns.javaee.String }
     *     
     */
    public com.sun.java.xml.ns.javaee.String getPageEncoding() {
        return pageEncoding;
    }

    /**
     * Define el valor de la propiedad pageEncoding.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.java.xml.ns.javaee.String }
     *     
     */
    public void setPageEncoding(com.sun.java.xml.ns.javaee.String value) {
        this.pageEncoding = value;
    }

    /**
     * Obtiene el valor de la propiedad scriptingInvalid.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseType }
     *     
     */
    public TrueFalseType getScriptingInvalid() {
        return scriptingInvalid;
    }

    /**
     * Define el valor de la propiedad scriptingInvalid.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseType }
     *     
     */
    public void setScriptingInvalid(TrueFalseType value) {
        this.scriptingInvalid = value;
    }

    /**
     * Obtiene el valor de la propiedad isXml.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseType }
     *     
     */
    public TrueFalseType getIsXml() {
        return isXml;
    }

    /**
     * Define el valor de la propiedad isXml.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseType }
     *     
     */
    public void setIsXml(TrueFalseType value) {
        this.isXml = value;
    }

    /**
     * Gets the value of the includePreludes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includePreludes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludePreludes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PathType }
     * 
     * 
     */
    public List<PathType> getIncludePreludes() {
        if (includePreludes == null) {
            includePreludes = new ArrayList<PathType>();
        }
        return this.includePreludes;
    }

    /**
     * Gets the value of the includeCodas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includeCodas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludeCodas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PathType }
     * 
     * 
     */
    public List<PathType> getIncludeCodas() {
        if (includeCodas == null) {
            includeCodas = new ArrayList<PathType>();
        }
        return this.includeCodas;
    }

    /**
     * Obtiene el valor de la propiedad deferredSyntaxAllowedAsLiteral.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseType }
     *     
     */
    public TrueFalseType getDeferredSyntaxAllowedAsLiteral() {
        return deferredSyntaxAllowedAsLiteral;
    }

    /**
     * Define el valor de la propiedad deferredSyntaxAllowedAsLiteral.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseType }
     *     
     */
    public void setDeferredSyntaxAllowedAsLiteral(TrueFalseType value) {
        this.deferredSyntaxAllowedAsLiteral = value;
    }

    /**
     * Obtiene el valor de la propiedad trimDirectiveWhitespaces.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseType }
     *     
     */
    public TrueFalseType getTrimDirectiveWhitespaces() {
        return trimDirectiveWhitespaces;
    }

    /**
     * Define el valor de la propiedad trimDirectiveWhitespaces.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseType }
     *     
     */
    public void setTrimDirectiveWhitespaces(TrueFalseType value) {
        this.trimDirectiveWhitespaces = value;
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
