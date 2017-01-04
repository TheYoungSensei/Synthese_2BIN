import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainSAXC {
	
	public static void main(String args[]){
		try {
			File inputFile = new File("course.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXC userHandler = new SAXC();
			saxParser.parse(inputFile, userHandler);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}