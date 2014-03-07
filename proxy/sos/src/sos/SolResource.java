package sos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


@Path("sol")
public class SolResource {

	@GET
	@Produces("application/x-javascript")
	public String getSolr(){
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		String rString = null;

		String solr = "http://192.168.56.101:8983/solr/newsfeeds_shard1_replica1/select?q=*%3A*&wt=json&indent=true";
		//String solr = "http://192.168.1.14:8983/solr/texthtml_shard1_replica1/select?q=*%3A*&wt=json&indent=true";

		//HTTP request
		try {
			HttpGet get = new HttpGet(solr);
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
		}
		return "callback(" + rString + ")";
	}
}
