package gov.digital.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class TSVFormatWriter {

	String[] fields = {
		"summary", 
		"recall_number", 
		"recall_date",
		"organization",
		"description",
		"recall_url",
		"hazards",
		"upcs",
		"manufacturers",
		"countries",
		"descriptions",
		"product_types",
		"defect_summary",
		"potential_units_affected",
		"component_description",
		"code",
		"corrective_summary",
		"recall_date",
		"report_date",
		"initiator",
		"manufacturer",
		"notes",
		"records",
		"recall_subject",
		"consequence_summary"
	};
	
	File sourceFile;
	
	public static void main(String[] args) {
		new TSVFormatWriter("dataFile.txt");
	}
	public TSVFormatWriter(String fileName) {
		sourceFile = new File(fileName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line;
			while ((line = br.readLine()) != null) {
			   JSONObject json = new JSONObject(line);
			   JSONArray array = json.getJSONArray("results");
			   for(int i = 0; i < array.length(); i++) {
				   System.out.println(array.getString(0));
			   }
			}
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}	
}
