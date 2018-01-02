//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0-b170531.0717 
// Visite <a href="https://jaxb.java.net/">https://jaxb.java.net/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.01.02 a las 07:48:19 PM CET 
//


package com.sun.java.xml.ns.javaee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 	
 * 
 * 	  The ejb-ref-name element contains the name of an EJB
 * 	  reference. The EJB reference is an entry in the
 * 	  Deployment Component's environment and is relative to the
 * 	  java:comp/env context.  The name must be unique within the
 * 	  Deployment Component.
 * 
 * 	  It is recommended that name is prefixed with "ejb/".
 * 
 * 	  Example:
 * 
 * 	  <ejb-ref-name>ejb/Payroll</ejb-ref-name>
 * 
 * 	  
 *       
 * 
 * <p>Clase Java para ejb-ref-nameType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ejb-ref-nameType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;restriction base="&lt;http://java.sun.com/xml/ns/javaee&gt;jndi-nameType"&gt;
 *     &lt;/restriction&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ejb-ref-nameType")
public class EjbRefNameType
    extends JndiNameType
{


}
