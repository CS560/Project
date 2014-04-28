package gov.digital.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CPSC {

	private static String[] headers = {
		"organization",
		"recall_number",
		"recall_date",
		"recall_url",
		"manufacturers",
		"product_types",
		"descriptions",
		"upcs",
		"hazards",
		"countries"
	};
	
	public CPSC() {
		
	}
	
	public String organization;
	public String recall_number;
	public String recall_date;
	public String recall_url;
	public String[] manufacturers;
	public String[] product_types;
	public String[] descriptions;
	public String[] upcs;
	public String[] hazards;
	public String[] countries;
	
	/**
	 * return this object as a line of tab separated values
	 */
	public String toTSV() {
		StringBuilder builder = new StringBuilder();
		builder.append(organization);
		builder.append("\t" + recall_number);
		builder.append("\t" + recall_date);
		builder.append("\t" + recall_url);
		
		if(manufacturers.length > 0)
			builder.append("\t" + manufacturers[0]);
		for(int i = 1; i < manufacturers.length; i++) {
			builder.append("," + manufacturers[i]);
		}

		if(product_types.length > 0)
			builder.append("\t" + product_types[0]);
		for(int i = 1; i < product_types.length; i++) {
			builder.append("," + product_types[i]);
		}
		
		if(descriptions.length > 0)
			builder.append("\t" + descriptions[0]);
		for(int i = 1; i < descriptions.length; i++) {
			builder.append("," + descriptions[i]);
		}

		if(upcs.length > 0)
			builder.append("\t" + upcs[0]);
		for(int i = 1; i < upcs.length; i++) {
			builder.append("," + upcs[i]);
		}
		
		if(hazards.length > 0)
			builder.append("\t" + hazards[0]);
		for(int i = 1; i < hazards.length; i++) {
			builder.append("," + hazards[i]);
		}
			
		if(countries.length > 0)
			builder.append("\t" + countries[0]);
		for(int i = 1; i < countries.length; i++) {
			builder.append("," + countries[i]);
		}
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
	
	/**
	 * The http://search.digitalgov.gov/developer/recalls.html API lacks
	 * summary description information. This method will use the URLS in the cpsc.tsv
	 * file to gather more data from every page. That file is generated after 
	 * TSVOrganizationWriter.java parses DataUtil.java output
	 * @param fileName
	 */
	public static void getMoreData(String inputFileName, String outputFileName)
		throws FileNotFoundException, IOException {

		File inputFile;
		File outputFile;
		BufferedReader br;
		BufferedWriter bw;
			
		//set files
		inputFile = new File(inputFileName);
		outputFile = new File(outputFileName);
		
		//set readers and writers
		br = new BufferedReader(new FileReader(inputFile));
		bw = new BufferedWriter(new FileWriter(outputFile));
		
		//burn 1 line of headers
		br.readLine();
		
		//loop: read then write, over and over
		String line;
		while((line = br.readLine()) != null) {
			
			//split by tabs, index 3 = recall_url
			String url = line.split("\\t")[3].trim();
			
			//for every url, visit the web page and fetch the following categories of data
			System.out.println(url);

			
			/*
			//every 2 lines is a result, so read a new line
			//next line looks like this:
			//contamination: -377.0525363173781  mislabeled: -306.024402843043 => mislabeled
			String labelLine = br.readLine();
			String[] classifications = labelLine.split("=>"); //to the right of this is the actual classification
			
			//everything else split to classification/weight pairs
			//removes trailing, leading, and double spaces
			String[] labels = classifications[0].trim().replaceAll("  ", " ").split(" "); 
			
			//build the output TSV lines
			StringBuilder builder = new StringBuilder();
			builder.append(classifications[1].trim() + "\t");
			builder.append(id);
			for(int i = 0; i < labels.length; i++) {
				if(!labels[i].equalsIgnoreCase("\t"));
					builder.append("\t" + labels[i]);
			}
			System.out.println(builder.toString());
			bw.write(builder.toString() + "\n");
			*/
		}
		
		br.close();
		bw.close();
	}
}
