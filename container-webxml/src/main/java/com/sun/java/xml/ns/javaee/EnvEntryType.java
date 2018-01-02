//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.02 a las 07:48:19 PM CET 
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
 * 	The env-entryType is used to declare an application's
 * 	environment entry. The declaration consists of an optional
 * 	description, the name of the environment entry, a type
 * 	(optional if the value is injected, otherwise required), and
 * 	an optional value.
 * 
 * 	It also includes optional elements to define injection of
 * 	the named resource into fields or JavaBeans properties.
 * 
 * 	If a value is not specified and injection is requested,
 * 	no injection will occur and no entry of the specified name
 * 	will be created.  This allows an initial value to be
 * 	specified in the source code without being incorrectly
 * 	changed when no override has been specified.
 * 
 * 	If a value is not specified and no injection is requested,
 * 	a value must be supplied during deployment.
 * 
 * 	This type is used by env-entry elements.
 * 
 *       
 * 
 * <p>Clase Java para env-entryType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="env-entryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="env-entry-name" type="{http://java.sun.com/xml/ns/javaee}jndi-nameType"/&gt;
 *         &lt;element name="env-entry-type" type="{http://java.sun.com/xml/ns/javaee}env-entry-type-valuesType" minOccurs="0"/&gt;
 *         &lt;element name="env-entry-value" type="{http://java.sun.com/xml/ns/javaee}xsdStringType" minOccurs="0"/&gt;
 *         &lt;group ref="{http://java.sun.com/xml/ns/javaee}resourceGroup"/&gt;
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
@XmlType(name = "env-entryType", propOrder = {
    "description",
    "envEntryName",
    "envEntryType",
    "envEntryValue",
    "mappedName",
    "injectionTarget"
})
public class EnvEntryType {

    protected List<DescriptionType> description;
    @XmlElement(name = "env-entry-name", required = true)
    protected JndiNameType envEntryName;
    @XmlElement(name = "env-entry-type")
    protected EnvEntryTypeValuesType envEntryType;
    @XmlElement(name = "env-entry-value")
    protected XsdStringType envEntryValue;
    @XmlElement(name = "mapped-name")
    protected XsdStringType mappedName;
    @XmlElement(name = "injection-target")
    protected List<InjectionTargetType> injectionTarget;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Obtiene el valor de la propiedad envEntryName.
     * 
     * @return
     *     possible object is
     *     {@link JndiNameType }
     *     
     */
    public JndiNameType getEnvEntryName() {
        return envEntryName;
    }

    /**
     * Define el valor de la propiedad envEntryName.
     * 
     * @param value
     *     allowed object is
     *     {@link JndiNameType }
     *     
     */
    public void setEnvEntryName(JndiNameType value) {
        this.envEntryName = value;
    }

    /**
     * Obtiene el valor de la propiedad envEntryType.
     * 
     * @return
     *     possible object is
     *     {@link EnvEntryTypeValuesType }
     *     
     */
    public EnvEntryTypeValuesType getEnvEntryType() {
        return envEntryType;
    }

    /**
     * Define el valor de la propiedad envEntryType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvEntryTypeValuesType }
     *     
     */
    public void setEnvEntryType(EnvEntryTypeValuesType value) {
        this.envEntryType = value;
    }

    /**
     * Obtiene el valor de la propiedad envEntryValue.
     * 
     * @return
     *     possible object is
     *     {@link XsdStringType }
     *     
     */
    public XsdStringType getEnvEntryValue() {
        return envEntryValue;
    }

    /**
     * Define el valor de la propiedad envEntryValue.
     * 
     * @param value
     *     allowed object is
     *     {@link XsdStringType }
     *     
     */
    public void setEnvEntryValue(XsdStringType value) {
        this.envEntryValue = value;
    }

    /**
     * Obtiene el valor de la propiedad mappedName.
     * 
     * @return
     *     possible object is
     *     {@link XsdStringType }
     *     
     */
    public XsdStringType getMappedName() {
        return mappedName;
    }

    /**
     * Define el valor de la propiedad mappedName.
     * 
     * @param value
     *     allowed object is
     *     {@link XsdStringType }
     *     
     */
    public void setMappedName(XsdStringType value) {
        this.mappedName = value;
    }

    /**
     * Gets the value of the injectionTarget property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the injectionTarget property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInjectionTarget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InjectionTargetType }
     * 
     * 
     */
    public List<InjectionTargetType> getInjectionTarget() {
        if (injectionTarget == null) {
            injectionTarget = new ArrayList<InjectionTargetType>();
        }
        return this.injectionTarget;
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
