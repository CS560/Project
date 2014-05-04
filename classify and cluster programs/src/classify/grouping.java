package classify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;


public class grouping {
	public static void main(String args[])
	{
		try{
		FileInputStream stream = new FileInputStream("single_output.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        
        String line=null;
        String temp=null;
        String group_line=null;
        String group_temp=null;
     //   HashMap<Integer, String> cache = new HashMap<Integer, String>();
       // HashMap<Integer, String> single = new HashMap<Integer, String>();
    //    HashMap<Integer, String>  map = new HashMap(source);
        line=br.readLine();
        int i=0;
        
        
        File filenew = new File("group_results.txt");
        BufferedWriter group_output = new BufferedWriter(new FileWriter(filenew));
        System.out.println("i : "+i);
        while ((line=br.readLine())!= null)
        {
        	String[] tokens = line.split("\t", 2);
        	temp=tokens[1];
        	System.out.println(" recall category : " + temp +"\n");
        	group_output.write(" recall category : " + temp +"\n");
        	FileInputStream gstream = new FileInputStream("nhtsa.tsv");
            BufferedReader bro = new BufferedReader(new InputStreamReader(gstream));
        	while ((group_line=bro.readLine())!=null)   
        	{
        		String[] group_tokens = group_line.split("\t", 13);
            	group_temp=group_tokens[6];
            	if(group_temp==temp)
            	{
            	     group_output.write(group_line+"\n");
            	}
        	}
       
        }
        group_output.close();
        System.out.println("completed");
        }
		catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			}
        
	}

}
