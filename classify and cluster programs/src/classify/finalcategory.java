package classify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class finalcategory 
{
	public static void main (String args[]) {
	try 
	{
		FileInputStream fastream = new FileInputStream("nhtsa.tsv");
        BufferedReader bro = new BufferedReader(new InputStreamReader(fastream));
        File filenew = new File("finalcategorised.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(filenew));
        String fline=null;
        String line=null;
        String temp=null;
        fline=bro.readLine();
        int i=0;       
        String sline=null;
        while ((fline=bro.readLine())!=null)
        		{
        			sline=fline;
        	//		System.out.println("i :" + i);
        			String[] tokens = sline.split("\t", 13);
        			temp=tokens[6];
        			line=temp.toLowerCase();        	
        			if (line.contains("america")||line.contains("usa")||line.contains("u.s.a"))
        			{
        				out.write("USA"+ "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("auto")||line.contains("truck")||line.contains("wheel")||line.contains("vehicle")||line.contains("car")||line.contains("bus"))
        			{
        				out.write( "Auto" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("build")||line.contains("built")||line.contains("construct"))
        			{
        				out.write("Buildings" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("california"))
        			{
        				out.write("California" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("coach")||line.contains("wagon"))
        			{
        				out.write( "Coaches" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("child")||line.contains("baby"))
        			{
        				out.write("Children" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("company")||line.contains("comp.")||line.contains("comp"))
        			{
        				out.write( "Company" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("corp"))
        			{
        				out.write("Corporation" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("christ"))
        			{
        				out.write("Christmas" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("industry")||line.contains("industr")||line.contains("factor"))
        			{
        				out.write("Industries" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("Electric"))
        			{
        				out.write("Electrical" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("electronic"))
        			{
        				out.write("Electronics" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("engineer"))
        			{
        				out.write("Engineering" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("equip"))
        			{
        				out.write("Equipment" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("technolo"))
        			{
        				out.write("Technology" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("interior"))
        			{
        				out.write("Interiors" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("international"))
        			{
        				out.write("International" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("llc"))
        			{
        				out.write("Limited liability company" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("ltd"))
        			{
        				out.write("Private Ltd company" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("london"))
        			{
        				out.write("London" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("manufactur")||line.contains("mfg"))
        			{
        				out.write("Manufacturing company" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("miriam"))
        			{
        				out.write("Miriam" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("morel"))
        			{
        				out.write("Morel"+ "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("mr."))
        			{
        				out.write("Men" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("mrs."))
        			{
        				out.write("women" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("national"))
        			{
        				out.write("National" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("trailer"))
        			{
        				out.write("Trailer" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("commercial"))
        			{
        				out.write("Commercial" + "\t::\t"  + fline + "\n");
        			}
        			else if (line.contains("inc."))
        			{
        				out.write("Buisiness" + "\t::\t"  + fline + "\n");
        			}
        			else 
        			{
        				out.write("Others" + "\t::\t"  + fline + "\n");
        			}
        		}
        out.close();
        System.out.println("completed.");
	}
	catch (Exception e){//Catch exception if any
		System.err.println("Error: " + e.getMessage());
		}
    
	}
}