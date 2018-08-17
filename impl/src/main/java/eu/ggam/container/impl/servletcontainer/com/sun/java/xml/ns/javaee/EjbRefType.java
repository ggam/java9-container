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
 * 	The ejb-refType is used by ejb-ref elements for the
 * 	declaration of a reference to an enterprise bean's home or
 *         to the remote business interface of a 3.0 bean.
 * 	The declaration consists of:
 * 
 * 	    - an optional description
 * 	    - the EJB reference name used in the code of
 * 	      the Deployment Component that's referencing the enterprise
 * 	      bean.
 * 	    - the optional expected type of the referenced enterprise bean
 *             - the optional remote interface of the referenced enterprise bean
 *               or the remote business interface of the referenced enterprise
 *               bean
 * 	    - the optional expected home interface of the referenced
 *               enterprise bean.  Not applicable if this ejb-ref
 *               refers to the remote business interface of a 3.0 bean.
 * 	    - optional ejb-link information, used to specify the
 * 	      referenced enterprise bean
 *             - optional elements to define injection of the named enterprise
 *               bean into a component field or property
 * 
 *       
 * 
 * <p>Clase Java para ejb-refType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ejb-refType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ejb-ref-name" type="{http://java.sun.com/xml/ns/javaee}ejb-ref-nameType"/&gt;
 *         &lt;element name="ejb-ref-type" type="{http://java.sun.com/xml/ns/javaee}ejb-ref-typeType" minOccurs="0"/&gt;
 *         &lt;element name="home" type="{http://java.sun.com/xml/ns/javaee}homeType" minOccurs="0"/&gt;
 *         &lt;element name="remote" type="{http://java.sun.com/xml/ns/javaee}remoteType" minOccurs="0"/&gt;
 *         &lt;element name="ejb-link" type="{http://java.sun.com/xml/ns/javaee}ejb-linkType" minOccurs="0"/&gt;
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
@XmlType(name = "ejb-refType", propOrder = {
    "descriptions",
    "ejbRefName",
    "ejbRefType",
    "home",
    "remote",
    "ejbLink",
    "mappedName",
    "injectionTargets"
})
public class EjbRefType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "ejb-ref-name", required = true)
    protected EjbRefNameType ejbRefName;
    @XmlElement(name = "ejb-ref-type")
    protected EjbRefTypeType ejbRefType;
    protected HomeType home;
    protected RemoteType remote;
    @XmlElement(name = "ejb-link")
    protected EjbLinkType ejbLink;
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
     * Obtiene el valor de la propiedad ejbRefName.
     * 
     * @return
     *     possible object is
     *     {@link EjbRefNameType }
     *     
     */
    public EjbRefNameType getEjbRefName() {
        return ejbRefName;
    }

    /**
     * Define el valor de la propiedad ejbRefName.
     * 
     * @param value
     *     allowed object is
     *     {@link EjbRefNameType }
     *     
     */
    public void setEjbRefName(EjbRefNameType value) {
        this.ejbRefName = value;
    }

    /**
     * Obtiene el valor de la propiedad ejbRefType.
     * 
     * @return
     *     possible object is
     *     {@link EjbRefTypeType }
     *     
     */
    public EjbRefTypeType getEjbRefType() {
        return ejbRefType;
    }

    /**
     * Define el valor de la propiedad ejbRefType.
     * 
     * @param value
     *     allowed object is
     *     {@link EjbRefTypeType }
     *     
     */
    public void setEjbRefType(EjbRefTypeType value) {
        this.ejbRefType = value;
    }

    /**
     * Obtiene el valor de la propiedad home.
     * 
     * @return
     *     possible object is
     *     {@link HomeType }
     *     
     */
    public HomeType getHome() {
        return home;
    }

    /**
     * Define el valor de la propiedad home.
     * 
     * @param value
     *     allowed object is
     *     {@link HomeType }
     *     
     */
    public void setHome(HomeType value) {
        this.home = value;
    }

    /**
     * Obtiene el valor de la propiedad remote.
     * 
     * @return
     *     possible object is
     *     {@link RemoteType }
     *     
     */
    public RemoteType getRemote() {
        return remote;
    }

    /**
     * Define el valor de la propiedad remote.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteType }
     *     
     */
    public void setRemote(RemoteType value) {
        this.remote = value;
    }

    /**
     * Obtiene el valor de la propiedad ejbLink.
     * 
     * @return
     *     possible object is
     *     {@link EjbLinkType }
     *     
     */
    public EjbLinkType getEjbLink() {
        return ejbLink;
    }

    /**
     * Define el valor de la propiedad ejbLink.
     * 
     * @param value
     *     allowed object is
     *     {@link EjbLinkType }
     *     
     */
    public void setEjbLink(EjbLinkType value) {
        this.ejbLink = value;
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
