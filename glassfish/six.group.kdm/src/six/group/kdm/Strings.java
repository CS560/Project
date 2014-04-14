package six.group.kdm;

public class Strings {

	String url;
	
	public Strings() {
		
	}
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nurl: " + url);
		
		return builder.toString();
	}
}
