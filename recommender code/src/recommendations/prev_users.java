package recommendations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class prev_users {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			String line=null;
			int i=1;
		FileInputStream fastream = new FileInputStream("finalcategories.txt");
        BufferedReader bro = new BufferedReader(new InputStreamReader(fastream));
        File filenew = new File("prev_users.txt");
        BufferedWriter outl = new BufferedWriter(new FileWriter(filenew));
        HashMap<Integer, String> cache = new HashMap<Integer, String>();
        while ((line=bro.readLine())!=null)
        {
        	cache.put(i, line);
        	i++;         	
        }
        String tem=null;
        for (i=20;i<40;i++)
        {
        	if((i%2)==0){	
        		outl.write(cache.get(1)+","); 
        		
        	}
        	if((i%3)==0){	outl.write(cache.get(2)+",");   	}	
        	if((i%4)==0){	outl.write(cache.get(3)+",");   	}
        	if((i%5)==0){	outl.write(cache.get(4)+",");   	}
        	if((i%6)==0){	outl.write(cache.get(5)+",");   	}
        	if((i%7)==0){	outl.write(cache.get(6)+",");   	}
        	if((i%8)==0){	outl.write(cache.get(7)+",");   	}
        	if((i%9)==0){	outl.write(cache.get(8)+",");   	}
        	if((i%10)==0){	outl.write(cache.get(9)+",");   	}
        	if((i%11)==0){	outl.write(cache.get(10)+",");   }
        	if((i%13)==0){	outl.write(cache.get(15)+",");   }
        	if((i%17)==0){	outl.write(cache.get(13)+",");   }
        	if((i%15)==0){	outl.write(cache.get(17)+",");   }
        	outl.write("\n");
        }
        for (i=30;i<60;i++)
        {
        	if((i%2)==0){	
        		outl.write(cache.get(9)+","); 
        		
        	}
        	if((i%3)==0){	outl.write(cache.get(12)+",");   	}	
        	if((i%4)==0){	outl.write(cache.get(13)+",");   	}
        	if((i%5)==0){	outl.write(cache.get(14)+",");   	}
        	if((i%6)==0){	outl.write(cache.get(15)+",");   	}
        	if((i%7)==0){	outl.write(cache.get(16)+",");   	}
        	if((i%8)==0){	outl.write(cache.get(17)+",");   	}
        	if((i%9)==0){	outl.write(cache.get(18)+",");   	}
        	if((i%10)==0){	outl.write(cache.get(19)+",");   	}
        	if((i%11)==0){	outl.write(cache.get(20)+",");   }
        	if((i%13)==0){	outl.write(cache.get(5)+",");   }
        	if((i%17)==0){	outl.write(cache.get(3)+",");   }
        	if((i%15)==0){	outl.write(cache.get(7)+",");   }
        	outl.write("\n");
        }
        for (i=110;i<140;i++)
        {
        	if((i%2)==0){	
        		outl.write(cache.get(4)+","); 
        		
        	}
        	if((i%13)==0){	outl.write(cache.get(24)+",");   	}	
        	if((i%24)==0){	outl.write(cache.get(23)+",");   	}
        	if((i%35)==0){	outl.write(cache.get(22)+",");   	}
        	if((i%46)==0){	outl.write(cache.get(21)+",");   	}
        	if((i%17)==0){	outl.write(cache.get(20)+",");   	}
        	if((i%28)==0){	outl.write(cache.get(19)+",");   	}
        	if((i%19)==0){	outl.write(cache.get(18)+",");   	}
        	if((i%10)==0){	outl.write(cache.get(17)+",");   	}
        	if((i%21)==0){	outl.write(cache.get(16)+",");   }
        	if((i%23)==0){	outl.write(cache.get(15)+",");   }
        	if((i%7)==0){	outl.write(cache.get(13)+",");   }
        	if((i%5)==0){	outl.write(cache.get(11)+",");   }
        	outl.write("\n");
        }   
    
        outl.close();
        System.out.println("Completed.");
		}
		catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
          }
	}

}
