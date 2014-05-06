package com.example.recallapp_project;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firstapp.clientside.SimpleHttpClient;

@SuppressLint("NewApi")
public class LoginLayout extends Activity {
	EditText un, pw;
	TextView error;
	Button ok;
	private String resp;
	private String errorMsg;
	
	//called on creation
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		un = (EditText) findViewById(R.id.et_un);
		pw = (EditText) findViewById(R.id.et_pw);
		ok = (Button) findViewById(R.id.btn_login);
		error = (TextView) findViewById(R.id.tv_error);
		
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
						postParameters.add(new BasicNameValuePair("username",un.getText().toString()));
						postParameters.add(new BasicNameValuePair("password",pw.getText().toString()));
						String response = null;
						try {
							response = SimpleHttpClient.executeHttpPost("http://localhost:8080/six.group.kdm/rest/mongo/auth",postParameters);
							String res = response.toString();
							resp = res.replaceAll("\\s+", "");//what this do?
						} catch (Exception e) {
							e.printStackTrace();
							errorMsg = e.getMessage();
						}
					}
					
				}).start();
				try {
					Thread.sleep(1000);
					error.setText(resp);
					
					if(null != errorMsg && !errorMsg.isEmpty()) {
						error.setText(errorMsg);
					}
				} catch (Exception e) {
					error.setText(e.getMessage());
				}
			}
		});
	}
}
