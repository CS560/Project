package gov.digital.search;
public class Start {
	private static final String[] ORGANIZATIONS = {"CPSC", "FDA", "NHTSA", "USDA"};
	public static void main(String[] args) {
		
		new TimedRequest(ORGANIZATIONS[0]);
	}
}
