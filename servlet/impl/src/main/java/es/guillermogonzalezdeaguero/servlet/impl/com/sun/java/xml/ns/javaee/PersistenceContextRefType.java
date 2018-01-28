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
 * 
 * 	  The persistence-context-ref element contains a declaration
 * 	  of Deployment Component's reference to a persistence context
 * 	  associated within a Deployment Component's
 * 	  environment. It consists of:
 * 
 * 		  - an optional description
 * 		  - the persistence context reference name
 * 		  - an optional persistence unit name.  If not specified,
 *                     the default persistence unit is assumed.
 * 		  - an optional specification as to whether
 * 		    the persistence context type is Transaction or
 * 		    Extended.  If not specified, Transaction is assumed.
 *                   - an optional list of persistence properties
 * 		  - optional injection targets
 * 
 * 	  Examples:
 * 
 *             <persistence-context-ref>
 *               <persistence-context-ref-name>myPersistenceContext
 *               </persistence-context-ref-name>
 *             </persistence-context-ref>
 * 
 *             <persistence-context-ref>
 *               <persistence-context-ref-name>myPersistenceContext
 *                 </persistence-context-ref-name>
 *               <persistence-unit-name>PersistenceUnit1
 *                 </persistence-unit-name>
 *               <persistence-context-type>Extended</persistence-context-type>
 *             </persistence-context-ref>
 * 
 * 	  
 *       
 * 
 * <p>Clase Java para persistence-context-refType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="persistence-context-refType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="persistence-context-ref-name" type="{http://java.sun.com/xml/ns/javaee}jndi-nameType"/&gt;
 *         &lt;element name="persistence-unit-name" type="{http://java.sun.com/xml/ns/javaee}string" minOccurs="0"/&gt;
 *         &lt;element name="persistence-context-type" type="{http://java.sun.com/xml/ns/javaee}persistence-context-typeType" minOccurs="0"/&gt;
 *         &lt;element name="persistence-property" type="{http://java.sun.com/xml/ns/javaee}propertyType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "persistence-context-refType", propOrder = {
    "descriptions",
    "persistenceContextRefName",
    "persistenceUnitName",
    "persistenceContextType",
    "persistenceProperties",
    "mappedName",
    "injectionTargets"
})
public class PersistenceContextRefType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "persistence-context-ref-name", required = true)
    protected JndiNameType persistenceContextRefName;
    @XmlElement(name = "persistence-unit-name")
    protected eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.String persistenceUnitName;
    @XmlElement(name = "persistence-context-type")
    protected PersistenceContextTypeType persistenceContextType;
    @XmlElement(name = "persistence-property")
    protected List<PropertyType> persistenceProperties;
    @XmlElement(name = "mapped-name")
    protected XsdStringType mappedName;
    @XmlElement(name = "injection-target")
    protected List<InjectionTargetType> injectionTargets;
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
     * Obtiene el valor de la propiedad persistenceContextRefName.
     * 
     * @return
     *     possible object is
     *     {@link JndiNameType }
     *     
     */
    public JndiNameType getPersistenceContextRefName() {
        return persistenceContextRefName;
    }

    /**
     * Define el valor de la propiedad persistenceContextRefName.
     * 
     * @param value
     *     allowed object is
     *     {@link JndiNameType }
     *     
     */
    public void setPersistenceContextRefName(JndiNameType value) {
        this.persistenceContextRefName = value;
    }

    /**
     * Obtiene el valor de la propiedad persistenceUnitName.
     * 
     * @return
     *     possible object is
     *     {@link eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    /**
     * Define el valor de la propiedad persistenceUnitName.
     * 
     * @param value
     *     allowed object is
     *     {@link eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.String }
     *     
     */
    public void setPersistenceUnitName(eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.String value) {
        this.persistenceUnitName = value;
    }

    /**
     * Obtiene el valor de la propiedad persistenceContextType.
     * 
     * @return
     *     possible object is
     *     {@link PersistenceContextTypeType }
     *     
     */
    public PersistenceContextTypeType getPersistenceContextType() {
        return persistenceContextType;
    }

    /**
     * Define el valor de la propiedad persistenceContextType.
     * 
     * @param value
     *     allowed object is
     *     {@link PersistenceContextTypeType }
     *     
     */
    public void setPersistenceContextType(PersistenceContextTypeType value) {
        this.persistenceContextType = value;
    }

    /**
     * Gets the value of the persistenceProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the persistenceProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersistenceProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyType }
     * 
     * 
     */
    public List<PropertyType> getPersistenceProperties() {
        if (persistenceProperties == null) {
            persistenceProperties = new ArrayList<PropertyType>();
        }
        return this.persistenceProperties;
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
     * Gets the value of the injectionTargets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the injectionTargets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInjectionTargets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InjectionTargetType }
     * 
     * 
     */
    public List<InjectionTargetType> getInjectionTargets() {
        if (injectionTargets == null) {
            injectionTargets = new ArrayList<InjectionTargetType>();
        }
        return this.injectionTargets;
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
