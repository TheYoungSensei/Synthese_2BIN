import java.io.File;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

public class MainCourseSAX {

	public static void main(String[] args) {
		try {
			File inputFile = new File("course.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
			CourseSAX userHandler = new CourseSAX();
			saxParser.parse(inputFile, userHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
