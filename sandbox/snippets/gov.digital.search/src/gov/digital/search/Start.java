package gov.digital.search;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class Start {
	
	static final String[] ORGANIZATIONS = {"CPSC", "FDA", "NHTSA", "USDA"};
	static final DateTime EARLIEST_RECALL_DATE = new DateTime(1966, 1, 19, 0, 0, 0); //this query reveals recalls begin in 1966: http://api.usa.gov/recalls/search.json?start_date=0000-01-01&end_date=1966-01-19
	static final String BASE_URL = "http://api.usa.gov/recalls/search.json?sort=date&per_page=50"; //the data source including sort and results per page query params
	static final int MAX_RESULTS = 1000; //1000 = 50 data items per page * 20 max pages
	static final int TIMER_DELAY = 1000; //time to wait between sending web requests (don't overwhelm the server)
	static final int QUERY_TIME_SPAN_MONTHS = 2; //query time span
	
	static boolean running = false;	
	static String org;
	static String url;
	static int currentPage;
	static int totalPages;
	static int totalResults;
	static DateTime end_date;
	static DateTime start_date;	

	/**
	 * start firing web requests covering 2 month intervals
	 * @param args
	 */	
	public static void main(String[] args) {
		init();
		setTimer();
	}
	
	static void init() {
		running = true;
		currentPage = 1;
		end_date = new DateTime();
		start_date = end_date.minusMonths(2);
		org = ORGANIZATIONS[0];
		setUrl();
	}
	
	/**
	 * govern pace with a timer
	 */
	static void setTimer() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				wrapRequest();
				if(running) {
					setTimer();
				}
			}
		};
		timer.schedule(task, TIMER_DELAY);
	}
	
	/**
	 * do some heavy lifting with a request wrapper
	 * manage the state of this class
	 */
	static void wrapRequest() {
		
		System.out.println("requesting data from: " + url);	

		String response = getResponse(url);
		JSONObject json = new JSONObject(response);
		JSONObject success = json.getJSONObject("success");
		
		//more than 50 results means we need to scan forward pages
		if(currentPage == 1) {
			totalResults = success.getInt("total");
		} else {
			totalResults = totalResults - 50; //because 50 results per request
		}

		//distribute query across pages
		if(totalResults > MAX_RESULTS) {
			//split the date range in half by shifting the start
			long duration = new Duration(start_date, end_date).getStandardDays();
			int days = (int)duration/2;
			start_date = end_date.minusDays(days);
		} else {
			if(totalResults > 50) {
				currentPage++;
				int totalPages = 1 + totalResults/50 - currentPage;
				setUrl();
				System.out.println(totalResults + " results to process\n" + (1 + totalPages) + " remaining pages to process");
			} else {
				//reset range to months and continue
				end_date = end_date.minusMonths(QUERY_TIME_SPAN_MONTHS);
				start_date = end_date.minusMonths(QUERY_TIME_SPAN_MONTHS);
				currentPage = 1;
				setUrl();
				System.out.println(totalResults + " results to process");
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
	 * get a remote response in json format
	 * @param url
	 * @return
	 */
	static String getResponse(String url) {
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
	 * sets the url for every web request
	 * @param organization
	 * @param start_date
	 * @param end_date
	 * @param page
	 */
	static void setUrl() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		StringBuilder query = new StringBuilder();
		query.append(BASE_URL);
		query.append("&organization=");
		query.append(org);
		query.append("&start_date=");
		query.append(start_date.toString(format));
		query.append("&end_date=");
		query.append(end_date.toString(format));
		query.append("&page=");
		query.append(currentPage);
		url = query.toString();
	}
}
