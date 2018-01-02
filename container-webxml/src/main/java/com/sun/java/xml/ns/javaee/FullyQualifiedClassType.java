//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.02 a las 07:48:19 PM CET 
//


package com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * 	The elements that use this type designate the name of a
 * 	Java class or interface.  The name is in the form of a
 * 	"binary name", as defined in the JLS.  This is the form
 * 	of name used in Class.forName().  Tools that need the
 * 	canonical name (the name used in source code) will need
 * 	to convert this binary name to the canonical name.
 * 
 *       
 * 
 * <p>Clase Java para fully-qualified-classType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="fully-qualified-classType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;restriction base="&lt;http://java.sun.com/xml/ns/javaee&gt;string"&gt;
 *     &lt;/restriction&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fully-qualified-classType")
@XmlSeeAlso({
    HomeType.class,
    LocalHomeType.class,
    LocalType.class,
    MessageDestinationTypeType.class,
    RemoteType.class
})
public class FullyQualifiedClassType
    extends String
{


}
