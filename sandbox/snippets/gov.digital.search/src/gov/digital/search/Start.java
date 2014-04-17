package gov.digital.search;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Start {
	private static final String[] ORGANIZATIONS = {"CPSC", "FDA", "NHTSA", "USDA"};
	public static void main(String[] args) {
		startRequests();
	}
	private static void startRequests() {
		for(String org : ORGANIZATIONS) {
			new TimedRequest(org);
		}
	}
	private static void jodaTime() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

		DateTime now = new DateTime();
		System.out.println("now: " + now.toString());
		System.out.println("formatted now: " + now.toString(fmt));
		
		DateTime then = now.minusMonths(5);
		System.out.println("then: " + then.toString(fmt));
		
		DateTime start = new DateTime(2004, 1, 25, 0, 0, 0);
		DateTime end = new DateTime(2004, 2, 25, 0, 0, 0);
		
		System.out.println("start: " + start.toString(fmt));
		System.out.println("end: " + end.toString(fmt));
		
		Duration d = new Duration(start, end);
		
		System.out.println("duration: " + d.getStandardDays());
		
		start = end.minusWeeks(2);
		
		d = new Duration(start, end);
		
		System.out.println("duration after end.minusWeeks(2): " + d.getStandardDays());
	}
}
