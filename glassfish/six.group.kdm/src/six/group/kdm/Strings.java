package six.group.kdm;

public class Strings {

	String url;
	String collection;
	
	public Strings() {
		
	}
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public String getCollection() { return collection; }
	public void setCollection(String collection) { this.collection = collection; }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nurl: " + url);
		builder.append("\ncollection: " + collection);
		
		return builder.toString();
	}
}
