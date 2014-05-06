package six.group.kdm.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoUtil {

	public static AppUser getAppUser(String username) throws UnknownHostException {
		AppUser user = new AppUser(username);
		String host = "54.186.3.69";
		MongoClient  client = new MongoClient(host);	
		DB db = client.getDB("RecallApp");
		DBCollection appUsers = db.getCollection("appusers");
		DBObject json = (DBObject) JSON.parse("{'username':'" + username + "'}");
		DBCursor cursor = appUsers.find(json);
		
		if(cursor.size() >= 1) {
			String s = cursor.next().toString();
			JSONObject j = new JSONObject(s);
			user.username = j.getString("username");
			JSONArray subs = j.getJSONArray("subscriptions");
			for(int i = 0; i < subs.length(); i++) {
				user.subscriptions.add(subs.getString(i));
			}
		}
		return user;
	}
}
