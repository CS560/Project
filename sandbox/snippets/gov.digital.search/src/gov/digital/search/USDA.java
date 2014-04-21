package gov.digital.search;

public class USDA {

	private static String[] headers = {
		"organization",
		"recall_number",
		"recall_date",
		"recall_url",
		"description",
		"summary"
	};
	
	public USDA(){
		
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
	
	/**
	 * return the file headers as a TSV string
	 */
	public static String getTSVHeaders() {
		StringBuilder builder = new StringBuilder();
		if(headers.length > 0)
			builder.append(headers[0]);
		for(int i = 1; i < headers.length; i++) {
			builder.append("\t" + headers[i]);
		}
		return builder.toString();
	}
}
