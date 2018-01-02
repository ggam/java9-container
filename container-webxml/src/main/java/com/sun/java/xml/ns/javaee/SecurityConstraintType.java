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
 * 	The security-constraintType is used to associate
 * 	security constraints with one or more web resource
 * 	collections
 * 
 * 	Used in: web-app
 * 
 *       
 * 
 * <p>Clase Java para security-constraintType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="security-constraintType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="display-name" type="{http://java.sun.com/xml/ns/javaee}display-nameType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="web-resource-collection" type="{http://java.sun.com/xml/ns/javaee}web-resource-collectionType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="auth-constraint" type="{http://java.sun.com/xml/ns/javaee}auth-constraintType" minOccurs="0"/&gt;
 *         &lt;element name="user-data-constraint" type="{http://java.sun.com/xml/ns/javaee}user-data-constraintType" minOccurs="0"/&gt;
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
@XmlType(name = "security-constraintType", propOrder = {
    "displayName",
    "webResourceCollection",
    "authConstraint",
    "userDataConstraint"
})
public class SecurityConstraintType {

    @XmlElement(name = "display-name")
    protected List<DisplayNameType> displayName;
    @XmlElement(name = "web-resource-collection", required = true)
    protected List<WebResourceCollectionType> webResourceCollection;
    @XmlElement(name = "auth-constraint")
    protected AuthConstraintType authConstraint;
    @XmlElement(name = "user-data-constraint")
    protected UserDataConstraintType userDataConstraint;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Gets the value of the displayName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DisplayNameType }
     * 
     * 
     */
    public List<DisplayNameType> getDisplayName() {
        if (displayName == null) {
            displayName = new ArrayList<DisplayNameType>();
        }
        return this.displayName;
    }

    /**
     * Gets the value of the webResourceCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the webResourceCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWebResourceCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WebResourceCollectionType }
     * 
     * 
     */
    public List<WebResourceCollectionType> getWebResourceCollection() {
        if (webResourceCollection == null) {
            webResourceCollection = new ArrayList<WebResourceCollectionType>();
        }
        return this.webResourceCollection;
    }

    /**
     * Obtiene el valor de la propiedad authConstraint.
     * 
     * @return
     *     possible object is
     *     {@link AuthConstraintType }
     *     
     */
    public AuthConstraintType getAuthConstraint() {
        return authConstraint;
    }

    /**
     * Define el valor de la propiedad authConstraint.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthConstraintType }
     *     
     */
    public void setAuthConstraint(AuthConstraintType value) {
        this.authConstraint = value;
    }

    /**
     * Obtiene el valor de la propiedad userDataConstraint.
     * 
     * @return
     *     possible object is
     *     {@link UserDataConstraintType }
     *     
     */
    public UserDataConstraintType getUserDataConstraint() {
        return userDataConstraint;
    }

    /**
     * Define el valor de la propiedad userDataConstraint.
     * 
     * @param value
     *     allowed object is
     *     {@link UserDataConstraintType }
     *     
     */
    public void setUserDataConstraint(UserDataConstraintType value) {
        this.userDataConstraint = value;
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
