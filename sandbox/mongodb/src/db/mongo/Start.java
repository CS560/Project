package db.mongo;

import java.util.Set;

public class Start {


	public static void main(String[] args) {
		String host = "54.186.3.69";
		MongoDatabase db = new MongoDatabase(host);
		Set<String> set =  db.getCollectionNames();
		for(String s : set) {
			System.out.println(s);
		}
	}
}
