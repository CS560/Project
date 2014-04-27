package gov.digital.search;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Start {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String org = "usda";
		String iteration = "02";
		
		String inputFile = org + "-classified-result-" + iteration + ".txt";
		String outputFile = org + "-classified-parsed-" + iteration + ".tsv";
	
		new NBClassificationParser(inputFile, outputFile);
		
		
		/** code to break all the data into respective data files, line-by-line as TSV
		String fileName = "dataFile.txt";
		new TSVOrganizationWriter(fileName);
		*/
	}
}
