import java.io.FileReader;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DOMA {
	
	public static void main(String[] args) throws Exception{
		XPathFactory factory = XPathFactory.newInstance();
		javax.xml.xpath.XPath xPath = factory.newXPath();
		NodeList lists = (NodeList) xPath.evaluate("//*", new InputSource(new FileReader("library.xml")), XPathConstants.NODESET);
		int compteurElem = lists.getLength();
		int compteurAtt = 0;
		for(int i = 0; i < lists.getLength(); i++){
			compteurAtt += ((Element) lists.item(i)).getAttributes().getLength();
		}
		System.out.println("Nombre d'éléments : " + compteurElem);
		System.out.println("Nombre d'attributs : " + compteurAtt);
	}
}
