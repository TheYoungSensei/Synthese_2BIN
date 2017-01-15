import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class libraryDOM {
	 
	public static void main(String[] args) {
		try { 
			XPathFactory factory = XPathFactory.newInstance();
			javax.xml.xpath.XPath xPath = factory.newXPath();
			NodeList list = (NodeList) xPath.evaluate("//*", new InputSource(new FileReader("library.xml")), XPathConstants.NODESET);
			System.out.println("Nombre d'éléments : " + list.getLength());
			int nombreAtt = 0;
			for(int i = 0; i < list.getLength(); i++) {
				nombreAtt += list.item(i).getAttributes().getLength();
			}
			System.out.println("Nombre Attribut : " + nombreAtt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
