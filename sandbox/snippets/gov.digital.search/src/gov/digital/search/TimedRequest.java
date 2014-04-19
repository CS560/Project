package gov.digital.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class TimedRequest extends TimerTask {
	
	
	private static final DateTime EARLIEST_RECALL_DATE = new DateTime(1966, 1, 19, 0, 0, 0); //this query reveals recalls begin in 1966: http://api.usa.gov/recalls/search.json?start_date=0000-01-01&end_date=1966-01-19
	private static final String BASE_URL = "http://api.usa.gov/recalls/search.json?sort=date&per_page=50"; //the data source including sort and results per page query params
	private static final int MAX_RESULTS = 1000; //1000 = 50 data items per page * 20 max pages
	private static final int DELAY = 4000; //time to wait between sending web requests (don't overwhelm the server)
	private static final int QUERY_TIME_SPAN_MONTHS = 2; //query time span
	
	private String url;
	private int page;
	private int total;
	private DateTime end_date;
	private DateTime start_date;
	private String org;
	

	/**
	 * constructor
	 * @param org
	 */
	public TimedRequest(String org) {
		this.page = 1;
		this.org = org;
		this.end_date = new DateTime();
		this.start_date = new DateTime();
		this.start_date = end_date.minusMonths(QUERY_TIME_SPAN_MONTHS);
		new TimedRequest(org, start_date, end_date, page);
	}
	
	/**
	 * actionable constructor
	 * @param org
	 * @param start_date
	 * @param end_date
	 */
	public TimedRequest(String org, DateTime start_date, DateTime end_date, int page) {
		this.page = page;
		this.end_date = end_date;
		this.start_date = start_date;
		this.org = org;
		this.setUrl(org, start_date, end_date, page);
		
		//start a timer to activate this class
		Timer timer = new Timer();
		timer.schedule(this, DELAY);
	}
	
	public TimedRequest(String org, DateTime start_date, DateTime end_date, int page, int total) {
		this.total = total;
		this.page = page;
		this.end_date = end_date;
		this.start_date = start_date;
		this.org = org;
		this.setUrl(org, start_date, end_date, page);
		
		//start a timer to activate this class
		Timer timer = new Timer();
		timer.schedule(this, DELAY);
	}
	
	/**
	 * TimerTask run interface
	 */
	public void run() {
		
		System.out.println("requesting data from: " + url);
		String response = getResponse(url);
		JSONObject json = new JSONObject(response);
		
		//JSONObject j = json.getJSONObject("success");
		JSONObject success = json.getJSONObject("success");
		if(this.page == 1) {
			total = success.getInt("total");
		} else { 
			//total was set during construction and reduced by the quantity processed during last iteration
		}
		System.out.println("received " + total + " results");
		
		if(total > MAX_RESULTS) {
			//split the date range in half by shifting the start
			long duration = new Duration(start_date, end_date).getStandardDays();
			int days = (int)duration/2;
			start_date = end_date.minusDays(days);
		} else {
			if(total > 50) {
				//default request is page=1, so have this result in 2
				int pages = 1 + total/50;
				System.out.println("> 50 results: pages = " + pages);
				
				//for more than 1 page, start requesting additional pages
				for(int page = 1; page < pages; page++) {
					new TimedRequest(org, start_date, end_date, page + 1, total - 50); //get the next page, reduce internal total by 50
				}
			} else {
				//reset range to months and continue
				end_date = end_date.minusMonths(QUERY_TIME_SPAN_MONTHS);
				start_date = end_date.minusMonths(QUERY_TIME_SPAN_MONTHS);
				new TimedRequest(org, start_date, end_date, 1); //single page request
			}
		}
		//wrap a new request - 1 month spread
		
		
		/* maxResults = 1000
		 * end = today
		 * start = end - month
		 * 
		 * do //work backwards from today until zero results
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
		 *         if results > 50
		 *           pages = 1 + results/2
		 *           from count = 1 to pages do
		 *             wrap a web request:
		 *         else
		 *           end = end - month
		 *           start = end - month
		 *           wrap a web request:
		 *           
		 * while (end > 1966)
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
	public void setUrl(String organization, DateTime start_date, DateTime end_date, int page) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		StringBuilder query = new StringBuilder();
		query.append(BASE_URL);
		query.append("&organization=");
		query.append(organization);
		query.append("&start_date=");
		query.append(start_date.toString(format));
		query.append("&end_date=");
		query.append(end_date.toString(format));
		query.append("&page=");
		query.append(page);
		this.url = query.toString();
	}
}
