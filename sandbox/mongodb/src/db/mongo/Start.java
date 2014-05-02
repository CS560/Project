package db.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;

import six.group.kdm.mongo.TiruStaticNhtsaRecommender;
import six.group.kdm.mongo.WeightedLabel;


public class Start {


	public static void main(String[] args) throws UnknownHostException {
		
		TiruStaticNhtsaRecommender r = new TiruStaticNhtsaRecommender();
		ArrayList<WeightedLabel> l = r.recommend("user01");
		for(int i = 0; i < l.size(); i++) {
			System.out.println(l.get(i).label + " - " + l.get(i).weight);
		}
		/*
		String j1 = "{'id':'james','email':'this.clark@gmail.com','password':'password'}";
		
		String host = "54.186.3.69";
		MongoDatabase db = new MongoDatabase(host);
		//db.insert(j1);
		db.doCursorDoc();
		*/
	}
}
