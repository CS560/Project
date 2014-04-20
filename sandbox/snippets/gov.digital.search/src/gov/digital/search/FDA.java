package gov.digital.search;

public class FDA {
	
	public FDA() {
		
	}
	
	public String organization;
	public String recall_number;
	public String recall_date;
	public String recall_url;
	public String description;
	public String summary;
	
	public String toTSV() {
		StringBuilder builder = new StringBuilder();
		builder.append(organization);
		builder.append("\t" + recall_number);
		builder.append("\t" + recall_date);
		builder.append("\t" + recall_url);
		builder.append("\t" + description);
		builder.append("\t" + summary);
		return builder.toString();
	}
}
