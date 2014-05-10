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
import android.widget.Toast;


public class NHTSAListActivity extends ListActivity {
	// url jason format
	String URL = "";
	// 5 vaules we will parsing to our app
	static final String Recall ="docs";
	static final String Title ="subject";
	static final String Description ="description";
	static final String Date ="last_modified";
	static final String Link="url";
	static final String Keywords="keywords";
	static final String num="numFound";
	JSONArray recalls=null;
	ArrayList<HashMap<String, String>> recallRList;
	private ProgressDialog pD;
	AlertDialog.Builder alt_bld;
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
                String keywords = ((TextView) view.findViewById(R.id.manufacturer)).getText().toString();
                // Starting new intent
                Intent in = new Intent(getApplicationContext(), SingleNHTSAItemActivity.class);
                in.putExtra(Title, title);
                in.putExtra(Description, description);
                in.putExtra(Date, pubDate);
                in.putExtra(Link, link);
                in.putExtra(Keywords,keywords);
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
            pD = new ProgressDialog(NHTSAListActivity.this);
            pD.setMessage("Loading...");
            pD.setCancelable(false);
            pD.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            HttpHandler sh = new HttpHandler();
    		Bundle b=getIntent().getExtras();
    		String cg =b.getString("ctg");
    		String org = b.getString("org");
    		String mf = b.getString("mf");
    		String srh =b.getString("srh");
    		System.out.println("1. "+org+"2. "+cg+"3. "+mf+"4. "+srh);
    		if(mf.equals("")&&srh.equals("")){
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"%0A&rows=200&sort=last_modified+desc&wt=json";
    		}else if(srh.equals("")){
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+description%3A"+mf+"%0A&rows=200&sort=last_modified+desc&wt=json";
    		}else if(mf.equals("")){
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+subject%3A"+srh+"%0A&rows=200&sort=last_modified+desc&wt=json";
    		}else if(cg.equals("")){
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+description%3A"+mf+"+AND+subject%3A"+srh+"%0A&rows=200&sort=last_modified+desc&wt=json";
    		}else{
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+description%3A"+mf+"+AND+subject%3A"+srh+"%0A&rows=200&sort=last_modified+desc&wt=json";

    		}
    		System.out.println(URL);
            // Making a request to url and getting response
            String js = sh.makeHttpCall(URL, HttpHandler.GET);
 
            Log.d("Response: ", "> " + js);
 
            if (js != null) {
                try {
                    JSONObject jsonObj = new JSONObject(js);
                    JSONObject jo = jsonObj.getJSONObject("response"); 
                    // Getting JSON Array node
                    recalls = jo.getJSONArray(Recall);
                    String numr = jo.getString(num);
                    System.out.println(numr);
                    // looping through All Contacts
                    for (int i = 0; i < recalls.length(); i++) {
                        JSONObject jo1 = recalls.getJSONObject(i);
                         
                        String title = jo1.getString(Title);
                        String description = jo1.getString(Description);
                        String link = jo1.getString(Link);
                        String date = jo1.getString(Date);
                        String kw = jo1.getString(Keywords);
                        //System.out.println(title+""+description+""+link+""+date);
 
                        // tmp hashmap for single contact
                        HashMap<String, String> recall = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        recall.put(Title, title);
                        recall.put(Description, description);
                        recall.put(Link, link);
                        recall.put(Date, date);
                        recall.put(Keywords, kw);
                        // adding contact to contact list
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
    		String org = b.getString("org");
    		String cg =b.getString("ctg");
    		String mf = b.getString("mf");
    		String srh = b.getString("srh");
    		System.out.println("1. "+org+"2. "+cg+"3. "+mf+"4. "+srh);
    		if(mf.equals("")&&srh.equals("")){
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"%0A&rows=200&sort=last_modified+desc&wt=json";
    		}else if(srh.equals("")){
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+description%3A"+mf+"%0A&rows=200&sort=last_modified+desc&wt=json";
    		}else if(mf.equals("")){
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+subject%3A"+srh+"%0A&rows=200&sort=last_modified+desc&wt=json";
    		}else{
    			URL = "http://134.193.136.127:8983/solr/group6_shard1_replica1/select?q=category%3A"+org+"+AND+resourcename%3A"+cg+"+AND+description%3A"+mf+"+AND+subject%3A"+srh+"%0A&rows=200&sort=last_modified+desc&wt=json";
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
                    	alt_bld = new AlertDialog.Builder(NHTSAListActivity.this);
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
                    	Toast.makeText(getApplicationContext(), numr+" results", Toast.LENGTH_SHORT).show();
                    	ListAdapter adapter = new SimpleAdapter(
                    NHTSAListActivity.this, recallRList,
                    R.layout.recent_recall_view, new String[] { Title,Date,Description,Link,Keywords }, new int[] {
                            R.id.title, R.id.pubDate, R.id.description,R.id.link,R.id.manufacturer });
 
            setListAdapter(adapter);
        }
 
    }catch (JSONException e) {
        e.printStackTrace();
    	
    }
            }}
}}

