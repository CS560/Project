package gov.digital.search;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Start {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String inputFile = "fda-classified-result-01.txt";
		String outputFile = "fda-classified-parsed-01.tsv";
		new NBClassificationParser(inputFile, outputFile);
		
		
		/** code to break all the data into respective data files, line-by-line as TSV
		String fileName = "dataFile.txt";
		new TSVOrganizationWriter(fileName);
		*/
	}
}
