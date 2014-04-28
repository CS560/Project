/**
 * Pull data from Soap service
 * http://www.cpsc.gov/cgibin/CPSCUpcWS/CPSCUpcSvc.asmx
 *  
 * This document outlines use of the API:
 * http://www.cpsc.gov//Global/info/Recall/requirements.pdf
 * 
 * Depencies (Add to build path)
 * Axis2 - http://axis.apache.org/axis2/java/core/
 * Xerces2 - http://xerces.apache.org/xerces2-j/
 * 
 * Note: org.tempuri package is built by wsdl2java axis2 command line tool
 */

package kdm.data;

import java.awt.List;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.xerces.parsers.DOMParser;
import org.tempuri.CPSCUpcSvcStub;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Start {

	public static void main(String[] args) throws Exception {
		
		String startDate = "2014-03-15";
		String endDate = "2014-03-18";
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		xml += wsdl(startDate, endDate);

		List urls = getUrls(xml);

		for(int i = 0; i < urls.getItemCount(); i++){
			String html = html(urls.getItem(i));
			getCpscData(html);
		}
	}
	public static void getCpscData(String html) {
		char[] c = html.toCharArray();
		System.out.println(c.length);
	}

	/** consume WSDL web service */
	public static String wsdl(String startDate, String endDate) throws Exception {
		CPSCUpcSvcStub stub = new CPSCUpcSvcStub();
		
		//prevents: Exception in thread "main" org.apache.axis2.AxisFault: Transport error: 411 Error: Length Required
		//thanks - http://stackoverflow.com/questions/3770835/org-apache-axis2-axisfault-transport-error-501-error-not-implemented
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);
		
		CPSCUpcSvcStub.GetRecallByDate request = new CPSCUpcSvcStub.GetRecallByDate();
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setUserId("");
		request.setPassword("");
		
		CPSCUpcSvcStub.GetRecallByDateResponse response = stub.getRecallByDate(request);
		
		//as of now, response will print as org.tempuri.CPSCUpcSvcStub$GetRecallByDateResponse@9c8834
		//how to convert to Xml? 
		//Answer: http://axis.8716.n7.nabble.com/axis2-How-to-convert-XMLStreamReader-to-String-td4558.html
		XMLStreamReader reader = response.getPullParser(null); 		
		OMElement omElement = new StAXOMBuilder(reader).getDocumentElement(); 
		String xml = omElement.toStringWithConsume(); 
		
		return xml;
	}
	
	/** fetch HTML source from URL */
	public static String html(String uri) throws Exception {
		URL url = new URL(uri);
		URLConnection spoof = url.openConnection();

		//Spoof the connection so we look like a web browser
		spoof.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)" );
		BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
		String strLine = "";
		String finalHTML = "";
		//Loop through every line in the source
		while ((strLine = in.readLine()) != null){
		   finalHTML += strLine;
		}
		return finalHTML;
	}
	
	/** parse and search XML by node, attribute, or value */
	public static void xml() {
		try {
		    DOMParser parser = new DOMParser();
		    parser.parse("mydocument.xml");
		    Document doc = parser.getDocument();
		 
		    // Get the document's root XML node
		    NodeList root = doc.getChildNodes();
		 
		    // Navigate down the hierarchy to get to the CEO node
		    Node comp = getNode("Company", root);
		    Node exec = getNode("Executive", comp.getChildNodes() );
		    String execType = getNodeAttr("type", exec);
		 
		    // Load the executive's data from the XML
		    NodeList nodes = exec.getChildNodes();
		    String lastName = getNodeValue("LastName", nodes);
		    String firstName = getNodeValue("FirstName", nodes);
		    String street = getNodeValue("street", nodes);
		    String city = getNodeValue("city", nodes);
		    String state = getNodeValue("state", nodes);
		    String zip = getNodeValue("zip", nodes);
		 
		    System.out.println("Executive Information:");
		    System.out.println("Type: " + execType);
		    System.out.println(lastName + ", " + firstName);
		    System.out.println(street);
		    System.out.println(city + ", " + state + " " + zip);
		}
		catch ( Exception e ) {
		    e.printStackTrace();
		}
	}
	
	/** gets the URLs out of the CPSC service response */
	public static List getUrls(String xml) throws Exception {
		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
		Document doc = parser.getDocument();
		
		NodeList root = doc.getChildNodes();
		
		Node getRecallByDateResult = getNode("getRecallByDateResult", root);
		Node message = getNode("message", getRecallByDateResult.getChildNodes());
		Node results = getNode("results", message.getChildNodes());
		Node result = results.getFirstChild();
		
		List urls = new List();
		
		while(result != null) {
			urls.add(getNodeAttr("recallUrl", result));
			result = result.getNextSibling();
		}
		return urls;
	}
	
	/** code from Dr. Dobbs at http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580 */
	
	protected static Node getNode(String tagName, NodeList nodes) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            return node;
	        }
	    }
	 
	    return null;
	}
	protected static String getNodeValue( Node node ) {
	    NodeList childNodes = node.getChildNodes();
	    for (int x = 0; x < childNodes.getLength(); x++ ) {
	        Node data = childNodes.item(x);
	        if ( data.getNodeType() == Node.TEXT_NODE )
	            return data.getNodeValue();
	    }
	    return "";
	}
	protected static String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}
	protected static String getNodeAttr(String attrName, Node node ) {
	    NamedNodeMap attrs = node.getAttributes();
	    for (int y = 0; y < attrs.getLength(); y++ ) {
	        Node attr = attrs.item(y);
	        if (attr.getNodeName().equalsIgnoreCase(attrName)) {
	            return attr.getNodeValue();
	        }
	    }
	    return "";
	}
	protected static String getNodeAttr(String tagName, String attrName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.ATTRIBUTE_NODE ) {
	                    if ( data.getNodeName().equalsIgnoreCase(attrName) )
	                        return data.getNodeValue();
	                }
	            }
	        }
	    }
	 
	    return "";
	}
	
	/** end Dobbs code */

}


/*
 * WSDL client - http://roseindia.net/webservices/axis2/axis2-client.shtml
 * DOM parsing XML - http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580
*/