package six.group.kdm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

@Path("/solr")
public class SolrService {
	
	private String SOLR_URL = "";
	private String SOLR_COLLECTION = "";
	private String MONGO_HOST = "";
	
	/**
	 * Initialize class variables
	 * Invoked any time the REST service is called
	 */
	@PostConstruct
	private void setClassVariables() {
		String pathToXml = getWebInfPath() + "/strings.xml";
		StringsSaxParser parser = new StringsSaxParser(pathToXml);
		Strings stringsObj = parser.getStrings();
		SOLR_URL = stringsObj.getUrl();
		SOLR_COLLECTION = stringsObj.getCollection();
		MONGO_HOST = stringsObj.getMongoHost();
	}
	/**
	 * prints the class variable values
	 * @return
	 */
	@GET
	@Produces("text/html")
	public String getHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append("<div>" + SOLR_URL + "</div>");
		builder.append("<div>" + SOLR_COLLECTION + "</div>");
		builder.append("<div>" + MONGO_HOST + "</div>");
		return builder.toString();
	}

	/**
	 * Issue the *.* query to see all documents
	 * @return
	 */
	@GET
	@Path("/index")
	@Produces("application/json")
	public String getIndex(){
		String query = "select?q=*%3A*&wt=json&indent=true";
		String response = getResponse(SOLR_URL + "/" + SOLR_COLLECTION+ "/" + query);
		return response;
	}
	
	/**
	 * Search for a specific term
	 * @param q
	 * @return
	 */
	@GET
	@Path("search")
	@Produces("application/json")
	public String getSearch(
			@QueryParam("q") String q
			){
		String query = "select?q=" + q + "&wt=json&defType=dismax&qf=title+description";
		String response = getResponse(SOLR_URL + "/" + SOLR_COLLECTION + "/" + query);
		return response;
	}
	
	@GET
	@Path("db/{action}")
	@Produces("application/json")
	public String dbUpdate(@PathParam("action") String action) {
		String notice = "";
		if(action.equalsIgnoreCase("update")) {
			notice = "update requested";
		} else {
			notice = "i don't know what you're trying to do";
		}
		return notice;
	}
	
	/**
	 * hacking a GET to submit data to Solr
	 * @param id
	 * @param name
	 * @param price
	 * @return
	 */
	@GET
	@Path("insert")
	@Produces("application/json")
	public String insert(
			@QueryParam("id") String id,
			@QueryParam("name") String name,
			@QueryParam("price") String price
			) {
		try {
			HttpSolrServer server = new HttpSolrServer(SOLR_URL + "/" + SOLR_COLLECTION + "/");
			server.setParser(new XMLResponseParser());
			SolrInputDocument doc1 = new SolrInputDocument();
		    doc1.addField("id", id, 1.0f );
		    doc1.addField( "name", name, 1.0f );
		    doc1.addField( "price", price);
		    server.add(doc1);
		    server.commit();
		} catch (IOException e) {
			return "{\"status\":\"IOException\"}";
		} catch (SolrServerException e) {
			return "{\"status\":\"SolrServerException\"}";
		} finally {
			return "{\"status\":\"success\"}";
		}
	}
	
	/**
	 * Post JSON formatted Solr document to Solr server
	 * @param document
	 */
	@POST
	@Path("update")
	@Consumes("application/json")
	public void update(String document) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(SOLR_URL + "/" + SOLR_COLLECTION + "/update");
		
		try {
			StringEntity input = new StringEntity(document);
			input.setContentType("application/json");
			post.setEntity(input);
			HttpResponse response = client.execute(post);
		} catch (ClientProtocolException e) {
			
		} catch (UnsupportedEncodingException e) {
			
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * Gets HTTP Response from URL
	 * @param url
	 * @return HTTP Response as String
	 */
	private String getResponse(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		String rString = null;

		//HTTP request
		try {
			HttpGet get = new HttpGet(url);
			response = client.execute(get);
			StatusLine status = response.getStatusLine();

			if(status.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				response.getEntity().writeTo(output);
				output.close();
				rString = output.toString();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//close client?
		}
		
		return rString;
	}
	
	/**
	 * Wrap JSON strings as a JavaScript callback method
	 * @param response
	 * @return
	 */
	private String wrapAsCallback(String response) {
		return "callback(" + response + ")";
	}
	
	/**
	 * Get the path of the WEB-INF folder in your application
	 * 		http://melandri.net/2009/05/28/get-the-web-inf-folder-path/
	 * Alternatively, get the Glassfish global configuration path
	 * 		http://www.shirmanov.com/2011/05/read-file-from-glassfish-domain.html
	 * @return The path to this application's WEB-INF folder
	 */
	private String getWebInfPath() {
		String WEBINF = "WEB-INF";
		String filePath = "";
		URL url = SolrService.class.getResource("SolrService.class");
		String className = url.getFile();
		filePath = className.substring(0, className.indexOf(WEBINF) + WEBINF.length());
		return filePath;
	}
}
