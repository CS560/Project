/**
 * http://docs.mongodb.org/ecosystem/tutorial/getting-started-with-java-driver/
 */

package db.mongo;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

public class MongoDatabase {
	
	private String host;
	private MongoClient client;
	DB db;
	
	public MongoDatabase(String host) {
		this.host = host;
		connect("test");
	}
	
	public void connect(String dbName) {
		try {
			client = new MongoClient(host);
			db = client.getDB(dbName);
			_Logger.log("connected");
		} catch (UnknownHostException e) {
			_Logger.log(e.getMessage());
		}
		
	}
	
	public void insert(String json) {
		DBObject doc = (DBObject) JSON.parse(json);
	}
	public void update() {
		
	}
	public Set<String> getCollectionNames() {
		Set<String> collections =  db.getCollectionNames();
		return collections;
	}
}
