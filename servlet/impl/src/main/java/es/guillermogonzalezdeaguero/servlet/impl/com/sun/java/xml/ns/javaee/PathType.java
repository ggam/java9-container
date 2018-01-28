//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * 	The elements that use this type designate either a relative
 * 	path or an absolute path starting with a "/".
 * 
 * 	In elements that specify a pathname to a file within the
 * 	same Deployment File, relative filenames (i.e., those not
 * 	starting with "/") are considered relative to the root of
 * 	the Deployment File's namespace.  Absolute filenames (i.e.,
 * 	those starting with "/") also specify names in the root of
 * 	the Deployment File's namespace.  In general, relative names
 * 	are preferred.  The exception is .war files where absolute
 * 	names are preferred for consistency with the Servlet API.
 * 
 *       
 * 
 * <p>Clase Java para pathType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="pathType"&gt;
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
@XmlType(name = "pathType")
@XmlSeeAlso({
    JspFileType.class
})
public class PathType
    extends String
{


}
