package gov.digital.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class TimedRequest extends TimerTask {
	
	//this query reveals recalls begin in 1966: http://api.usa.gov/recalls/search.json?start_date=0000-01-01&end_date=1966-01-19
	private static final Calendar EARLIEST_RECALL_DATE = new GregorianCalendar(1966, 0, 19);
	//the data source including sort and results per page query params
	private static final String BASE_URL = "http://api.usa.gov/recalls/search.json?sort=date&per_page=50";
	//1000 = 50 data items per page * 20 max pages
	private static final int MAX_RESULTS = 1000;
	
	private String url;
	private String response;
	Calendar end_date;
	Calendar start_date;
	String org;
	

	/**
	 * constructor
	 * @param org
	 */
	public TimedRequest(String org) {
		this.org = org;
		this.end_date = new GregorianCalendar();
		this.start_date = new GregorianCalendar();
		this.start_date.set(Calendar.MONTH, end_date.get(Calendar.MONTH) - 1);
		new TimedRequest(org, start_date, end_date);
	}
	
	/**
	 * actionable constructor
	 * @param org
	 * @param start_date
	 * @param end_date
	 */
	public TimedRequest(String org, Calendar start_date, Calendar end_date) {
		this.end_date = end_date;
		this.start_date = start_date;
		this.org = org;
		this.setUrl(org, start_date, end_date);
		Timer timer = new Timer();
		timer.schedule(this, 1000);
	}
	
	/**
	 * TimerTask run interface
	 */
	public void run() {
		
		System.out.println(this.getUrl());
		this.end_date.set(Calendar.MONTH, end_date.get(Calendar.MONTH) - 1);
		this.start_date.set(Calendar.MONTH, start_date.get(Calendar.MONTH) - 1);
		new TimedRequest(org, start_date, end_date);
		
		/* maxResults = 1000
		 * end = today
		 * start = today - one month
		 * 
		 * do //work backwards from now until zero results
		 *   wrap a web request:
		 *     start a timer
		 *     on timer complete:
		 *       get JSON results
		 *       if(numResults > maxResults)
		 *         split the date range in half
		 *           start = dateBetween(start, end)
		 *           wrap a web request:
		 *       else
		 *         process results
		 *           writeOut(outputFormatWriter)
		 *           start = end + 1 day
		 *           end = end + 1 month
		 *           wrap a web request:
		 * while (numResults > 0)
		 */
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
	public String getUrl() { return this.url; }
	public void setUrl(String organization, Calendar start_date, Calendar end_date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder query = new StringBuilder();
		query.append(BASE_URL);
		query.append("&organization=");
		query.append(organization);
		query.append("&start_date=");
		query.append(df.format(start_date.getTime()));
		query.append("&end_date=");
		query.append(df.format(end_date.getTime()));
		this.url = query.toString();
	}
}
