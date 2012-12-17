package xml;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XmlUtil {

	/**
	 * XML TO STRING
	 * @param element
	 * @return
	 */
	public static String getXmlToString(Element element, String charSet) throws Exception {
		Document doc = new Document(element);
		
		Format f = Format.getCompactFormat();
		f.setEncoding(charSet);
		f.setIndent("	");
		f.setLineSeparator("\r\n");
		f.setTextMode(Format.TextMode.TRIM);
		
		XMLOutputter xmlout = new XMLOutputter();
		xmlout.setFormat(f);
		return xmlout.outputString(doc);	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}
