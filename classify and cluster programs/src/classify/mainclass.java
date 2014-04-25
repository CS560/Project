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


public class mainclass {
	public static void main (String args[]){ 
	try
	{
		FileInputStream fstream = new FileInputStream("nhtsa.tsv");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String line=null;
        String temp=null;
        HashMap<Integer, String> cache = new HashMap<Integer, String>();
        HashMap<Integer, String> single = new HashMap<Integer, String>();
    //    HashMap<Integer, String>  map = new HashMap(source);
        int i=0;
        while ((line=br.readLine())!= null)
        {
        	String[] tokens = line.split("\t", 13);
        	temp=tokens[6];
        	cache.put(i, temp);    
        
       // 	System.out.println("key :" + i + " Value : " + cache.get(i) );
        //	if (i==100)
        //		break;
        	i++;
        }
        
        Iterator<Integer> iterator = cache.keySet().iterator();
        String value=null;
        int j=0;
        File file = new File("single_output.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
       
        
       while(iterator.hasNext()){
    	   Integer key = iterator.next();
    	   value = cache.get(key);
        	if (single.containsValue(value)==false)
	        	{
	        	single.put(key, value);
	        //	System.out.println("key :" + key + " Value : " + single.get(key) );
	        	output.write(j +"\t" + value + "\n");
	        	j++;
	        	}
	       	
        	}
       		output.close();
          System.out.println("count : " + j + " recalls");
    //      System.out.println("key: " + key + " value: " + cache.get(key));       
        String lock=null;
          Iterator<Integer> repeat = single.keySet().iterator();
          HashMap<Integer, String>  scrap = new HashMap(cache);
          Iterator<Integer> tempo = scrap.keySet().iterator();
          File filenew = new File("group_output.txt");
          BufferedWriter group = new BufferedWriter(new FileWriter(filenew));
        while(repeat.hasNext())
        {
        	Integer k=repeat.next();
        	lock=single.get(k);
        	FileInputStream fastream = new FileInputStream("nhtsa.tsv");
            BufferedReader bro = new BufferedReader(new InputStreamReader(fastream));
            group.write("recall category : " + lock + "\n");
        	while((line=bro.readLine())!=null)
        	{
        		String[] tokens = line.split("\t", 13);
            	temp=tokens[6];
            	if (temp==lock)
            	{
              	group.write(line+"\n");
            	}
        	}
        }
        group.close();
        System.out.println("completed");
        
	}
	catch (Exception e){//Catch exception if any
	System.err.println("Error: " + e.getMessage());
	}
  }
}
