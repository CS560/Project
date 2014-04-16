package gov.digital.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class TimedRequest extends TimerTask {
	private String url;
	private String response;
	
	/**
	 * constructors
	 */
	public TimedRequest() { }
	public TimedRequest(String url) {
		this.setUrl(url);
	}
	
	/**
	 * TimerTask run interface
	 */
	public void run() {
		System.out.println("timer done");
		//this.response = getResponse(url);
	}
	
	/**
	 * Get HTTP response from any URL
	 * @param url
	 * @return
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
	 * accessors
	 */
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public String getResponse() { return response; }
}
