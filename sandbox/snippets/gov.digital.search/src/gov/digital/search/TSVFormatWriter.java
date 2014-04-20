package gov.digital.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class TSVFormatWriter {


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
