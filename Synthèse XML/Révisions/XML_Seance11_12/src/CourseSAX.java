import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Node;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class CourseSAX extends DefaultHandler {
	
	private boolean estCoureuse;
	private String coureuse;

	@Override
	public void endDocument() throws SAXException {
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(estCoureuse) {
			estCoureuse = false;
			System.out.println(coureuse);
			System.out.println("-----------------------------");
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if(qName.equals("coureur") && atts.getValue("sexe").equals("femme")) {
			this.estCoureuse = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(estCoureuse) {
			this.coureuse = new String(ch, start, length);
		}
	}
	
	

}
