/*
 * info on search relevancy:
 * https://wiki.apache.org/solr/SolrRelevancyFAQ
 */

package sos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

@Path("solr")
public class SolResource {

	private static final String SOLR_URL = "http://192.168.56.101:8983/solr";
	
	@GET
	@Path("index")
	@Produces("application/x-javascript")
	public String getIndex(){

		String solr = SOLR_URL + "/newsfeeds_shard1_replica1/select?q=*%3A*&wt=json&indent=true";
		//String solr = SOLR_URL + "/texthtml_shard1_replica1/select?q=*%3A*&wt=json&indent=true";

		String response = getResponse(solr);
		return wrapAsCallback(response);
	}
	
	@GET
	@Path("search")
	@Produces("application/x-javascript")
	public String getSearch(
			@QueryParam("q") String q
			){
		
		//example of working query:
		//http://192.168.56.101:8983/solr/newsfeeds_shard1_replica1/select?q=children&wt=json&indent=true&defType=dismax&qf=title+description
		String url = SOLR_URL;
		String collection = "/newsfeeds_shard1_replica1";
		String query = "/select?q=" + q + "&wt=json&defType=dismax&qf=title+description";

		String response = getResponse(url + collection + query);
		return wrapAsCallback(response);
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
	private String wrapAsCallback(String response) {
		return "callback(" + response + ")";
	}
}
