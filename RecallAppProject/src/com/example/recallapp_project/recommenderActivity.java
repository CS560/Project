package com.example.recallapp_project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class recommenderActivity extends Activity {
	String URL = "";
	EditText userN;
	Button rcm;
	TextView rcd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommender_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
//		userN =(EditText) findViewById(R.id.UserName);
//		rcm =(Button) findViewById(R.id.enter);
		addListenerOnButton();
	}
	public void addListenerOnButton() {
		userN =(EditText) findViewById(R.id.UserName);
		rcm =(Button) findViewById(R.id.enter);
			rcm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) { 
				TextView rcd= (TextView)findViewById(R.id.rcd);
				TextView subs = (TextView)findViewById(R.id.subslist);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
				String responseString = null;
				
	    		EditText userN =(EditText) findViewById(R.id.UserName);
	    		CharSequence uN = (CharSequence) userN.getText();
	    		URL = "http://134.193.136.127:8080/six.group.kdm/rest/mongo/recommend?username="+uN;
	    		try {
	    		HttpGet httpPost = new HttpGet(URL);
	    		httpPost.setHeader("Content-Type", "application/json");
	    		response = httpclient.execute(httpPost);
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        response.getEntity().writeTo(out);
			        out.close();
			        responseString = out.toString();
				     String of = JSONAnalysis(responseString);
				     String sub = JSONAnalysis(responseString);
				     rcd.setText(of);
				     subs.setText(sub);
				     
				     System.out.println(of);	    
				} else{
			        //Closes the connection.
			        response.getEntity().getContent().close();
			        throw new IOException(statusLine.getReasonPhrase());
			    }
			} catch (ClientProtocolException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			}});
			}
			public String JSONAnalysis(String jsonString)
		    {
				String rcd="";
		    	JSONObject jSONObj;
		    	//String temp="";
				try {
					jSONObj = new JSONObject(jsonString);
					String[] rc= jSONObj.getString("recommendation").split(",");
					int count=0;
					for (String r: rc){
						rcd+=r+",";
						count++;
						if(count==3)
							break;
						}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return rcd;
		    }
			public String JSONAnalysis1(String jsonString)
		    {
				String subs="";
		    	JSONObject jSONObj;
		    	//String temp="";
				try {
					jSONObj = new JSONObject(jsonString);
					String[] rc= jSONObj.getString("subscriptions").split(",");
					for (String r: rc){
						subs+=r+",";
						}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return subs;
		    }
}
