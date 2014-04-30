package db.mongo;

import java.util.Set;

public class Start {


	public static void main(String[] args) {
		String j1 = "{'id':'james','email':'this.clark@gmail.com','password':'password'}";
		
		String host = "54.186.3.69";
		MongoDatabase db = new MongoDatabase(host);
		//db.insert(j1);
		db.doCursorDoc();
	}
}
