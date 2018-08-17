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
 * 
 * 	  The message-destination-ref element contains a declaration
 * 	  of Deployment Component's reference to a message destination
 * 	  associated with a resource in Deployment Component's
 * 	  environment. It consists of:
 * 
 * 		  - an optional description
 * 		  - the message destination reference name
 * 		  - an optional message destination type
 * 		  - an optional specification as to whether
 * 		    the destination is used for
 * 		    consuming or producing messages, or both.
 * 		    if not specified, "both" is assumed.
 * 		  - an optional link to the message destination
 * 		  - optional injection targets
 * 
 * 	  The message destination type must be supplied unless an
 * 	  injection target is specified, in which case the type
 * 	  of the target is used.  If both are specified, the type
 * 	  must be assignment compatible with the type of the injection
 * 	  target.
 * 
 * 	  Examples:
 * 
 * 	  <message-destination-ref>
 * 		  <message-destination-ref-name>jms/StockQueue
 * 		  </message-destination-ref-name>
 * 		  <message-destination-type>javax.jms.Queue
 * 		  </message-destination-type>
 * 		  <message-destination-usage>Consumes
 * 		  </message-destination-usage>
 * 		  <message-destination-link>CorporateStocks
 * 		  </message-destination-link>
 * 	  </message-destination-ref>
 * 
 * 	  
 *       
 * 
 * <p>Clase Java para message-destination-refType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="message-destination-refType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="message-destination-ref-name" type="{http://java.sun.com/xml/ns/javaee}jndi-nameType"/&gt;
 *         &lt;element name="message-destination-type" type="{http://java.sun.com/xml/ns/javaee}message-destination-typeType" minOccurs="0"/&gt;
 *         &lt;element name="message-destination-usage" type="{http://java.sun.com/xml/ns/javaee}message-destination-usageType" minOccurs="0"/&gt;
 *         &lt;element name="message-destination-link" type="{http://java.sun.com/xml/ns/javaee}message-destination-linkType" minOccurs="0"/&gt;
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
@XmlType(name = "message-destination-refType", propOrder = {
    "descriptions",
    "messageDestinationRefName",
    "messageDestinationType",
    "messageDestinationUsage",
    "messageDestinationLink",
    "mappedName",
    "injectionTargets"
})
public class MessageDestinationRefType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "message-destination-ref-name", required = true)
    protected JndiNameType messageDestinationRefName;
    @XmlElement(name = "message-destination-type")
    protected MessageDestinationTypeType messageDestinationType;
    @XmlElement(name = "message-destination-usage")
    protected MessageDestinationUsageType messageDestinationUsage;
    @XmlElement(name = "message-destination-link")
    protected MessageDestinationLinkType messageDestinationLink;
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
     * Obtiene el valor de la propiedad messageDestinationRefName.
     * 
     * @return
     *     possible object is
     *     {@link JndiNameType }
     *     
     */
    public JndiNameType getMessageDestinationRefName() {
        return messageDestinationRefName;
    }

    /**
     * Define el valor de la propiedad messageDestinationRefName.
     * 
     * @param value
     *     allowed object is
     *     {@link JndiNameType }
     *     
     */
    public void setMessageDestinationRefName(JndiNameType value) {
        this.messageDestinationRefName = value;
    }

    /**
     * Obtiene el valor de la propiedad messageDestinationType.
     * 
     * @return
     *     possible object is
     *     {@link MessageDestinationTypeType }
     *     
     */
    public MessageDestinationTypeType getMessageDestinationType() {
        return messageDestinationType;
    }

    /**
     * Define el valor de la propiedad messageDestinationType.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageDestinationTypeType }
     *     
     */
    public void setMessageDestinationType(MessageDestinationTypeType value) {
        this.messageDestinationType = value;
    }

    /**
     * Obtiene el valor de la propiedad messageDestinationUsage.
     * 
     * @return
     *     possible object is
     *     {@link MessageDestinationUsageType }
     *     
     */
    public MessageDestinationUsageType getMessageDestinationUsage() {
        return messageDestinationUsage;
    }

    /**
     * Define el valor de la propiedad messageDestinationUsage.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageDestinationUsageType }
     *     
     */
    public void setMessageDestinationUsage(MessageDestinationUsageType value) {
        this.messageDestinationUsage = value;
    }

    /**
     * Obtiene el valor de la propiedad messageDestinationLink.
     * 
     * @return
     *     possible object is
     *     {@link MessageDestinationLinkType }
     *     
     */
    public MessageDestinationLinkType getMessageDestinationLink() {
        return messageDestinationLink;
    }

    /**
     * Define el valor de la propiedad messageDestinationLink.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageDestinationLinkType }
     *     
     */
    public void setMessageDestinationLink(MessageDestinationLinkType value) {
        this.messageDestinationLink = value;
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
