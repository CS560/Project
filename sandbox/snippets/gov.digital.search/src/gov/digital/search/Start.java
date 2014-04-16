/**
 * Use Timers, JSON, and Date objects to fetch 26000 records from usa.gov
 */

package gov.digital.search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

public class Start {

	//there are four possible organizations to query
	private static final String[] orgs = {"CPSC", "FDA", "NHTSA", "USDA"};
	
	//this query reveals recalls begin in 1966: http://api.usa.gov/recalls/search.json?start_date=0000-01-01&end_date=1966-01-19
	private static int startYear = 1966;
	
	private static String url = "http://api.usa.gov/recalls/search.json?";
	private static String query;
	private static String organization;
	private static String page; //between 1 and 20
	private static String per_page; //max 50
	private static String sort;
	
	private static Date start_date;
	private static Date end_date;
	
	public static void main(String[] args) {

		start_date = new Date(1966, 0, 1);
		
		System.out.println(start_date);
		
		/* maxResults = 1000
		 * start = startDate
		 * end = start + month
		 * 
		 * while(start < now + one day) do
		 * 
		 * wrap a web request:
		 *   start a timer
		 *   on timer complete:
		 *     get JSON results
		 *     if(numResults > maxResults)
		 *       split the date range in half
		 *         end = timeBetween(start, end)
		 *         wrap a web request:
		 *     else
		 *       process results
		 *         writeOut(outputFormatWriter)
		 *         start = end + 1 day
		 *         end = end + 1 month
		 *         wrap a web request
		 *       
		 */
	}
	
	private void dos() {
		int count = 1;
		for(String org : orgs) {
			organization = org;
			
			Timer timer = new Timer();
			TimedRequest request = new TimedRequest();
			request.setUrl(url);
			timer.schedule(request, count*2300);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
			Calendar cal = new GregorianCalendar();
			System.out.println(cal.JANUARY);
			
			String f = df.format(cal.getTime());
			System.out.println(f);
			
			count++;
		}
	}

}
