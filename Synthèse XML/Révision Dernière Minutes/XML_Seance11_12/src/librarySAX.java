import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class librarySAX extends DefaultHandler {

	int compteurElem = 0;
	int compteurAtt = 0;
	@Override
	public void endDocument() throws SAXException {
		System.out.println("Nombre d'éléments : " + compteurElem);
		System.out.println("Nombre d'attributs : " + compteurAtt);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		compteurElem++;
		for(int i = 0; i < atts.getLength(); i++){
			compteurAtt++;
		}
	}
	
}
