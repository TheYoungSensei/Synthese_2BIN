import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainLibrarySAX {
	
	public static void main(String[] args){
		try {
			File inputFile = new File("library.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			librarySAX2 userhandler = new librarySAX2();
			saxParser.parse(inputFile, userhandler);
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
}
