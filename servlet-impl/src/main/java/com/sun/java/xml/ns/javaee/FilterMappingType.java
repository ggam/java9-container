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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 
 * 	Declaration of the filter mappings in this web
 * 	application is done by using filter-mappingType.
 * 	The container uses the filter-mapping
 * 	declarations to decide which filters to apply to a request,
 * 	and in what order. The container matches the request URI to
 * 	a Servlet in the normal way. To determine which filters to
 * 	apply it matches filter-mapping declarations either on
 * 	servlet-name, or on url-pattern for each filter-mapping
 * 	element, depending on which style is used. The order in
 * 	which filters are invoked is the order in which
 * 	filter-mapping declarations that match a request URI for a
 * 	servlet appear in the list of filter-mapping elements.The
 * 	filter-name value must be the value of the filter-name
 * 	sub-elements of one of the filter declarations in the
 * 	deployment descriptor.
 * 
 *       
 * 
 * <p>Clase Java para filter-mappingType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="filter-mappingType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="filter-name" type="{http://java.sun.com/xml/ns/javaee}filter-nameType"/&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="url-pattern" type="{http://java.sun.com/xml/ns/javaee}url-patternType"/&gt;
 *           &lt;element name="servlet-name" type="{http://java.sun.com/xml/ns/javaee}servlet-nameType"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="dispatcher" type="{http://java.sun.com/xml/ns/javaee}dispatcherType" maxOccurs="4" minOccurs="0"/&gt;
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
@XmlType(name = "filter-mappingType")
public class FilterMappingType {

    @XmlElement(name = "filter-name", required = true)
    protected FilterNameType filterName;
    @XmlElement(name = "url-pattern")
    protected List<UrlPatternType> urlPatterns;
    @XmlElement(name = "servlet-name")
    protected List<UrlPatternType> servletNames;
    @XmlElement(name = "dispatcher")
    protected List<DispatcherType> dispatchers;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

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
            urlPatterns = new ArrayList<>();
        }
        return this.urlPatterns;
    }

    public List<UrlPatternType> getServletNames() {
            if (servletNames == null) {
            servletNames = new ArrayList<>();
        }
        return this.servletNames;
    }
    
    

    /**
     * Gets the value of the dispatchers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dispatchers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDispatchers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DispatcherType }
     * 
     * 
     */
    public List<DispatcherType> getDispatchers() {
        if (dispatchers == null) {
            dispatchers = new ArrayList<DispatcherType>();
        }
        return this.dispatchers;
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
