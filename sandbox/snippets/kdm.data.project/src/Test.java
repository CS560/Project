import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		doPost("someting said");
	}
	public static void doGet() throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://localhost:8080/kdm.data/rest/hello");
		HttpResponse response = client.execute(request);
		
		//get response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		String line = "";
		while((line = rd.readLine()) != null) {
			System.out.println(line);
		}
	}
	public static void doPost(String message) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://localhost:8080/kdm.data/rest/hello");
		
		StringEntity input = new StringEntity(message);
		input.setContentType("application/json");
		post.setEntity(input);
		
		//List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("msg", "my message"));
		//post.setHeader("Content-type", "application/json");
	    //post.setHeader("Accept", "application/json");
		
		
		
		HttpResponse response = client.execute(post);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		String line = "";
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	}

}
