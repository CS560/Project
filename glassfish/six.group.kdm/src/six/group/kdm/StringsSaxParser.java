/* @this.clark
 * adapted from
 * http://www.javacodegeeks.com/2012/01/xml-parsing-using-saxparser-with.html
 * 13-apr-2014
 */

package six.group.kdm;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StringsSaxParser extends DefaultHandler {
	private Strings stringsObj;
	private String url;
	private String file;
	private String temp;
	
	/**
	 * Default Constructor
	 * @param stringsXmlFileName
	 */
	public StringsSaxParser(String file) {
		this.file = file;
		parseDocument();
	}
	
	/**
	 * Parses an XML file into memory
	 */
	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException: ");
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("SAXException: ");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Parser starts reading an element
	 */
	@Override
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
		//create a new StringsXml object for the outer element, <solr>
		if(elementName.equalsIgnoreCase("solr")) {
			stringsObj = new Strings();
		}
	}
	
	/**
	 * Parser finishes reading an element
	 */
	@Override
	public void endElement(String s, String s1, String element) throws SAXException {
		//set the solr url value in StringsXml object
		if(element.equalsIgnoreCase("url")) {
			stringsObj.setUrl(temp);
		}
		if(element.equalsIgnoreCase("collection")) {
			stringsObj.setCollection(temp);
		}
		if(element.equalsIgnoreCase("host")) {
			stringsObj.setMongoHost(temp);
		}
	}
	
	/**
	 * Parser reads a string
	 */
	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
		temp = new String(ac, i, j);
	}
	
	public Strings getStrings() {
		return this.stringsObj;
	}
}
