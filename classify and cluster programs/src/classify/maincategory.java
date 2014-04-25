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

public class maincategory 
{
	public static void main (String args[]) {
	try 
	{
		FileInputStream fastream = new FileInputStream("single_output.txt");
        BufferedReader bro = new BufferedReader(new InputStreamReader(fastream));
        File filenew = new File("categorised.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(filenew));
        String fline=null;
        String line=null;
        while ((fline=bro.readLine())!=null)
        		{
        			line=fline.toLowerCase();        	
        			if (line.contains("america")||line.contains("usa")||line.contains("u.s.a"))
        			{
        				out.write(line + "\t" + "USA"+ "\n");
        			}
        			else if (line.contains("auto")||line.contains("truck")||line.contains("wheel")||line.contains("vehicle")||line.contains("car")||line.contains("bus"))
        			{
        				out.write(line + "\t" +  "Auto" + "\n");
        			}
        			else if (line.contains("build")||line.contains("built")||line.contains("construct"))
        			{
        				out.write(line +  "\t" + "Buildings" + "\n");
        			}
        			else if (line.contains("california"))
        			{
        				out.write(line +  "\t" + "California" + "\n");
        			}
        			else if (line.contains("coach")||line.contains("wagon"))
        			{
        				out.write(line + "\t" +  "Coaches" + "\n");
        			}
        			else if (line.contains("child")||line.contains("baby"))
        			{
        				out.write(line +  "\t" + "Children" + "\n");
        			}
        			else if (line.contains("company")||line.contains("comp.")||line.contains("comp"))
        			{
        				out.write(line + "\t" +  "Company" + "\n");
        			}
        			else if (line.contains("corp"))
        			{
        				out.write(line +  "\t" + "Corporation" + "\n");
        			}
        			else if (line.contains("christ"))
        			{
        				out.write(line +  "\t" + "Christmas" + "\n");
        			}
        			else if (line.contains("industry")||line.contains("industr")||line.contains("factor"))
        			{
        				out.write(line +  "\t" + "Industries" + "\n");
        			}
        			else if (line.contains("Electric"))
        			{
        				out.write(line +  "\t" + "Electrical" + "\n");
        			}
        			else if (line.contains("electronic"))
        			{
        				out.write(line +  "\t" + "Electronics" + "\n");
        			}
        			else if (line.contains("engineer"))
        			{
        				out.write(line +  "\t" + "Engineering" + "\n");
        			}
        			else if (line.contains("equip"))
        			{
        				out.write(line +  "\t" + "Equipment" + "\n");
        			}
        			else if (line.contains("technolo"))
        			{
        				out.write(line +  "\t" + "Technology" + "\n");
        			}
        			else if (line.contains("interior"))
        			{
        				out.write(line +  "\t" + "Interiors" + "\n");
        			}
        			else if (line.contains("international"))
        			{
        				out.write(line +  "\t" + "International" + "\n");
        			}
        			else if (line.contains("llc"))
        			{
        				out.write(line +  "\t" + "Limited liability company" + "\n");
        			}
        			else if (line.contains("ltd"))
        			{
        				out.write(line +  "\t" + "Private Ltd company" + "\n");
        			}
        			else if (line.contains("london"))
        			{
        				out.write(line +  "\t" + "London" + "\n");
        			}
        			else if (line.contains("manufactur")||line.contains("mfg"))
        			{
        				out.write(line +  "\t" + "Manufacturing company" + "\n");
        			}
        			else if (line.contains("miriam"))
        			{
        				out.write(line +  "\t" + "Miriam" + "\n");
        			}
        			else if (line.contains("morel"))
        			{
        				out.write(line +  "\t" + "Morel"+ "\n");
        			}
        			else if (line.contains("mr."))
        			{
        				out.write(line +  "\t" + "Men" + "\n");
        			}
        			else if (line.contains("mrs."))
        			{
        				out.write(line +  "\t" + "women" + "\n");
        			}
        			else if (line.contains("national"))
        			{
        				out.write(line +  "\t" + "National" + "\n");
        			}
        			else if (line.contains("trailer"))
        			{
        				out.write(line +  "\t" + "Trailer" + "\n");
        			}
        			else if (line.contains("commercial"))
        			{
        				out.write(line +  "\t" + "Commercial" + "\n");
        			}
        			else if (line.contains("inc."))
        			{
        				out.write(line +  "\t" + "Buisiness" + "\n");
        			}
        			else 
        			{
        				out.write(line +  "\t" + "Others" + "\n");
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