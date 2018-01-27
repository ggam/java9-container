//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * 	The message-destination-linkType is used to link a message
 * 	destination reference or message-driven bean to a message
 * 	destination.
 * 
 * 	The Assembler sets the value to reflect the flow of messages
 * 	between producers and consumers in the application.
 * 
 * 	The value must be the message-destination-name of a message
 * 	destination in the same Deployment File or in another
 * 	Deployment File in the same Java EE application unit.
 * 
 * 	Alternatively, the value may be composed of a path name
 * 	specifying a Deployment File containing the referenced
 * 	message destination with the message-destination-name of the
 * 	destination appended and separated from the path name by
 * 	"#". The path name is relative to the Deployment File
 * 	containing Deployment Component that is referencing the
 * 	message destination.  This allows multiple message
 * 	destinations with the same name to be uniquely identified.
 * 
 *       
 * 
 * <p>Clase Java para message-destination-linkType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="message-destination-linkType"&gt;
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
@XmlType(name = "message-destination-linkType")
public class MessageDestinationLinkType
    extends String
{


}
