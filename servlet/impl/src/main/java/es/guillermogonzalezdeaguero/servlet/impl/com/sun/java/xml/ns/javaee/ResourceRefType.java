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
 * 
 * 	  The resource-refType contains a declaration of a
 * 	  Deployment Component's reference to an external resource. It
 * 	  consists of an optional description, the resource manager
 * 	  connection factory reference name, an optional indication of
 * 	  the resource manager connection factory type expected by the
 * 	  Deployment Component code, an optional type of authentication
 * 	  (Application or Container), and an optional specification of
 * 	  the shareability of connections obtained from the resource
 * 	  (Shareable or Unshareable).
 * 
 * 	  It also includes optional elements to define injection of
 * 	  the named resource into fields or JavaBeans properties.
 * 
 * 	  The connection factory type must be supplied unless an
 * 	  injection target is specified, in which case the type
 * 	  of the target is used.  If both are specified, the type
 * 	  must be assignment compatible with the type of the injection
 * 	  target.
 * 
 * 	  Example:
 * 
 * 	  <resource-ref>
 * 	      <res-ref-name>jdbc/EmployeeAppDB</res-ref-name>
 * 	      <res-type>javax.sql.DataSource</res-type>
 * 	      <res-auth>Container</res-auth>
 * 	      <res-sharing-scope>Shareable</res-sharing-scope>
 * 	  </resource-ref>
 * 
 * 	  
 *       
 * 
 * <p>Clase Java para resource-refType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="resource-refType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="res-ref-name" type="{http://java.sun.com/xml/ns/javaee}jndi-nameType"/&gt;
 *         &lt;element name="res-type" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType" minOccurs="0"/&gt;
 *         &lt;element name="res-auth" type="{http://java.sun.com/xml/ns/javaee}res-authType" minOccurs="0"/&gt;
 *         &lt;element name="res-sharing-scope" type="{http://java.sun.com/xml/ns/javaee}res-sharing-scopeType" minOccurs="0"/&gt;
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
@XmlType(name = "resource-refType", propOrder = {
    "descriptions",
    "resRefName",
    "resType",
    "resAuth",
    "resSharingScope",
    "mappedName",
    "injectionTargets"
})
public class ResourceRefType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "res-ref-name", required = true)
    protected JndiNameType resRefName;
    @XmlElement(name = "res-type")
    protected FullyQualifiedClassType resType;
    @XmlElement(name = "res-auth")
    protected ResAuthType resAuth;
    @XmlElement(name = "res-sharing-scope")
    protected ResSharingScopeType resSharingScope;
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
     * Obtiene el valor de la propiedad resRefName.
     * 
     * @return
     *     possible object is
     *     {@link JndiNameType }
     *     
     */
    public JndiNameType getResRefName() {
        return resRefName;
    }

    /**
     * Define el valor de la propiedad resRefName.
     * 
     * @param value
     *     allowed object is
     *     {@link JndiNameType }
     *     
     */
    public void setResRefName(JndiNameType value) {
        this.resRefName = value;
    }

    /**
     * Obtiene el valor de la propiedad resType.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getResType() {
        return resType;
    }

    /**
     * Define el valor de la propiedad resType.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setResType(FullyQualifiedClassType value) {
        this.resType = value;
    }

    /**
     * Obtiene el valor de la propiedad resAuth.
     * 
     * @return
     *     possible object is
     *     {@link ResAuthType }
     *     
     */
    public ResAuthType getResAuth() {
        return resAuth;
    }

    /**
     * Define el valor de la propiedad resAuth.
     * 
     * @param value
     *     allowed object is
     *     {@link ResAuthType }
     *     
     */
    public void setResAuth(ResAuthType value) {
        this.resAuth = value;
    }

    /**
     * Obtiene el valor de la propiedad resSharingScope.
     * 
     * @return
     *     possible object is
     *     {@link ResSharingScopeType }
     *     
     */
    public ResSharingScopeType getResSharingScope() {
        return resSharingScope;
    }

    /**
     * Define el valor de la propiedad resSharingScope.
     * 
     * @param value
     *     allowed object is
     *     {@link ResSharingScopeType }
     *     
     */
    public void setResSharingScope(ResSharingScopeType value) {
        this.resSharingScope = value;
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
