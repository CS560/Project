/*
 * Copyright (c) 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

/**
 * http://www.chimpler.com
 */
public class CarsTSVToSeq {
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
			String[] tokens = line.split("\t", 47);
			if (tokens.length != 47) {
				System.out.println("Skip line: " + line);
				continue;
			}
			String cmplid = tokens[0];
			String odino = tokens[1];
			String mfr_name = tokens[2];
			String maketxt = tokens[3];
			String modeltxt = tokens[4];
			String yeartxt = tokens[5];
			String crash = tokens[6];
			String faildate = tokens[7];
			String fire = tokens[8];
			String injured = tokens[9];
			String deaths = tokens[10];
			String compdesc = tokens[11];
			String city = tokens[12];
			String state = tokens[13];
			String vin = tokens[14];
			String datea = tokens[15];
			String ldate = tokens[16];
			String miles = tokens[17];
			String occurences = tokens[18];
			String cdescr = tokens[19];
			String cmpl_type = tokens[20];
			String police_rpt_yn = tokens[21];
			String purch_dt = tokens[22];
			String orig_owner_yn = tokens[23];
			String anti_brakes_yn = tokens[24];
			String cruise_cont_yn = tokens[25];
			String num_cyls = tokens[26];
			String driv_train = tokens[27];
			String fuel_sys = tokens[28];
			String fuel_type = tokens[29];
			String trans_type = tokens[30];
			String veh_speed = tokens[31];
			String dot = tokens[32];
			String tire_size = tokens[33];
			String loc_of_fire = tokens[34];
			String tire_fail_type = tokens[35];
			String orig_equip_yn = tokens[36];
			String manuf_dt = tokens[37];
			String seat_type = tokens[38];
			String restraint_type = tokens[39];
			String dealer_name = tokens[40];
			String dealer_tel = tokens[41];
			String dealer_city = tokens[42];
			String dealer_state = tokens[43];
			String dealer_zip = tokens[44];
			String prod_type = tokens[45];
			String repaired_yn = tokens[46];
			key.set("/" + cmplid + "/" + compdesc);
			value.set(cdescr);
			writer.append(key, value);
			count++;
		}
		reader.close();
		writer.close();
		System.out.println("Wrote " + count + " entries.");
	}
}
