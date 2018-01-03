//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * 	The lifecycle-callback type specifies a method on a
 * 	class to be called when a lifecycle event occurs.
 * 	Note that each class may have only one lifecycle callback
 *         method for any given event and that the method may not
 * 	be overloaded.
 * 
 *         If the lifefycle-callback-class element is missing then
 *         the class defining the callback is assumed to be the
 *         component class in scope at the place in the descriptor
 *         in which the callback definition appears.
 * 
 *       
 * 
 * <p>Clase Java para lifecycle-callbackType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="lifecycle-callbackType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="lifecycle-callback-class" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType" minOccurs="0"/&gt;
 *         &lt;element name="lifecycle-callback-method" type="{http://java.sun.com/xml/ns/javaee}java-identifierType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lifecycle-callbackType", propOrder = {
    "lifecycleCallbackClass",
    "lifecycleCallbackMethod"
})
public class LifecycleCallbackType {

    @XmlElement(name = "lifecycle-callback-class")
    protected FullyQualifiedClassType lifecycleCallbackClass;
    @XmlElement(name = "lifecycle-callback-method", required = true)
    protected JavaIdentifierType lifecycleCallbackMethod;

    /**
     * Obtiene el valor de la propiedad lifecycleCallbackClass.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getLifecycleCallbackClass() {
        return lifecycleCallbackClass;
    }

    /**
     * Define el valor de la propiedad lifecycleCallbackClass.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setLifecycleCallbackClass(FullyQualifiedClassType value) {
        this.lifecycleCallbackClass = value;
    }

    /**
     * Obtiene el valor de la propiedad lifecycleCallbackMethod.
     * 
     * @return
     *     possible object is
     *     {@link JavaIdentifierType }
     *     
     */
    public JavaIdentifierType getLifecycleCallbackMethod() {
        return lifecycleCallbackMethod;
    }

    /**
     * Define el valor de la propiedad lifecycleCallbackMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link JavaIdentifierType }
     *     
     */
    public void setLifecycleCallbackMethod(JavaIdentifierType value) {
        this.lifecycleCallbackMethod = value;
    }

}
