package gov.digital.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class NHTSA {
	
	public static String[] headers = {
		"organization",
		"recall_number",
		"recall_date",
		"recall_url",
		"records",
		"component_description",
		"manufacturer",
		"code",
		"potential_units_affected",
		"initiator",
		"report_date",
		"defect_summary",
		"consequence_summary",
		"corrective_summary",
		"notes",
		"recall_subject"
	};

	public NHTSA() {
		
	}
	
	public String organization;
	public String recall_number;
	public String recall_date;
	public String recall_url;
	public NHTSARecord[] records;
	public String component_description;
	public String manufacturer;
	public String code;
	public String potential_units_affected;
	public String initiator;
	public String report_date;
	public String defect_summary;
	public String consequence_summary;
	public String corrective_summary;
	public String notes;
	public String recall_subject;
	
	public String toTSV() {
		StringBuilder builder = new StringBuilder();
		builder.append(organization);
		builder.append("\t" + recall_number);
		builder.append("\t" + recall_date);
		builder.append("\t" + recall_url);

		if(records.length > 0)
			builder.append("\t" + records[0].toCSV());
		for(int i = 1; i < records.length; i++) {
			builder.append("," + records[i].toCSV());
		}
		
		builder.append("\t" + component_description);
		builder.append("\t" + manufacturer);
		builder.append("\t" + code);
		builder.append("\t" + potential_units_affected);
		builder.append("\t" + initiator);
		builder.append("\t" + report_date);
		builder.append("\t" + defect_summary);
		builder.append("\t" + consequence_summary);
		builder.append("\t" + corrective_summary);
		builder.append("\t" + notes);
		builder.append("\t" + recall_subject);
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
	public static HashSet<String> printColumnAsHashSet(int index) 
		throws FileNotFoundException, IOException {
		HashSet<String> set = new HashSet<String>();
		File inputFile = new File("nhtsa.tsv");
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		
		//burn 1 line of headers
		br.readLine();
		
		String line;
		while((line = br.readLine()) != null) {
			
			//split by tabs, id is index 1, url is index 3
			String[] fields = line.split("\\t");
			set.add(fields[index].trim());
		}	
		Iterator<String> iterator = set.iterator();
		System.out.println("iterating set with " + set.size() + " items");
		int count = 0;
		while(iterator.hasNext()) {
			System.out.print(count++ + "\t");
			System.out.println(iterator.next());
		}
		
		
		return set;
	}
}
