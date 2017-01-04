import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXA extends DefaultHandler {

	private int compteurElements;
	private int nombreAttributs;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Nombre d'éléments : " + this.compteurElements);
		System.out.println("Nombre d'attributs : " + this.nombreAttributs);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		this.compteurElements++;
		for(int i=0; i < atts.getLength(); i++){
			this.nombreAttributs++;
		}
	}
	
}
