package six.group.kdm.mongo;

import java.util.Set;

public class AppUser {
	
	public Set<String> subscriptions;

	public String username;
	public AppUser(String username) {
		this.username = username;
	}
}
