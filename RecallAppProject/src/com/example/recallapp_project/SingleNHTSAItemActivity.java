package com.example.recallapp_project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class SingleNHTSAItemActivity extends Activity{

		// 4 information from xml
	static final String Title ="subject";
	static final String Description ="description";
	static final String Date ="last_modified";
	static final String Link="url";
	static final String keyWords="keywords";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get jason values from previous intent
        String title = in.getStringExtra(Title);
        String description = in.getStringExtra(Description);
        String date = in.getStringExtra(Date);
        String link = in.getStringExtra(Link);
        String kw =in.getStringExtra(keyWords);
        // Displaying all values on the screen
        TextView lblTitle = (TextView) findViewById(R.id.title_label);
        TextView lblDesc = (TextView) findViewById(R.id.description_label);
        TextView lblDate = (TextView) findViewById(R.id.Date_label);
        TextView lblLink = (TextView) findViewById(R.id.Link_label);
        TextView lblkw = (TextView) findViewById(R.id.mf_label);
        
        lblTitle.setText(title);
        lblDesc.setText(description);
        lblDate.setText(date);
        lblLink.setText(link);
        lblkw.setText(kw);
        
    }
}


