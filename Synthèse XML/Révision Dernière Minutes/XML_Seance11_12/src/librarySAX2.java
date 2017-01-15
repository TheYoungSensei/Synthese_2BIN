import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Node;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class librarySAX2 extends DefaultHandler {

	
	private boolean estArtiste;
	private boolean estName;
	private String artiste;
	private String name;
	private boolean estTrack;
	private String track;
	private HashMap<String, String> infos = new HashMap<String, String>();

	@Override
	public void endDocument() throws SAXException {
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if(qName.equals("artist")){
			this.estArtiste = true;
		} else if (qName.equals("name")) {
			this.estName = true;
		} else if (qName.equals("track_ID")) {
			this.estTrack = true;
		}
	}
	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("song")){
				this.infos.put(track, artiste + " - " + name);
		} else if (qName.equals("artist")) {
			this.estArtiste = false;
		} else if (qName.equals("name")) {
			this.estName = false;
		} else if (qName.equals("list")) {
			System.out.println("-------------------------------");
		} else if (qName.equals("track_ID")) {
			this.estTrack = false;
			if(this.infos.containsKey(track)) {
				System.out.println(infos.get(track));
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(estArtiste) {
			this.artiste = new String(ch, start, length);
		} else if (estName) {
			this.name = new String(ch, start, length);
		} else if (estTrack) {
			this.track = new String(ch, start, length);
		}
	}
	
	
	
}