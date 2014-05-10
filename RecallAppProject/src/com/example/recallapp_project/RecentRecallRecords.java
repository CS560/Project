package com.example.recallapp_project;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;


import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.app.ProgressDialog;


public class RecentRecallRecords extends ListActivity {
	 private ProgressDialog pD;
	//test url json format
	static final String URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3AFDA%0A&rows=50&sort=last_modified+desc&wt=json";
	// 5 vaules we will parsing to our app
	static final String Recall="docs";
	static final String Title ="comments";
	static final String Description ="description";
	static final String Date ="last_modified";
	static final String Link="url";
	JSONArray recalls=null;
	ArrayList<HashMap<String, String>> recallRList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		recallRList = new ArrayList<HashMap<String, String>>();
		ListView rlv=getListView();
		 
       
        rlv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String title = ((TextView) view.findViewById(R.id.title)).getText().toString();
                String description = ((TextView) view.findViewById(R.id.description)).getText().toString();
                String pubDate = ((TextView) view.findViewById(R.id.pubDate)).getText().toString();
                String link = ((TextView) view.findViewById(R.id.link)).getText().toString();
                 
                // Starting new intent
                Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
                in.putExtra(Title, title);
                in.putExtra(Description, description);
                in.putExtra(Date, pubDate);
                in.putExtra(Link, link);
                startActivity(in);
 
            }
        });
        new GetRecalls().execute();
    }
	private class GetRecalls extends AsyncTask<Void, Void, Void> {
		 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pD = new ProgressDialog(RecentRecallRecords.this);
            pD.setMessage("Loading...");
            pD.setCancelable(false);
            pD.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            HttpHandler sh = new HttpHandler();
 
            // Making a request to url and getting response
            String js = sh.makeHttpCall(URL, HttpHandler.GET);
 
            Log.d("Response: ", "> " + js);
 
            if (js != null) {
                try {
                    JSONObject jsonObj = new JSONObject(js);
                    JSONObject jo = jsonObj.getJSONObject("response"); 
                    // Getting JSON Array node
                    recalls = jo.getJSONArray(Recall);
 
                    // looping through All recalls
                    for (int i = 0; i < recalls.length(); i++) {
                        JSONObject jo1 = recalls.getJSONObject(i);
                         
                        String title = jo1.getString(Title);
                        String description = jo1.getString(Description);
                        String link = jo1.getString(Link);
                        String date = jo1.getString(Date);
                        System.out.println(title+""+description+""+link+""+date);
 
                        // tmp hashmap for single recall
                        HashMap<String, String> recall = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        recall.put(Title, title);
                        recall.put(Description, description);
                        recall.put(Link, link);
                        recall.put(Date, date);
 
                        // adding contact to recall list
                        recallRList.add(recall);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the API");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pD.isShowing())
                pD.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    RecentRecallRecords.this, recallRList,
                    R.layout.recent_recall_view, new String[] { Title,Date,Description,Link }, new int[] {
                            R.id.title, R.id.pubDate, R.id.description,R.id.link });
 
            setListAdapter(adapter);
        }
 
    }
	
}



