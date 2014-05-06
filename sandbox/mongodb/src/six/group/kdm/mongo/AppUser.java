package six.group.kdm.mongo;

import java.util.HashSet;
import java.util.Set;

public class AppUser {
	
	public Set<String> subscriptions;
	public String username;
	public String password;

	public AppUser(String username) {
		this.username = username;
		this.subscriptions = new HashSet<String>();
	}
	public AppUser(String username, String password) {
		this.username = username;
		this.password = password;
		this.subscriptions = new HashSet<String>();
	}
}