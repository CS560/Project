package com.example.recallapp_project;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;


public class MainMenu extends Activity {
	Button button,button1,button2;
//	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		button = (Button) findViewById(R.id.button1);
		button1 = (Button) findViewById(R.id.button2);
		button2=(Button) findViewById(R.id.recommender);
		
		
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, SubCategoryMenu1.class);
                            startActivity(intent);   
 
			}
 
		});
		button1.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, RecentRecallRecords.class);
                            startActivity(intent);   
 
			}
 
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(context, recommenderActivity.class);
				startActivity(intent);
			}
		});
	}
 
}
