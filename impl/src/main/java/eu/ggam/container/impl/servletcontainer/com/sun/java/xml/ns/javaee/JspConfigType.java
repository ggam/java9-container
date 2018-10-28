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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 
 * 	The jsp-configType is used to provide global configuration
 * 	information for the JSP files in a web application. It has
 * 	two subelements, taglib and jsp-property-group.
 * 
 *       
 * 
 * <p>Clase Java para jsp-configType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="jsp-configType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taglib" type="{http://java.sun.com/xml/ns/javaee}taglibType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="jsp-property-group" type="{http://java.sun.com/xml/ns/javaee}jsp-property-groupType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "jsp-configType", propOrder = {
    "taglibs",
    "jspPropertyGroups"
})
public class JspConfigType {

    @XmlElement(name = "taglib")
    protected List<TaglibType> taglibs;
    @XmlElement(name = "jsp-property-group")
    protected List<JspPropertyGroupType> jspPropertyGroups;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Gets the value of the taglibs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taglibs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaglibs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaglibType }
     * 
     * 
     */
    public List<TaglibType> getTaglibs() {
        if (taglibs == null) {
            taglibs = new ArrayList<TaglibType>();
        }
        return this.taglibs;
    }

    /**
     * Gets the value of the jspPropertyGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jspPropertyGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJspPropertyGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JspPropertyGroupType }
     * 
     * 
     */
    public List<JspPropertyGroupType> getJspPropertyGroups() {
        if (jspPropertyGroups == null) {
            jspPropertyGroups = new ArrayList<JspPropertyGroupType>();
        }
        return this.jspPropertyGroups;
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