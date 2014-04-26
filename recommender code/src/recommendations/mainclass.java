package recommendations;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class mainclass {
	public static void main(String[] args){
        try{
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream("prev_users.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
           // String[] strLine = br.readLine();
            int count=0;
            int j=0;
            int old_j=0;
         //   int[] testing = new int[100];
            int[][] input_data = new int[100][100];
            String store = null;
            String unique=null;
            int check =0;
           // String[] temp_data = null;
			String strLine = null;
			while (( strLine = br.readLine()) != null)   {
            //String[] tokens=strLine.split(",");
            //Read File Line By Line
				 
				String delim = ","; //insert here all delimitators
				 StringTokenizer temp_data = new StringTokenizer(strLine,delim);
				 while (temp_data.hasMoreTokens()) {
										 
					store = temp_data.nextToken();
		//			System.out.println("store :" + store);
					
					  if (check==0)
					 {
						  
						 unique = store;
						input_data[check][1] = check+1;
			//			 System.out.println("input data: " + check + ":" + input_data[check][1] );
						 check++;
						// old_j=j;
					 }
					 //boolean b = unique.contains(store);
					 
					 else if (unique.contains(store) != true)
					 	{
					//	input_data[check-1][2] = j-old_j;
						input_data[check][1] = check+1;
	//					 System.out.println("input data: " + check + ":" + input_data[check][1] );
					 	  
						unique += "," + store;
	//					System.out.println("unique:" + unique + "\n");
						check++;
				//		old_j=j;
						}
					
					// input_data[check-1][2]++;
					 
					 
					//   System.out.println("record" + j + ":" + "\t" +  store + "\t");
					j++;
					
				}
            //Close the input stream*/
				//System.out.println("line"+ count + ":" + temp_data[0]);
				   count++;
				//   System.out.println("count : " + count + "\n");
			}
			
			System.out.println("Items detected in training file :" + unique + "\n");
			int[][] process_data=new int[100][100];
			process_data=input_data;
			String store_var = null;
			String unique_var = unique;
			int process_check=0;
			
			String[][] prob_store=new String [100][100];
	//		System.out.println("unique_var:" + unique_var + "\n");
			
			 
			 
	 // Individual priority calculation
			
			
	            String delim = ","; //insert here all delimitators
				 StringTokenizer temp_var = new StringTokenizer(unique_var,delim);
				 while (temp_var.hasMoreTokens()) {	
					 int id=0;
					store_var = temp_var.nextToken();
//					System.out.println("store :" + store_var);
					 FileInputStream newstream = new FileInputStream("prev_users.txt");
			         BufferedReader breader = new BufferedReader(new InputStreamReader(newstream));
			         prob_store[process_check][id]=store_var;
					while (( strLine = breader.readLine()) != null)   {
						
						if (strLine.contains(store_var) == true){
							id++;
							process_data[process_check][2]++;
							prob_store[process_check][id]=strLine;
						//	System.out.println("process data : " + process_data[process_check][2]);
			//				System.out.println("lines containing "+ store_var +"  are :"+ prob_store[process_check][id]);
							
							}

					}
					//id--;
			//		System.out.println("Number of lines containing "+ store_var +"  are :"+ id--);
					process_check++;
				}
				 values points = new values();
			//	 int[][][] values=new int[100][100][100];
				 int num=0;
			//	 System.out.println("number:"+num);
				 File filenew = new File("users_recommendations.txt");
			        BufferedWriter outl = new BufferedWriter(new FileWriter(filenew));
				 while (prob_store[num][0]!=null){
		//			 System.out.println("number:"+num);
					 int line=0;
							 while (prob_store[num][line]!=null)
							 {
										 if (line==0)
										 {
											 points.var_id[num][0]=prob_store[num][0];
										 }
										 else
										 {
											 int id=1;
											// String delim = ","; //insert here all delimitators
											 StringTokenizer temp_calc = new StringTokenizer(unique_var,delim);
														 while (temp_calc.hasMoreTokens()) 
														 {																
															store_var = temp_calc.nextToken();
													//		System.out.println("id : " + id);
																	if (store_var!=prob_store[num][0])
																	{																		
																		points.var_id[num][id]=store_var;
																			if(prob_store[num][line].contains(store_var))
																			{																				
																				points.score[num][id]++;
																			}
																			else
																			{
																				points.score[num][id]--;
																			
																			}
																		
									//								System.out.println("points score of "+"for id : "+ id+","+num +"\t" + points.var_id[num][id]+" for variable "+ prob_store[num][0] +" for line "+ line +" is : "+ points.score[num][id]  );
																	}	
																	id++;
															}
														
											} 	
										 line++;
										 					 
							 }
							 num++;
				 }
				 int var=0;
				// int car=0;
				 while(points.var_id[var][0]!=null){
				//	 System.out.println("Points : " + var + " id : " +points.var_id[var][0]);
					 int car=0;
					 while(points.var_id[var][car]!=null)
					 {
						 System.out.println("Points : " + var + " car : " + car +" : " + points.var_id[var][car]);
						 outl.write(points.var_id[var][car]+"::"+points.score[var][car]+",");
						 car++;
					 }
					 outl.write("\n");
					 var++;
				 }
				 outl.close();
				 recommendations cust=new recommendations();
				 int check_here=0;
				 String recommendLine=null;
				 String single_item=null;
				 int[][] prior_scores = new int [100][100];
				 FileInputStream inp_stream = new FileInputStream("users_choice.txt");
		         BufferedReader recommend_input = new BufferedReader(new InputStreamReader(inp_stream));
		        while (( recommendLine = recommend_input.readLine()) != null)
		         {		 
		        	System.out.println("scanned input : " + recommendLine);
		        	 num=0;
		        	 while (points.var_id[num][0]!=null)
		        	 {
		        	//					 System.out.println("number:"+ points.var_id[num][0]);
		        						 int line=0;
		        						 if (recommendLine.contains(points.var_id[num][0])==true)
		        						 {	int ch=1;
		        							 while (points.var_id[num][ch]!=null)
		        							 {
		        							 cust.user_scores[check_here][ch]+=points.score[num][ch];
		        							 
		        //							 System.out.println("users scores : " +cust.user_scores[check_here][ch]);
		        							 ch++;
		        							 }
		        							
		        						 }
		        				
		        						 num++;
		        	 }
		        	 num=1;
		        	 int high=cust.user_scores[check_here][num];
		        	 int low=cust.user_scores[check_here][num];
		        	 while (points.var_id[1][num]!=null)
		        	 {
		      //  		 System.out.println("high : " + high);
		        		 if (cust.user_scores[check_here][num] > high)
		        		 {
		        			 high=cust.user_scores[check_here][num];
		        		 }
		        		 if (cust.user_scores[check_here][num] < low)
		        		 {
		        			 low=cust.user_scores[check_here][num];
		        		 }
		        		 num++;
		        	 }
		        	low--;
		        	int no=0;
		        	 while (high!=low)
		        	 {
		        		 
		        		 num=1;
		        		 while (points.var_id[1][num]!=null)
		        		 {
		        //			 System.out.println("customer score : "+cust.user_scores[check_here][num]+" High : " + high);
		      //  		 System.out.println("high : " + high);
		        		 if (cust.user_scores[check_here][num] == high)
		        		 {
		        			 cust.user_recommendations[check_here][no]=points.var_id[1][num];
		        			 no++;
		        		 }
		        		 num++;
		        		 }
		        		 high--;
		        	 }
		        	
		       /* 	 int no=0;
		        	 num = 1;
		        	 while (no<=4)
		        	 {
		        		 System.out.println("high : " + high);
		        		 while (points.var_id[1][num]!=null)
		        		 {
			        	 if (cust.user_scores[check_here][num]==high)	
			        	 {
			        		 cust.user_recommendations[check_here][num]=points.var_id[0][num];
			        		 no++;
			        	 }
			        	 if(no >= 4)
			        		 break;
			        	 num++;
		        		 }
		        		 if(no>=4)
		        			 break;
		        		 high--;
		        	 }
		        	 */
		        	 for(int i=1; i<8; i++){
		        		 if(recommendLine.contains(cust.user_recommendations[check_here][i])==false)
		        		 {
		             //    System.out.println("Recommendations for user "+check_here+" : "+ cust.user_recommendations[check_here][i]);
		        		 }
		        	 }
		        	 // printing recommendations        	 
		        	 
		        	 
		        	 check_here++;
	         }	 
				 
		       
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
			/*	 int enter = 0;
				 while (enter == 0)
				 {
					 System.out.println("Enter your preference : ");
			      InputStreamReader input = new InputStreamReader(System.in);
			      BufferedReader cust_prev_recall = new BufferedReader(input);
			      System.out.println("Customer previous choice of recalls : " + cust_prev_recall);
			      System.out.println("Enter 0 to continue : ");
			      InputStreamReader ent = new InputStreamReader(System.in);
			      BufferedReader ente = new BufferedReader(ent);
			      enter=String2int(ente);
				 }
			//    System.out.println("input data: " + input_data );	*/
				 
				 
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
            }


    }

	private static int String2int(BufferedReader ente) {
		// TODO Auto-generated method stub
		return 0;
	}

}






