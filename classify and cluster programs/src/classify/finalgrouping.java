package classify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class finalgrouping {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			FileInputStream fstream = new FileInputStream("finalcategorised.txt");
	        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	        String line=null;
	        String fline=null;
	        String sline=null;
	        String temp=null;
	        String value=null;
	        int i=1;
	        HashMap<Integer, String> cache = new HashMap<Integer, String>();
	        File filenew = new File("finalgrouped.txt");
	        BufferedWriter out = new BufferedWriter(new FileWriter(filenew));
	        while ((fline=br.readLine())!=null)
	        {
	        		sline=fline;
	        	//		System.out.println("i :" + i);
	        			String[] tokens = sline.split("::", 2);
	        			temp=tokens[0];
	        	//		line=temp.toLowerCase();
	        	if (cache.containsValue(temp)==false)
	        	{	        		
	        		cache.put(i, temp);
	        	//	System.out.println("Detected category : " + temp);
	        		i++;
	        	}
	        }
	     //   FileInputStream lstream = new FileInputStream("finalcategorised.txt");
	        Iterator<Integer> iterator = cache.keySet().iterator();
	        int count=0;
	        while(iterator.hasNext())
	        {
		     	   Integer key = iterator.next();
		     	   value = cache.get(key);
		     	FileInputStream lstream = new FileInputStream("finalcategorised.txt");
		        BufferedReader bro = new BufferedReader(new InputStreamReader(lstream));
		        while ((fline=bro.readLine())!=null)
		        {
		        		sline=fline;
		        		//	System.out.println("i :" + i);
		        			String[] tokens = sline.split("::", 2);
		        			temp=tokens[0];
		        	//		System.out.println("value :" + value + "\t" + "temp : " + temp);
		        			if (temp.contains(value))
		        			{
		        	//			System.out.println("i :" + i);
		        				out.write(fline + "\n");
		        				
		        			}
		        }
		        
		        count++;
	        }
	        System.out.println("Total number of categories in this file : " + count);
		}
		catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			}
	}

}
