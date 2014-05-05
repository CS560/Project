package gov.digital.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtendedCPSC {
	
	private static final String organization = "CPSC";
	
	private static BidiMap<String,String> bmap;
	private static ArrayList<String> list = new ArrayList<String>(); //list to catch errors from HTTP status exceptions		
	private static ArrayList<String> problems = new ArrayList<String>(); //list to catch errors from format parser .archived and .details

	
	/**
	 * the main point of this property and method is to map headers to values, thus keeping the columns in line
	 * it is up to the logic of the DataUtil class to enforce this data structure
	 */	
	private static ArrayList<String> extendedHeaderCategoryPairs;
	private static BidiMap<String,String> setExtendedHeadersBmap() {
		bmap = new DualHashBidiMap();
		bmap.put("name_of_product","Name of product");
		bmap.put("manufacturer","Manufacturer");
		bmap.put("hazard","Hazard");
		bmap.put("sold_to","Sold to");
		bmap.put("sold_exclusively_at","Sold exclusively at");
		bmap.put("units","Units");
		bmap.put("description","Description");
		bmap.put("incidents_injuries","Incidents/Injuries");
		bmap.put("remedy","Remedy");
		bmap.put("importer","Importer");
		bmap.put("manufactured_in","Manufactured in");
		bmap.put("consumer_contact","Consumer contact");
		return bmap;
	}
	
	/**
	 * The http://search.digitalgov.gov/developer/recalls.html API lacks
	 * summary description information. This method will use the URLS in the cpsc.tsv
	 * file to gather more data from every page. That file is generated after 
	 * TSVOrganizationWriter.java parses DataUtil.java output
	 * @param fileName
	 * @param outputFileName
	 */
	public static void extendData(String inputFileName, String outputFileName) {
		//spread the requests out
		final int SLEEP_TIMER = 601; 
		//counters
		int httpErrors = 0;
		int numRecords = 0;
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
			//read from cpsc.tsv
			br = new BufferedReader(new FileReader(inputFile));
			//write new file
			bw = new BufferedWriter(new FileWriter(outputFile));
			//burn 1 line of headers in the read file
			br.readLine();
			//print the file headers to output file
			bw.write(getCPSCExtendedTSVHeaders() + "\n");
			
			//loop: read then write, over and over
			String line;
			while((line = br.readLine()) != null) {
				numRecords++;
				//split line from cpsc.tsv by tabs, id is index 1, url is index 3
				String[] fields = line.split("\\t");
				String id = fields[1].trim(); //id
				String url = fields[3].trim(); //url

				//print a point of reference to the console
				System.out.println(url);

				//start the builder with org and recall_number
				StringBuilder builder = new StringBuilder();
				builder.append("CPSC");
				builder.append("\t" + id);
				
				//for every url, inspect its pattern and decide an action
				try {
					//visit the web page and look for some matching templates
					Document doc = Jsoup.connect(url).get();						
					Elements archived = doc.select("div.archived strong");
					String tsv = "";
					if(archived.size() > 0) {
						tsv = getExtendedTSV(archived, organization, id);
					} else {
						Elements details = doc.select("div.details strong");
						tsv = getExtendedTSV(details, organization, id);
					}
					builder.append(tsv);

					builder.append("\n");
					bw.write(builder.toString());
					System.out.println(getCPSCExtendedTSVHeaders());
					System.out.println(numRecords + ": " + builder.toString());

				} catch (HttpStatusException e) {
					httpErrors++;
					list.add(e.getStatusCode() + " - " + e.getMessage());
				} catch (SocketTimeoutException e) {
					problems.add(e.getMessage());
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
			System.out.println("\n\n-----------------------\nHTTP ERRORS LIST");
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
	/**
	 * recursively iterate over an HTML section visiting each element and all children
	 * @param doc
	 * @return
	 */
	private static String getExtendedTSV(Elements elms, String org, String id) {
		StringBuilder builder = new StringBuilder();
		
		//build a new map from whatever's available on the page
		Map<String,String> availableMap = new HashMap<String,String>();
		for(Element ele : elms) {
			try {
				String key = ele.text().split(":")[0];
				String value = ele.nextSibling().toString().trim();
				availableMap.put(key, value);
			} catch (NullPointerException e) {
				//dont' worry about it - it's not the key/value we're looking for anyway. continue the loop
			}
		}
		//then, map that map to the bidimap to build a uniformly columnar TSV file
		Set<String> values = bmap.values();//value is like "Sold to"
		Iterator vit = values.iterator();
		while(vit.hasNext()) {
			builder.append("\t" + availableMap.get(vit.next()));
		}		
		return builder.toString();
	}
	/**
	 * print the headers for the entire file - one time only
	 * @return
	 */
	private static String getCPSCExtendedTSVHeaders() {
		setExtendedHeadersBmap();
		StringBuilder headers = new StringBuilder();
		headers.append(organization);
		headers.append("\trecall_number");
		Set<String> values = bmap.values();//value is like "Sold to"
		Iterator vit = values.iterator();
		while(vit.hasNext()) {
			headers.append("\t" + bmap.getKey(vit.next()));
		}
		return headers.toString();
	}
}
