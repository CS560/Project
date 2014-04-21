package gov.digital.search;

import java.io.IOException;

public class Start {
	public static void main(String[] args) throws IOException {
		
		String fileName = "dataFile.txt";
		
		new TSVOrganizationWriter(fileName);
	}
}
