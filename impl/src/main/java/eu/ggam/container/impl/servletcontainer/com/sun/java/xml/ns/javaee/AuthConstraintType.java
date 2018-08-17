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
 * 	The auth-constraintType indicates the user roles that
 * 	should be permitted access to this resource
 * 	collection. The role-name used here must either correspond
 * 	to the role-name of one of the security-role elements
 * 	defined for this web application, or be the specially
 * 	reserved role-name "*" that is a compact syntax for
 * 	indicating all roles in the web application. If both "*"
 * 	and rolenames appear, the container interprets this as all
 * 	roles.  If no roles are defined, no user is allowed access
 * 	to the portion of the web application described by the
 * 	containing security-constraint.  The container matches
 * 	role names case sensitively when determining access.
 * 
 *       
 * 
 * <p>Clase Java para auth-constraintType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="auth-constraintType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://java.sun.com/xml/ns/javaee}descriptionType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="role-name" type="{http://java.sun.com/xml/ns/javaee}role-nameType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "auth-constraintType", propOrder = {
    "descriptions",
    "roleNames"
})
public class AuthConstraintType {

    @XmlElement(name = "description")
    protected List<DescriptionType> descriptions;
    @XmlElement(name = "role-name")
    protected List<RoleNameType> roleNames;
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
     * Gets the value of the roleNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RoleNameType }
     * 
     * 
     */
    public List<RoleNameType> getRoleNames() {
        if (roleNames == null) {
            roleNames = new ArrayList<RoleNameType>();
        }
        return this.roleNames;
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
