/* Recall project for kdm subject
 */

package project.bayes;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;

// sequential class for converting text data into sequential files
// mahout needs data in sequential form to work on it

public class CarsTXTToSeq {
	public static void main(String args[]) throws Exception {
		if (args.length != 2) {
			System.err.println("Arguments: [input tsv file] [output sequence file]");
			return;
		}
		String inputFileName = args[0];
		String outputDirName = args[1];
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(configuration);
		Writer writer = new SequenceFile.Writer(fs, configuration, new Path(outputDirName + "/carsseq"),
				Text.class, Text.class);
		
		int count = 0;
		BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
		Text key = new Text();
		Text value = new Text();
		while(true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			//Every line or input data record contains 47 columns contains details like complaint id,manufaturer, fail date etc., 
			String[] tokens = line.split("\t", 47);
			if (tokens.length != 47) {        
			    System.out.println("Skip line: " + line);  //if we found any incorrectly entered record which means it doesn't have 47 columns or 47 fieds related to recall
				continue;
			}
			String cmplid = tokens[0];    // NHTSA'S INTERNAL UNIQUE SEQUENCE NUMBER.IT IS AN UPDATEABLE FIELD,THUS DATA FOR A GIVEN RECORD POTENTIALLY COULD CHANGE FROM ONE DATA OUTPUT FILE TO THE NEXT.
			String odino = tokens[1];    //NHTSA'S INTERNAL REFERENCE NUMBER.THIS NUMBER MAY BE REPEATED FOR MULTIPLE COMPONENTS. ALSO, IF LDATE IS PRIOR TO DEC 15, 2002, THIS NUMBER MAY BE REPEATED FOR MULTIPLE 
			String mfr_name = tokens[2];  //MANUFACTURER'S NAME
			String maketxt = tokens[3];   //VEHICLE/EQUIPMENT MAKE
			String modeltxt = tokens[4];  //VEHICLE/EQUIPMENT MODEL
			String yeartxt = tokens[5];   //MODEL YEAR, 9999 IF UNKNOWN or N/A
			String crash = tokens[6];     // WAS VEHICLE INVOLVED IN A CRASH, 'Y' OR 'N'
			String faildate = tokens[7];  //DATE OF INCIDENT (YYYYMMDD)
			String fire = tokens[8];      //WAS VEHICLE INVOLVED IN A FIRE 'Y' OR 'N'
			String injured = tokens[9];   //NUMBER OF PERSONS INJURED
			String deaths = tokens[10];   //NUMBER OF FATALITIES
			String compdesc = tokens[11]; //SPECIFIC COMPONENT'S DESCRIPTION
			String city = tokens[12];     //CONSUMER'S CITY
			String state = tokens[13];    //CONSUMER'S STATE CODE
			String vin = tokens[14];      //VEHICLE'S VIN#
			String datea = tokens[15];    //DATE ADDED TO FILE (YYYYMMDD)
			String ldate = tokens[16];    //DATE COMPLAINT RECEIVED BY NHTSA (YYYYMMDD)
			String miles = tokens[17];    //VEHICLE MILEAGE AT FAILURE
			String occurences = tokens[18];//NUMBER OF OCCURRENCES
			String cdescr = tokens[19];    //DESCRIPTION OF THE COMPLAINT
			String cmpl_type = tokens[20]; /*SOURCE OF COMPLAINT CODE:
							 CAG  =CONSUMER ACTION GROUP
							 CON  =FORWARDED FROM A CONGRESSIONAL OFFICE
							 DP   =DEFECT PETITION,RESULT OF A DEFECT PETITION
							 EVOQ =HOTLINE VOQ
							 EWR  =EARLY WARNING REPORTING
							 INS  =INSURANCE COMPANY
							 IVOQ =NHTSA WEB SITE
							 LETR =CONSUMER LETTER
							 MAVQ =NHTSA MOBILE APP
							 MIVQ =NHTSA MOBILE APP
							 MVOQ =OPTICAL MARKED VOQ
							 RC   =RECALL COMPLAINT,RESULT OF A RECALL INVESTIGATION
							 RP   =RECALL PETITION,RESULT OF A RECALL PETITION
							 SVOQ =PORTABLE SAFETY COMPLAINT FORM (PDF)
							 VOQ  =NHTSA VEHICLE OWNERS QUESTIONNAIRE*/
			String police_rpt_yn = tokens[21];  //WAS INCIDENT REPORTED TO POLICE 'Y' OR 'N'
			String purch_dt = tokens[22];       //DATE PURCHASED (YYYYMMDD)
			String orig_owner_yn = tokens[23];  //WAS ORIGINAL OWNER 'Y' OR 'N'
			String anti_brakes_yn = tokens[24]; //ANTI-LOCK BRAKES 'Y' OR 'N'
			String cruise_cont_yn = tokens[25]; //CRUISE CONTROL 'Y' OR 'N'
			String num_cyls = tokens[26];       //NUMBER OF CYLINDERS
			String driv_train = tokens[27];     //DRIVE TRAIN TYPE [AWD,4WD,FWD,RWD]
			String fuel_sys = tokens[28];       /*FUEL SYSTEM CODE: 
							      FI =FUEL INJECTION
							      TB =TURBO  */                                              
			String fuel_type = tokens[29];      /*FUEL TYPE CODE: 
							      BF =BIFUEL
							      CN =CNG/LPG
							      DS =DIESEL
							      GS =GAS
							      HE =HYBRID ELECTRIC */
			String trans_type = tokens[30];     //VEHICLE TRANSMISSION TYPE [AUTO, MAN]
			String veh_speed = tokens[31];      //VEHICLE SPEED
			String dot = tokens[32];            //DEPARTMENT OF TRANSPORTATION TIRE IDENTIFIER
			String tire_size = tokens[33];      //TIRE SIZE
			String loc_of_fire = tokens[34];    /* LOCATION OF TIRE CODE:
							       FSW =DRIVER SIDE FRONT
							       DSR =DRIVER SIDE REAR
							       FTR =PASSENGER SIDE FRONT
							       PSR =PASSENGER SIDE REAR
							       SPR =SPARE*/
			String tire_fail_type = tokens[35]; /*TYPE OF TIRE FAILURE CODE: 
							      BST =BLISTER
							      BLW =BLOWOUT
							      TTL =CRACK
							      OFR =OUT OF ROUND
							      TSW =PUNCTURE
							      TTR =ROAD HAZARD
							      TSP =TREAD SEPARATION */
			String orig_equip_yn = tokens[36];  //WAS PART ORIGINAL EQUIPMENT 'Y' OR 'N'
			String manuf_dt = tokens[37];       //DATE OF MANUFACTURE (YYYYMMDD)
			String seat_type = tokens[38];      /*TYPE OF CHILD SEAT CODE:
							      B  =BOOSTER
							      C  =CONVERTIBLE
							      I  =INFANT
							      IN =INTEGRATED
							      TD =TODDLER*/
			String restraint_type = tokens[39]; /*INSTALLATION SYSTEM CODE;
							      A =VEHICLE SAFETY BELT
							      B =LATCH SYSTEM */
			String dealer_name = tokens[40];    //DEALER'S NAME
			String dealer_tel = tokens[41];     //DEALER'S TELEPHONE NUMBER
			String dealer_city = tokens[42];    //DEALER'S CITY
			String dealer_state = tokens[43];   //DEALER'S STATE CODE
			String dealer_zip = tokens[44];     //DEALER'S ZIPCODE
			String prod_type = tokens[45];      /* PRODUCT TYPE CODE:
							       V =VEHICLE
							       T =TIRES
							       E =EQUIPMENT
							       C =CHILD RESTRAINT */
			String repaired_yn = tokens[46];    //WAS DEFECTIVE TIRE REPAIRED 'Y' OR 'N'

		       	key.set("/" + compdesc + "/" + cmplid);
			value.set(cdescr);
			writer.append(key, value);
			count++;
		}
		reader.close();
		writer.close();
		System.out.println("Wrote " + count + " entries.");
	}
}
