package com.example.firstapp.clientside;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class Http {
	/**
	 * Post URL encoded params to URL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, ArrayList<NameValuePair> params) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		String rString = null;

		//HTTP request
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(params));
			response = client.execute(post);
			StatusLine status = response.getStatusLine();
			
			if(status.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				response.getEntity().writeTo(output);
				output.close();
				rString = output.toString();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			rString = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			rString = e.getMessage();
		} finally {
			//close client?
		}
		return rString;
	}

	/**
	 * Gets HTTP Response from URL
	 * @param url
	 * @return HTTP Response as String
	 */
	public static String get(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		String rString = null;

		//HTTP request
		try {
			HttpGet get = new HttpGet(url);
			response = client.execute(get);
			StatusLine status = response.getStatusLine();

			if(status.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				response.getEntity().writeTo(output);
				output.close();
				rString = output.toString();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			rString = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			rString = e.getMessage();
		} finally {
			//close client?
		}
		
		return rString;
	}
}
