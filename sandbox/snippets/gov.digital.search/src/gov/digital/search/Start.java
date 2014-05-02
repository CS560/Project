package gov.digital.search;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Start {
	public static void main(String[] args) throws FileNotFoundException, IOException {

		CPSC.getMoreData("cpsc.tsv", "cpsc-expanded.tsv");

		/** code to parse an output file from from TweetsToTSV class
		 * make sure the input file is named such as 
		 * "usda-classified-result-02.txt"
		String org = "usda";
		String iteration = "02";
		String inputFile = org + "-classified-result-" + iteration + ".txt";
		String outputFile = org + "-classified-parsed-" + iteration + ".tsv";
		new NBClassificationParser(inputFile, outputFile);
		*/
		
		/** code to break all the data into respective data files, line-by-line as TSV
		 * @notes this files is generated by DataUtil.java, it crawls the below API
		 * @notes API located at - http://search.digitalgov.gov/developer/recalls.html
		String fileName = "dataFile.txt";
		new TSVOrganizationWriter(fileName);
		*/
	}
}
