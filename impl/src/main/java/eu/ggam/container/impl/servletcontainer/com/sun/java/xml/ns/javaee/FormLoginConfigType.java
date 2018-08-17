//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee;

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
 * 	The form-login-configType specifies the login and error
 * 	pages that should be used in form based login. If form based
 * 	authentication is not used, these elements are ignored.
 * 
 * 	Used in: login-config
 * 
 *       
 * 
 * <p>Clase Java para form-login-configType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="form-login-configType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="form-login-page" type="{http://java.sun.com/xml/ns/javaee}war-pathType"/&gt;
 *         &lt;element name="form-error-page" type="{http://java.sun.com/xml/ns/javaee}war-pathType"/&gt;
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
@XmlType(name = "form-login-configType", propOrder = {
    "formLoginPage",
    "formErrorPage"
})
public class FormLoginConfigType {

    @XmlElement(name = "form-login-page", required = true)
    protected WarPathType formLoginPage;
    @XmlElement(name = "form-error-page", required = true)
    protected WarPathType formErrorPage;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected java.lang.String id;

    /**
     * Obtiene el valor de la propiedad formLoginPage.
     * 
     * @return
     *     possible object is
     *     {@link WarPathType }
     *     
     */
    public WarPathType getFormLoginPage() {
        return formLoginPage;
    }

    /**
     * Define el valor de la propiedad formLoginPage.
     * 
     * @param value
     *     allowed object is
     *     {@link WarPathType }
     *     
     */
    public void setFormLoginPage(WarPathType value) {
        this.formLoginPage = value;
    }

    /**
     * Obtiene el valor de la propiedad formErrorPage.
     * 
     * @return
     *     possible object is
     *     {@link WarPathType }
     *     
     */
    public WarPathType getFormErrorPage() {
        return formErrorPage;
    }

    /**
     * Define el valor de la propiedad formErrorPage.
     * 
     * @param value
     *     allowed object is
     *     {@link WarPathType }
     *     
     */
    public void setFormErrorPage(WarPathType value) {
        this.formErrorPage = value;
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
