package six.group.kdm;

public class Strings {

	String url;
	String collection;
	String mongoHost;
	
	public Strings() {
		
	}
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public String getCollection() { return collection; }
	public void setCollection(String collection) { this.collection = collection; }
	
	public String getMongoHost() { return mongoHost; }
	public void setMongoHost(String host) { this.mongoHost = host; }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nurl: " + url);
		builder.append("\ncollection: " + collection);
		builder.append("\nmongoHost: " + mongoHost);
		
		return builder.toString();
	}
}
