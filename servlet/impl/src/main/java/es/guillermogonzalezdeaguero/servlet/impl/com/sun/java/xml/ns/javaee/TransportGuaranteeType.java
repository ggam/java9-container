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
 * 	The transport-guaranteeType specifies that the communication
 * 	between client and server should be NONE, INTEGRAL, or
 * 	CONFIDENTIAL. NONE means that the application does not
 * 	require any transport guarantees. A value of INTEGRAL means
 * 	that the application requires that the data sent between the
 * 	client and server be sent in such a way that it can't be
 * 	changed in transit. CONFIDENTIAL means that the application
 * 	requires that the data be transmitted in a fashion that
 * 	prevents other entities from observing the contents of the
 * 	transmission. In most cases, the presence of the INTEGRAL or
 * 	CONFIDENTIAL flag will indicate that the use of SSL is
 * 	required.
 * 
 * 	Used in: user-data-constraint
 * 
 *       
 * 
 * <p>Clase Java para transport-guaranteeType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="transport-guaranteeType"&gt;
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
@XmlType(name = "transport-guaranteeType")
public class TransportGuaranteeType
    extends String
{


}
