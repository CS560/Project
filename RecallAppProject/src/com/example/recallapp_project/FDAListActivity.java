package com.example.recallapp_project;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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


public class FDAListActivity extends ListActivity {
		//test url xml format
		String URL = "";
		// 5 vaules we will parsing to our app
		static final String Recall="docs";
		static final String Title ="comments";
		static final String Description ="description";
		static final String Date ="last_modified";
		static final String Link="url";
		static final String num="numFound";
		JSONArray recalls=null;
		ArrayList<HashMap<String, String>> recallRList;
		AlertDialog.Builder alt_bld;
		private ProgressDialog pD;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			recallRList = new ArrayList<HashMap<String, String>>();
			ListView rlv=getListView();
			
//			alt_bld = new AlertDialog.Builder(this);
//			alt_bld.setMessage("Sorry.Dont have record for what are you looking for ?")
//			.setCancelable(false)
//			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//			dialog.dismiss();
//			}
//			});
			
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
	                Intent in = new Intent(getApplicationContext(), SingleFDAItemActivity.class);
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
	            pD = new ProgressDialog(FDAListActivity.this);
	            pD.setMessage("Loading...");
	            pD.setCancelable(false);
	            pD.show();
	 
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	            // Creating service handler class instance
	            HttpHandler sh = new HttpHandler();
	    		Bundle b=getIntent().getExtras();
	    		String sbc = b.getString("sctg");
	    		String org = b.getString("org");
	    		String cg =b.getString("ctg");
	    		String srh = b.getString("srh");
	    		System.out.println(sbc+"`````"+org+"1111111"+cg+"..........."+srh);
	    		if(srh.equals("")){
	    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+keywords%3A"+sbc+"%0A&rows=2000&sort=last_modified+desc&wt=json";
	    		}else{
	    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+keywords%3A"+sbc+"+AND+description%3A"+srh+"%0A&rows=2000&sort=last_modified+desc&wt=json";
	    		}
	    		// Making a request to url and getting response
	            String js = sh.makeHttpCall(URL, HttpHandler.GET);
	            System.out.println(URL);
	            Log.d("Response: ", "> " + js);
	            if (js != null) {
	                try {
	                    JSONObject jsonObj = new JSONObject(js);
	                    JSONObject jo = jsonObj.getJSONObject("response"); 
	                    // Getting JSON Array node
	                    recalls = jo.getJSONArray(Recall);
	                    String numr = jo.getString(num);
	                    System.out.println(numr);
	                    // looping through All recalls
	                    for (int i = 0; i < recalls.length(); i++) {
	                        JSONObject jo1 = recalls.getJSONObject(i);
	                         
	                        String title = jo1.getString(Title);
	                        String description = jo1.getString(Description);
	                        String link = jo1.getString(Link);
	                        String date = jo1.getString(Date);
	                        //System.out.println(title+""+description+""+link+""+date);
	 
	                        // tmp hashmap for single recall
	                        HashMap<String, String> recall = new HashMap<String, String>();
	 
	                        // adding each child node to HashMap key => value
	                        recall.put(Title, title);
	                        recall.put(Description, description);
	                        recall.put(Link, link);
	                        recall.put(Date, date);
	 
	                        // adding recalls to recall list
	                        recallRList.add(recall);
	                    }
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	            } else {
	                Log.e("ServiceHandler", "cannot get data from the API");
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
	            HttpHandler sh = new HttpHandler();
	    		Bundle b=getIntent().getExtras();
	    		String sbc = b.getString("sctg");
	    		String org = b.getString("org");
	    		String cg =b.getString("ctg");
	    		String srh = b.getString("srh");
	    		System.out.println(sbc+"`````"+org+"1111111"+cg+"..........."+srh);
	    		if(srh.equals("")){
	    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+keywords%3A"+sbc+"%0A&rows=2000&sort=last_modified+desc&wt=json";
	    		}else{
	    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+keywords%3A"+sbc+"+AND+description%3A"+srh+"%0A&rows=2000&sort=last_modified+desc&wt=json";
	    		}
	    		// Making a request to url and getting response
	            String js = sh.makeHttpCall(URL, HttpHandler.GET);
	            System.out.println(URL);

	            if (js != null) {
	            	try{
	                    JSONObject jsonObj = new JSONObject(js);
	                    JSONObject jo = jsonObj.getJSONObject("response"); 
	                    // Getting JSON Array node
	                    recalls = jo.getJSONArray(Recall);
	                    String numr = jo.getString(num);
	                    System.out.println(numr);
	                    if (numr.equals("0")){
	                    	alt_bld = new AlertDialog.Builder(FDAListActivity.this);
	            			alt_bld.setMessage("Dont have the record you are looking for");
	            			alt_bld.setCancelable(false);
	            			alt_bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                    		public void onClick(DialogInterface dialog, int id) {
	                    		dialog.cancel();
	                    		}
	                    		});
	                    	AlertDialog alert = alt_bld.create();
	                    	
	            			// Title for AlertDialog
	            			alert.setTitle("Sorry");
	                    	alert.show();
	                    	
	                    }else{
	            
	            ListAdapter adapter = new SimpleAdapter(
	                    FDAListActivity.this, recallRList,
	                    R.layout.recent_recall_view, new String[] { Title,Date,Description,Link }, new int[] {
	                            R.id.title, R.id.pubDate, R.id.description,R.id.link });
	 
	            setListAdapter(adapter);
	                      }
	 
	    }catch (JSONException e) {
            e.printStackTrace();
        }
	            }
	}

		}}