package es.guillermogonzalezdeaguero.container.webxml;

import com.sun.java.xml.ns.javaee.WebAppType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author guillermo
 */
public class App {

    public static void main(String[] args) throws JAXBException {
        JAXBContext newInstance = JAXBContext.newInstance(WebAppType.class);
        Unmarshaller createUnmarshaller = newInstance.createUnmarshaller();
        JAXBElement unmarshal = (JAXBElement)createUnmarshaller.unmarshal(App.class.getClassLoader().getResource("web.xml"));
        
        System.out.println(((WebAppType)unmarshal.getValue()).getDescriptionAndDisplayNameAndIcon());
        
    }
}
