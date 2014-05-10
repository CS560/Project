package com.example.recallapp_project;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class SubCategoryMenu1 extends Activity implements OnItemSelectedListener {
	Spinner sp1,sp2,sp3;
	ArrayAdapter<String> adp1,adp2,adp3,adp4,adp5,adp6;
	List<String> organzation,categories,subcategories;
	Button cl;
	int position;
	EditText mft, search;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection);
        mft = (EditText) findViewById(R.id.editTextM);
        search =(EditText) findViewById(R.id.search);
        // set sp1 for organzation category
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp1.setOnItemSelectedListener(this);
        organzation = new ArrayList<String>();
        organzation.add("FDA");
        organzation.add("USDA");
        organzation.add("NHTSA");
        organzation.add("CPSC");
        adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, organzation);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);
        
        
        // set sp2 is subcategory for each org
        sp2= (Spinner) findViewById(R.id.spinner2);
        sp2.setOnItemSelectedListener(this);
        //set sp3 is subategory of sp2
        sp3=(Spinner) findViewById(R.id.spinner3);
        sp3.setOnItemSelectedListener(this);
        
        mft.setEnabled(false);
		search.setEnabled(false);
		
        addListenerOnButton();
    } 
 

	@Override
    public void onItemSelected (AdapterView<?> arg1, View view, int arg2, long id) {
    	String category = arg1.getItemAtPosition(arg2).toString();
        Toast.makeText(arg1.getContext(), "Selected: " + category, Toast.LENGTH_SHORT).show();
        mft = (EditText) findViewById(R.id.editTextM);
    	if(arg1.equals(sp1)){
    		if(sp1.getSelectedItem().equals("FDA")||sp1.getSelectedItem().equals("USDA")){
    			sp3.setEnabled(true);
    			mft.setEnabled(false);
    			search.setEnabled(true);
    			search.getText().clear();
    			mft.getText().clear();
    			categories=new ArrayList<String>();
    			categories.add("mislabeled");
    			categories.add("contamination");
    			adp2=new ArrayAdapter<String>(this,
		                  android.R.layout.simple_dropdown_item_1line,categories);
		        adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		        sp2.setAdapter(adp2);
		        select(); 
    		if(sp2.getSelectedItem().equals("mislabeled")||sp2.getSelectedItem().equals("contaminaion"))
    			// add manufactory of the car to the spinner
    			subcategories=new ArrayList<String>();
		        subcategories.add("debris");
		        subcategories.add("animal");
		        subcategories.add("illness");
		        subcategories.add("allergen");
		        subcategories.add("medecine");
		        adp3=new ArrayAdapter<String>(this,
		                  android.R.layout.simple_dropdown_item_1line,subcategories);
		        adp3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		        sp3.setAdapter(adp3);
		        select();
    		
    		}else if(sp1.getSelectedItem().equals("NHTSA")){
    			search.getText().clear();
    			mft.getText().clear();
    			sp3.setEnabled(false);
    	    	mft.setEnabled(true);
    			categories=new ArrayList<String>();
    			categories.add("America");categories.add("Auto");categories.add("Buisiness");
    			categories.add("Builder");categories.add("California");categories.add("Cars");
    			categories.add("Child");categories.add("Coaches");categories.add("Company");
    			categories.add("Commercial");categories.add("Corporation");categories.add("Christ");
    			categories.add("Electric");categories.add("Engineering");categories.add("Enterprises");
    			categories.add("EQUIPMENT");categories.add("Industries");categories.add("Interior");
    			categories.add("International");categories.add("Limited");categories.add("private");
    			categories.add("London");categories.add("Manufacturing");categories.add("MFG.");
    			categories.add("Motor");categories.add("Miriam");categories.add("Morel");
    			categories.add("Mr.");categories.add("Mrs");categories.add("National");
    			categories.add("Tech");categories.add("Trailer");categories.add("Vehicle");
    			categories.add("Others");
    	        adp4=new ArrayAdapter<String>(this,
    	                  android.R.layout.simple_dropdown_item_1line,categories);
    	        adp4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
    	        sp2.setAdapter(adp4);
    	        select();  
//    	    	sp3.setEnabled(false);
//    	    	mft.setEnabled(true);
    	    	
    		}else{
    			sp3.setEnabled(false);
    			mft.setEnabled(false);
    			search.setEnabled(true);
    			search.getText().clear();
    			mft.getText().clear();
    			categories=new ArrayList<String>();
    			categories.add("Safety");
    			categories.add("Property Damage");
    			categories.add("Children");
    			categories.add("Defective");

    	          
    	        adp5=new ArrayAdapter<String>(this,
    	                  android.R.layout.simple_dropdown_item_1line,categories);
    	        adp5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
    	        sp2.setAdapter(adp5);

    	        select();
    		}}
    	}

    private void select() {


        sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
            	String subcategory = arg0.getItemAtPosition(arg2).toString();
                Toast.makeText(arg0.getContext(), "Selected: " + subcategory, Toast.LENGTH_SHORT).show();
                

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
 
    }
    private void addListenerOnButton() {
    	final Context ct =this;
 		cl = (Button) findViewById(R.id.Submit);
 		cl.setOnClickListener(new OnClickListener(){
 			@Override
 			public void onClick(View arg0){
 				Spinner category = (Spinner) findViewById(R.id.spinner2);
 				Spinner subCategory = (Spinner) findViewById(R.id.spinner3);
 				Spinner organzation = (Spinner) findViewById(R.id.spinner1);
 				EditText manufactorer = (EditText) findViewById(R.id.editTextM);
 				EditText search = (EditText) findViewById(R.id.search);
 				CharSequence cT = (CharSequence) category.getSelectedItem();
 				CharSequence sct=(CharSequence) subCategory.getSelectedItem();
 				CharSequence og = (CharSequence) organzation.getSelectedItem();
 				CharSequence mf = (CharSequence) manufactorer.getText();
 				CharSequence srh = (CharSequence) search.getText();
 				String mfText="";
 				String cText="";
 				String scText="";
 				String orgText="";
 				String srhText="";
 				mfText=mf.toString();//manufacturer
 				cText = cT.toString();//categories
 				scText = sct.toString();//subcategory
 				orgText = og.toString();//Organization
 				srhText =srh.toString();//search
 				System.out.println(",,,,,,,,"+orgText);
 				if(orgText.equals("FDA")||orgText.equals("USDA")){
 					Intent intent = new Intent(ct, FDAListActivity.class);
 					Bundle b =new Bundle();
 					Bundle b1 =new Bundle();
 					Bundle b2=new Bundle();
 					Bundle b3 = new Bundle();
 	 				b.putString("org", orgText);
 	 				b1.putString("sctg", scText);
 	 				b2.putString("ctg", cText);
 	 				b3.putString("srh", srhText);
 	 				intent.putExtras(b);
 	 				intent.putExtras(b1);
 	 				intent.putExtras(b2);
 	 				intent.putExtras(b3);
 	 				System.out.println("org: "+b+"cate:   "+b1+"subcate: "+b2+"Search: "+b3);
				    System.out.println(orgText+"cate: "+cText+"subcate: "+scText+"search: "+srhText);
	                startActivity(intent);
 				}else if(orgText.equals("NHTSA")){
 					Intent intent2 = new Intent(ct, NHTSAListActivity.class);
 					Bundle b =new Bundle();
 					Bundle b1 =new Bundle();
 					Bundle b2 = new Bundle();
 					Bundle b3 = new Bundle();
 	 				b.putString("ctg", cText);
 	 				b1.putString("org", orgText);
 	 				b2.putString("mf", mfText);
 	 				b3.putString("srh",srhText);
 	 				intent2.putExtras(b);
 	 				intent2.putExtras(b1);
 	 				intent2.putExtras(b2);
 	 				intent2.putExtras(b3);
 	 				System.out.println("-------"+b+"======="+b1+"+++++++"+b2+"......."+b3);
				    System.out.println(cText+"+++++++"+orgText+"////////"+mfText+"?????????"+srhText);
				    startActivity(intent2);
 				}else {
 					Intent intent3 = new Intent(ct, CPSCListActivity.class);
 					Bundle b1 =new Bundle();
 					Bundle b2 = new Bundle();
 					Bundle b3 = new Bundle();
 	 				b1.putString("ctg", cText);
 	 				b2.putString("org", orgText);
 	 				b3.putString("srh", srhText);
 	 				intent3.putExtras(b1);
 	 				intent3.putExtras(b2);
 	 				intent3.putExtras(b3);
 	 				System.out.println("======="+b1+"+++++++"+b2+"/////////"+srh);
				    System.out.println(cText+"+++++++////////"+orgText+",,,,,,,,,"+srhText);
				    startActivity(intent3);
 				}
 			}
 		});
 	}
	}

