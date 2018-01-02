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
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 	
 * 
 * 	  The display-name type contains a short name that is intended
 * 	  to be displayed by tools. It is used by display-name
 * 	  elements.  The display name need not be unique.
 * 
 * 	  Example:
 * 
 * 	  ...
 * 	     <display-name xml:lang="en">
 * 	       Employee Self Service
 * 	     </display-name>
 * 
 * 	  The value of the xml:lang attribute is "en" (English) by default.
 * 
 * 	  
 *       
 * 
 * <p>Clase Java para display-nameType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="display-nameType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://java.sun.com/xml/ns/javaee&gt;string"&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "display-nameType")
public class DisplayNameType
    extends com.sun.java.xml.ns.javaee.String
{

    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected java.lang.String lang;

    /**
     * Obtiene el valor de la propiedad lang.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getLang() {
        return lang;
    }

    /**
     * Define el valor de la propiedad lang.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setLang(java.lang.String value) {
        this.lang = value;
    }

}
