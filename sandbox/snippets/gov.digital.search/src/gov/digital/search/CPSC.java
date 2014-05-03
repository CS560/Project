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
		/*
		 * there's a missing piece here - data mapping to tsv headers:
		 * name_of_product
		 *	units
		 *	manufacturer
		 *	hazard
		 *	description
		 *	incidents_injuries
		 *	remedy
		 *	sold_to
		 *	sold_exclusively_at
		 *	importer
		 *	manufactured_in
		 *	consumer_contact
		 */
		
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
				System.out.println(url);
				
				StringBuilder builder = new StringBuilder();
				builder.append(id);
				
				//for every url, inspect its style and decide an action
				//visit the web page and fetch the following categories of data
				//if url does not contain .aspx? then it is restful and has certain useful header meta properties
				if(url.indexOf(".aspx?") == -1) {
					try {
						
						Document doc = Jsoup.connect(url).get();
						//Elements details = doc.select("div.details > *");
						//Elements archived = doc.select("div.archived > *");
						Elements details = doc.select("strong");
						Elements archived = doc.select("strong");
						
						//flag for 2 formats - will go false if either not found
						boolean optimistic = true;
						
						//collection for a variety of data points, used to later print in order
						Map<String,String> pairs = new HashMap<String,String>();
						
						// .details exist 
						//it's possible an image can be collected from the 2nd table on every page
						try {
							for(Element ele : details) {
								//units
								if(ele.text().equalsIgnoreCase("Units")) {
									System.out.print(ele.text() + " - ");
									System.out.println(ele.nextElementSibling().text());
								}
								//description
								if(ele.text().equalsIgnoreCase("Description")) {
									System.out.print(ele.text() + " - ");
									System.out.println(ele.nextElementSibling().text());
								}
								//incidents_injuries
								if(ele.text().equalsIgnoreCase("Incidents/Injuries")) {
									System.out.print(ele.text() + " - ");
									System.out.println(ele.nextElementSibling().text());
								}
								//remedy
								if(ele.text().equalsIgnoreCase("Remedy")) {
									System.out.print(ele.text() + " - ");
									System.out.println(ele.nextElementSibling().text());
								}
								//sold_exclusively_at
								if(ele.text().equalsIgnoreCase("Sold exclusively at")) {
									System.out.print(ele.text() + " - ");
									System.out.println(ele.nextElementSibling().text());
								}
								//importer
								if(ele.text().equalsIgnoreCase("Importer")) {
									System.out.print(ele.text() + " - ");
									System.out.println(ele.nextElementSibling().text());
								}
								//manufactured_in
								if(ele.text().equalsIgnoreCase("Manufactured in")) {
									System.out.print(ele.text() + " - ");
									System.out.println(ele.nextElementSibling().text());
								}
							}
						} catch (NullPointerException e) {
							optimistic = false;
						}
						
						// .archived exists
						try {
							//making an assumption here: <strong> tags indicate the data we are collecting
							for(Element ele : archived) {
								System.out.println(ele.text());
								//another assumption: at least one <p> exists with a collection of <strong> inside
								//for every <strong> inside a <p>, farm data								
								try {
									String category = ele.text().split(":")[0];
									String content = ele.nextSibling().toString();
									//name_of_product
									if(category.equalsIgnoreCase("Name of product")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//units
									if(category.equalsIgnoreCase("units")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//manufacturer
									if(category.equalsIgnoreCase("manufacturer")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//hazard
									if(category.equalsIgnoreCase("hazard")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//description
									if(category.equalsIgnoreCase("description")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//sold_to
									if(category.equalsIgnoreCase("sold to")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//incidents_injuries
									if(category.equalsIgnoreCase("incidents/injuries")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//remedy
									if(category.equalsIgnoreCase("remedy")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//sold_exclusively_at
									if(category.equalsIgnoreCase("sold exclusively at")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//importer
									if(category.equalsIgnoreCase("importer")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//manufactured_in
									if(category.equalsIgnoreCase("manufactured_in")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
									//consumer_contact
									if(category.equalsIgnoreCase("consumer contact")) {
										System.out.print(category + " - ");
										System.out.println(content);
									}
								} catch(IndexOutOfBoundsException e) {
									//do nothing - the element has no child tag and is not used
								}			
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
					//actually, i think this is a page with links to more pages, so a recursive or iterative parser is needed
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
