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
 * 	The filterType is used to declare a filter in the web
 * 	application. The filter is mapped to either a servlet or a
 * 	URL pattern in the filter-mapping element, using the
 * 	filter-name value to reference. Filters can access the
 * 	initialization parameters declared in the deployment
 * 	descriptor at runtime via the FilterConfig interface.
 * 
 * 	Used in: web-app
 * 
 *       
 * 
 * <p>Clase Java para filterType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="filterType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}descriptionGroup"/&gt;
 *         &lt;element name="filter-name" type="{http://java.sun.com/xml/ns/javaee}filter-nameType"/&gt;
 *         &lt;element name="filter-class" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType"/&gt;
 *         &lt;element name="init-param" type="{http://java.sun.com/xml/ns/javaee}param-valueType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "filterType", propOrder = {
    "descriptions",
    "displayNames",
    "icons",
    "filterName",
    "filterClass",
    "initParams"
})
public class FilterType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "display-name")
    protected List<DisplayNameType> displayNames;
    @XmlElement(name = "icon")
    protected List<IconType> icons;
    @XmlElement(name = "filter-name", required = true)
    protected FilterNameType filterName;
    @XmlElement(name = "filter-class", required = true)
    protected FullyQualifiedClassType filterClass;
    @XmlElement(name = "init-param")
    protected List<ParamValueType> initParams;
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
     * Obtiene el valor de la propiedad filterName.
     * 
     * @return
     *     possible object is
     *     {@link FilterNameType }
     *     
     */
    public FilterNameType getFilterName() {
        return filterName;
    }

    /**
     * Define el valor de la propiedad filterName.
     * 
     * @param value
     *     allowed object is
     *     {@link FilterNameType }
     *     
     */
    public void setFilterName(FilterNameType value) {
        this.filterName = value;
    }

    /**
     * Obtiene el valor de la propiedad filterClass.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getFilterClass() {
        return filterClass;
    }

    /**
     * Define el valor de la propiedad filterClass.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setFilterClass(FullyQualifiedClassType value) {
        this.filterClass = value;
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
