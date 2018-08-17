//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * 	An injection target specifies a class and a name within
 * 	that class into which a resource should be injected.
 * 
 * 	The injection target class specifies the fully qualified
 * 	class name that is the target of the injection.  The
 * 	Java EE specifications describe which classes can be an
 * 	injection target.
 * 
 * 	The injection target name specifies the target within
 * 	the specified class.  The target is first looked for as a
 * 	JavaBeans property name.  If not found, the target is
 * 	looked for as a field name.
 * 
 * 	The specified resource will be injected into the target
 * 	during initialization of the class by either calling the
 * 	set method for the target property or by setting a value
 * 	into the named field.
 * 
 *       
 * 
 * <p>Clase Java para injection-targetType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="injection-targetType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="injection-target-class" type="{http://java.sun.com/xml/ns/javaee}fully-qualified-classType"/&gt;
 *         &lt;element name="injection-target-name" type="{http://java.sun.com/xml/ns/javaee}java-identifierType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "injection-targetType", propOrder = {
    "injectionTargetClass",
    "injectionTargetName"
})
public class InjectionTargetType {

    @XmlElement(name = "injection-target-class", required = true)
    protected FullyQualifiedClassType injectionTargetClass;
    @XmlElement(name = "injection-target-name", required = true)
    protected JavaIdentifierType injectionTargetName;

    /**
     * Obtiene el valor de la propiedad injectionTargetClass.
     * 
     * @return
     *     possible object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public FullyQualifiedClassType getInjectionTargetClass() {
        return injectionTargetClass;
    }

    /**
     * Define el valor de la propiedad injectionTargetClass.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyQualifiedClassType }
     *     
     */
    public void setInjectionTargetClass(FullyQualifiedClassType value) {
        this.injectionTargetClass = value;
    }

    /**
     * Obtiene el valor de la propiedad injectionTargetName.
     * 
     * @return
     *     possible object is
     *     {@link JavaIdentifierType }
     *     
     */
    public JavaIdentifierType getInjectionTargetName() {
        return injectionTargetName;
    }

    /**
     * Define el valor de la propiedad injectionTargetName.
     * 
     * @param value
     *     allowed object is
     *     {@link JavaIdentifierType }
     *     
     */
    public void setInjectionTargetName(JavaIdentifierType value) {
        this.injectionTargetName = value;
    }

}
