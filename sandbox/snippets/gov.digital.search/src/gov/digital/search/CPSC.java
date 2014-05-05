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
import java.util.Set;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CPSC {
	
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
}
