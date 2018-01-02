//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.02 a las 07:48:19 PM CET 
//


package com.sun.java.xml.ns.javaee;

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
 * 	The taglibType defines the syntax for declaring in
 * 	the deployment descriptor that a tag library is
 * 	available to the application.  This can be done
 * 	to override implicit map entries from TLD files and
 * 	from the container.
 * 
 *       
 * 
 * <p>Clase Java para taglibType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="taglibType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taglib-uri" type="{http://java.sun.com/xml/ns/javaee}string"/&gt;
 *         &lt;element name="taglib-location" type="{http://java.sun.com/xml/ns/javaee}pathType"/&gt;
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
@XmlType(name = "taglibType", propOrder = {
    "taglibUri",
    "taglibLocation"
})
public class TaglibType {

    @XmlElement(name = "taglib-uri", required = true)
    protected com.sun.java.xml.ns.javaee.String taglibUri;
    @XmlElement(name = "taglib-location", required = true)
    protected PathType taglibLocation;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Obtiene el valor de la propiedad taglibUri.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.java.xml.ns.javaee.String }
     *     
     */
    public com.sun.java.xml.ns.javaee.String getTaglibUri() {
        return taglibUri;
    }

    /**
     * Define el valor de la propiedad taglibUri.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.java.xml.ns.javaee.String }
     *     
     */
    public void setTaglibUri(com.sun.java.xml.ns.javaee.String value) {
        this.taglibUri = value;
    }

    /**
     * Obtiene el valor de la propiedad taglibLocation.
     * 
     * @return
     *     possible object is
     *     {@link PathType }
     *     
     */
    public PathType getTaglibLocation() {
        return taglibLocation;
    }

    /**
     * Define el valor de la propiedad taglibLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link PathType }
     *     
     */
    public void setTaglibLocation(PathType value) {
        this.taglibLocation = value;
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
