package gov.digital.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

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
	public static void getMoreData(String inputFileName, String outputFileName) {
		
		//spread the requests out
		final int SLEEP_TIMER = 601;
		
		//list to catch errors from HTTP status exceptions
		ArrayList<String> list = new ArrayList<String>();
		
		//list to catch errors from format parser .archived and .details
		ArrayList<String> problems = new ArrayList<String>();
		
		//counters
		int httpErrors = 0;
		int numRecords = 0;
		
		//the fields i am looking for in each visit to a page
		String[] data = {
				"units",
				"description",
				"incidents_injuries",
				"remedy",
				"sold_exclusively_at",
				"importer",
				"manufactured_in",
			};

		//file stuff
		File inputFile;
		File outputFile;
		BufferedReader br;
		BufferedWriter bw;
			
		//set files
		inputFile = new File(inputFileName);
		outputFile = new File(outputFileName);
		
		//set readers and writers, do everything
		try {
			br = new BufferedReader(new FileReader(inputFile));
			bw = new BufferedWriter(new FileWriter(outputFile));

			//burn 1 line of headers in the read file
			br.readLine();
			
			//loop: read then write, over and over
			String line;
			while((line = br.readLine()) != null) {
				numRecords++;
				
				//split by tabs, id is index 1, url is index 3
				String[] fields = line.split("\\t");
				String id = fields[1].trim(); //id
				String url = fields[3].trim(); //url
				
				StringBuilder builder = new StringBuilder();
				builder.append(id);
				
				//for every url, inspect its style and decide an action
				//visit the web page and fetch the following categories of data
				//if url does not contain .aspx? then it is restful and has certain useful header meta properties
				if(url.indexOf(".aspx?") == -1) {
					try {
						
						Document doc = Jsoup.connect(url).get();
						Elements details = doc.select("div.details > *");
						Elements archived = doc.select("div.archived > *");
						
						//flag for 2 formats - will go false if either not found
						boolean optimistic = true;
						
						//collection for a variety of data points, used to later print in order
						Map<String,String> pairs = new HashMap<String,String>();
						
						// .details exist 
						try {
							for(Element ele : details) {
								if(ele.tagName().equalsIgnoreCase("h5")) {
									Element sibling = ele.firstElementSibling();
									System.out.println(sibling.text());
									//units
									if(ele.text() == "Units") {
										
									}
										
										
										//description
										
										//incidents_injuries
										
										//remedy
										
										//sold_exclusively_at
										
										//importer
										
										//manufactured_in
									
									//pairs.put(", value)
								}
							}
						} catch (NullPointerException e) {
							optimistic = false;
						}
						
						// .archived exists
						try {
							for(Element ele : archived) {
								System.out.println(ele.text());
								
								
								//units
								
								//description
								
								//incidents_injuries
								
								//remedy
								
								//sold_exclusively_at
								
								//importer
								
								//manufactured_in
							}
						} catch (NullPointerException e) {
							optimistic = false;
						}
						
						//termination condition
						if(!optimistic) {
							problems.add("new format found - check url: " + url);
						}


						builder.append("\n");
						System.out.println(numRecords + ": " + builder.toString());
						bw.write(builder.toString());

					} catch (HttpStatusException e) {
						httpErrors++;
						list.add(e.getStatusCode() + " - " + e.getMessage());
					}
						
				} else { //then asp query params lead to a fairly useless data file with potentially lots of results
					System.out.println(numRecords + "ASPX params");
				}
				
				try {
					Thread.sleep(SLEEP_TIMER);
				} catch (InterruptedException e) {
					System.out.println("thread exception (what throws this kind of exception, anyway?): ");
					e.printStackTrace();
					System.exit(1);
				}
			}
			br.close();
			bw.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found: ");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("problem with file writer: ");
			e.printStackTrace();
			System.exit(1);
		} finally {
			System.out.println("\n\n-----------------------\nHTTP ERROS LIST");
			System.out.println("There were " + httpErrors + " errors");
			for(String error : list) {
				System.out.println(error);
			}
			System.out.println("\n\n-----------------------\nFORMAT PROBLEMS");
			for(String error : problems) {
				System.out.println(error);
			}
		}
	}
}
