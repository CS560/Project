package six.group.kdm;

import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/solr")
public class SolrService {

	/* Get the path of the WEB-INF folder in your application
	 * 		http://melandri.net/2009/05/28/get-the-web-inf-folder-path/
	 * Alternatively, get the Glassfish global configuration path
	 * 		http://www.shirmanov.com/2011/05/read-file-from-glassfish-domain.html
	 */
	private String getWebInfPath() {
		String WEBINF = "WEB-INF";
		String filePath = "";
		URL url = SolrService.class.getResource("SolrService.class");
		String className = url.getFile();
		filePath = className.substring(0, className.indexOf(WEBINF) + WEBINF.length());
		return filePath;
	}
	/**
	 * Get the web address of Solr Server from strings.xml
	 * @return Solr server URL
	 */
	private String getSolrUrl() {
		String pathToXml = getWebInfPath() + "/strings.xml";
		StringsSaxParser parser = new StringsSaxParser(pathToXml);
		return parser.getSolrUrl();
	}
}
