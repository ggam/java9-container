//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * 	The dispatcher has four legal values: FORWARD, REQUEST, INCLUDE,
 * 	and ERROR. A value of FORWARD means the Filter will be applied
 * 	under RequestDispatcher.forward() calls.  A value of REQUEST
 * 	means the Filter will be applied under ordinary client calls to
 * 	the path or servlet. A value of INCLUDE means the Filter will be
 * 	applied under RequestDispatcher.include() calls.  A value of
 * 	ERROR means the Filter will be applied under the error page
 * 	mechanism.  The absence of any dispatcher elements in a
 * 	filter-mapping indicates a default of applying filters only under
 * 	ordinary client calls to the path or servlet.
 * 
 *       
 * 
 * <p>Clase Java para dispatcherType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dispatcherType"&gt;
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
@XmlType(name = "dispatcherType")
public class DispatcherType
    extends String
{


}
