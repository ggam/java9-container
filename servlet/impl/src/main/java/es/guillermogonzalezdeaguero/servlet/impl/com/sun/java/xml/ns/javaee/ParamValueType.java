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
 * 	This type is a general type that can be used to declare
 * 	parameter/value lists.
 * 
 *       
 * 
 * <p>Clase Java para param-valueType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="param-valueType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="param-name" type="{http://java.sun.com/xml/ns/javaee}string"/&gt;
 *         &lt;element name="param-value" type="{http://java.sun.com/xml/ns/javaee}xsdStringType"/&gt;
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
@XmlType(name = "param-valueType", propOrder = {
    "descriptions",
    "paramName",
    "paramValue"
})
public class ParamValueType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "param-name", required = true)
    protected es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String paramName;
    @XmlElement(name = "param-value", required = true)
    protected XsdStringType paramValue;
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
     * Obtiene el valor de la propiedad paramName.
     * 
     * @return
     *     possible object is
     *     {@link es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String getParamName() {
        return paramName;
    }

    /**
     * Define el valor de la propiedad paramName.
     * 
     * @param value
     *     allowed object is
     *     {@link es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public void setParamName(es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.String value) {
        this.paramName = value;
    }

    /**
     * Obtiene el valor de la propiedad paramValue.
     * 
     * @return
     *     possible object is
     *     {@link XsdStringType }
     *     
     */
    public XsdStringType getParamValue() {
        return paramValue;
    }

    /**
     * Define el valor de la propiedad paramValue.
     * 
     * @param value
     *     allowed object is
     *     {@link XsdStringType }
     *     
     */
    public void setParamValue(XsdStringType value) {
        this.paramValue = value;
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
