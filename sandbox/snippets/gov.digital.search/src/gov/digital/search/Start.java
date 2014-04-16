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

	private static final String[] orgs = {"CPSC", "FDA", "NHTSA", "USDA"};
	
	private static String url = "http://api.usa.gov/recalls/search.json?";
	private static String query;
	private static String organization;
	private static String page;
	private static String per_page;
	private static String sort;
	
	private static Date start_date;
	private static Date end_date;
	
	public static void main(String[] args) {

		start_date = new Date(1966, 0, 1);
		
		System.out.println(start_date);
	}
	
	private void setUrl() {
		
	}
	
	private void fastForward() {
		
	}
	
	private void dos() {
		int count = 1;
		for(String org : orgs) {
			organization = org;
			
			//foreach year
			
			//foreach month
			
			//do this every 2 seconds to avoid rapidly targeting someone else's web server
			Timer timer = new Timer();
			TimedRequest request = new TimedRequest();
			request.setUrl(url);
			timer.schedule(request, count*2300);
			
			
			//this query reveals first recalls are in 1966: http://api.usa.gov/recalls/search.json?start_date=1966-01-01&end_date=1966-12-31
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
			Calendar cal = new GregorianCalendar();
			System.out.println(cal.JANUARY);
			
			String f = df.format(cal.getTime());
			System.out.println(f);
			
			count++;
		}
	}

}
