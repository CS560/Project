package gov.digital.search;

public class NHTSARecord {
	
	public NHTSARecord() {
		
	}
	
	
	public String component_description;
	public String make;
	public String manufacturer;
	public String manufacturing_begin_date;
	public String manufacturing_end_date;
	public String model;
	public String recalled_component_id;
	public int year;
	
	public String toCSV() {
		StringBuilder builder = new StringBuilder();
		builder.append(component_description); 
		builder.append("," + make); 
		builder.append("," + manufacturer); 
		builder.append("," + manufacturing_begin_date);
		builder.append("," + manufacturing_end_date); 
		builder.append("," + model); 
		builder.append("," + recalled_component_id);
		return builder.toString();
	}
}
