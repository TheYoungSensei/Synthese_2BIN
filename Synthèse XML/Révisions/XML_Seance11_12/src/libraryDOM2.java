import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class libraryDOM2 {
	 
	public static void main(String[] args) {
		try { 
			XPathFactory factory = XPathFactory.newInstance();
			javax.xml.xpath.XPath xPath = factory.newXPath();
			NodeList list = (NodeList) xPath.evaluate("//list", new InputSource(new FileReader("library.xml")), XPathConstants.NODESET);
			for(int i = 0; i < list.getLength(); i++) {
				Element elem = (Element) list.item(i);
				System.out.println(elem.getAttribute("name"));
				NodeList tracks = elem.getElementsByTagName("track_ID");
				for(int j = 0; j < tracks.getLength(); j++) {
					Element track = (Element) tracks.item(j);
					Node artist = (Node) xPath.evaluate("//song[track_ID = " + track.getTextContent() + "]/artist", new InputSource(new FileReader("library.xml")), XPathConstants.NODE);
					Node name = (Node) xPath.evaluate("//song[track_ID = " + track.getTextContent() + "]/name", new InputSource(new FileReader("library.xml")), XPathConstants.NODE);
					System.out.println(artist.getTextContent() +  " - " + name.getTextContent());
				}
				System.out.println("----------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
