//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.03 a las 04:45:20 PM CET 
//


package com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 	
 * 
 * 	  The ejb-linkType is used by ejb-link
 * 	  elements in the ejb-ref or ejb-local-ref elements to specify
 * 	  that an EJB reference is linked to enterprise bean.
 * 
 * 	  The value of the ejb-link element must be the ejb-name of an
 * 	  enterprise bean in the same ejb-jar file or in another ejb-jar
 * 	  file in the same Java EE application unit.
 * 
 * 	  Alternatively, the name in the ejb-link element may be
 * 	  composed of a path name specifying the ejb-jar containing the
 * 	  referenced enterprise bean with the ejb-name of the target
 * 	  bean appended and separated from the path name by "#".  The
 * 	  path name is relative to the Deployment File containing
 * 	  Deployment Component that is referencing the enterprise
 * 	  bean.  This allows multiple enterprise beans with the same
 * 	  ejb-name to be uniquely identified.
 * 
 * 	  Examples:
 * 
 * 	      <ejb-link>EmployeeRecord</ejb-link>
 * 
 * 	      <ejb-link>../products/product.jar#ProductEJB</ejb-link>
 * 
 * 	  
 *       
 * 
 * <p>Clase Java para ejb-linkType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ejb-linkType"&gt;
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
@XmlType(name = "ejb-linkType")
public class EjbLinkType
    extends String
{


}
