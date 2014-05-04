/**
 * This class will crawl the API outlined at
 * http://search.digitalgov.gov/developer/recalls.html
 * 
 * From now until 1966 when the data begins, this class will collect all the results
 * Output is printed in dataFile.txt
 * 
 * Data can be collected 50 results per page, 20 pages max, so this logic is set to 
 * work inside a 1000-result limit. Results can be parsed to respective organization files
 * using the TSVOrganizationWriter class.
 */

package gov.digital.search;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

public class DataUtil {
	
	static final DateTime EARLIEST_RECALL_DATE = new DateTime(1966, 1, 19, 0, 0, 0); //this query reveals recalls begin in 1966: http://api.usa.gov/recalls/search.json?start_date=0000-01-01&end_date=1966-01-19
	static final String BASE_URL = "http://api.usa.gov/recalls/search.json?sort=date&per_page=50"; //the data source including sort and results per page query params
	static final int MAX_RESULTS = 1000; //1000 = 50 data items per page * 20 max pages
	//last time a delay of 700 completed, no problem
	static final int TIMER_DELAY = 300; //time to wait between sending web requests (don't overwhelm the server)
	static final int QUERY_TIME_SPAN_MONTHS = 2; //query time span
	
	static int numInterruptions = 0;
	static boolean running = false;	
	static String org;
	static String url;
	static int currentPage;
	static int totalPages;
	static int totalResults;
	static DateTime end_date;
	static DateTime start_date;
	static File dataFile;

	/**
	 * start firing web requests covering 2 month intervals
	 * @param args
	 */	
	public static void getData() {	
		
		init();
		do {
			//termination criteria
			Duration termination = new Duration(EARLIEST_RECALL_DATE, end_date);
			if(termination.getMillis() < 0) {
				running = false;
			}
			if(running)
				wrapRequest();
			else
				finishGetData();
		} while (running);
	}
	
	/**
	 * set initial properties of the class
	 */
	static void init() {
		running = true;
		currentPage = 1;
		end_date = new DateTime();
		start_date = end_date.minusMonths(2);
		org = "CPSC+FDA+NHTSA+USDA";
		setUrl();
		dataFile = new File("dataFile.txt");
		try { //clear contents of the data file
			PrintWriter writer = new PrintWriter(dataFile);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
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
				setUrl();
				System.out.println(totalResults + " results to process");
			} else {
				//reset range to months and continue
				end_date = end_date.minusMonths(QUERY_TIME_SPAN_MONTHS);
				start_date = end_date.minusMonths(QUERY_TIME_SPAN_MONTHS);
				currentPage = 1;
				setUrl();
				System.out.println(totalResults + " results to process");
			}		
		}
		
		try {
			FileWriter fw = new FileWriter(dataFile.getName(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(success.toString() + "\n");
			bw.close();
		} catch (IOException e) {
			
		}
		
		try {
			Thread.sleep(TIMER_DELAY);
		} catch (InterruptedException e) {
			numInterruptions++;
			System.out.println("Interrupted: ");
			e.printStackTrace();
		}
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
	
	/**
	 * a closing method to print a summary report
	 */
	static void finishGetData() {
		System.out.println("\n-----------------");
		System.out.println("numInterruptions: " + numInterruptions);
	}
	
	/*********************************************************************************
	*** BELOW HERE IS FOR CPSC DATA - MUCH MORE COMPLEX THAN PREVIOUS DATA METHODS ***
	*********************************************************************************/
	
	
}
