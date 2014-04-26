/**
 * basically, here's what is happening:
 * 
 * recall id = readline
 * classification = readline
 * write to output file (id, classification)		
 */

package gov.digital.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class NBClassificationParser {
	
	File inputFile;
	File outputFile;
	BufferedReader br;
	BufferedWriter bw;
	int numIOExceptions;;

	/**
	 * constructor
	 * parse a *-classified-result.txt file
	 * reformat as TSV with columns: recall_id, classification_label
	 * @param fileName
	 */
	public NBClassificationParser(String inputFileName, String outputFileName) 
			throws FileNotFoundException, IOException {
		
		//set files
		inputFile = new File(inputFileName);
		outputFile = new File(outputFileName);
		
		//set IOException counter
		numIOExceptions = 0;
		
		//set readers and writers
		br = new BufferedReader(new FileReader(inputFile));
		bw = new BufferedWriter(new FileWriter(outputFile));
		
		//burn 2 lines for intro stats
		br.readLine();
		br.readLine();
		
		//loop: read then write, over and over
		String line;
		while((line = br.readLine()) != null) {
			
			//split by tabs, take first element like "Tweet: cc578a4dce" then further split "Tweet: " to isolate ID
			String id = line.split("\\t")[0].split("Tweet: ")[1].trim();
			
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
		}
		
		br.close();
		bw.close();
	}
	/*
	private String readLine() {
		//read line
		try {
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line;
			while ((line = br.readLine()) != null) {
				linesRead++;
				System.out.println("Processing line " + linesRead + " from " + sourceFile.getName() + ": " + line);
				//root
				JSONObject json = new JSONObject(line);
				//array of objects
				JSONArray results = json.getJSONArray("results");
				//foreach object in json array
				for(int i = 0; i < results.length(); i++) {
					JSONObject record = results.getJSONObject(i);
					writeToFile(record);
				}
			}
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}
	private void writeLine(String line) {
		//write line
		try {
			FileWriter fw = new FileWriter(fileName.getName(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(success.toString() + "\n");
			bw.close();
		} catch (IOException e) {
			
		}
	}
	*/
	
}
