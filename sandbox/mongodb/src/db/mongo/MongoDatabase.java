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
	private DBCollection userCollection;
	DB db;
	
	/**
	 * constructor
	 * @param host
	 */
	public MongoDatabase(String host) {
		this.host = host;
		connect("test");
	}
	
	/**
	 * connect to a host, use db, then get user collection
	 * @param dbName
	 */
	public void connect(String dbName) {
		try {
			client = new MongoClient(host);
			db = client.getDB(dbName);
			userCollection = db.getCollection("users");
			_Logger.log("connected");
		} catch (UnknownHostException e) {
			_Logger.log(e.getMessage());
		}
	}
	
	/**
	 * insert a new document
	 * @param json
	 */
	public void insert(String json) {
		DBObject doc = (DBObject) JSON.parse(json);
		userCollection.insert(doc);
	}
	
	/**
	 * update an existing document
	 */
	public void update() {
		
	}
	
	/**
	 * what's a cursor doc?
	 */
	public void doCursorDoc() {
		DBCursor cursor = userCollection.find();
		while(cursor.hasNext()) {
			_Logger.log(cursor.next().toString());
		}
	}
	
	/**
	 * get a collection list from a db
	 * @return
	 */
	public Set<String> getCollectionNames() {
		Set<String> collections =  db.getCollectionNames();
		return collections;
	}
}
