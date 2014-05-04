package kdm.data;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.api.Param;

@Path("/hello")
public class Hello {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Plain Text Hello";
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXmlHello() {
		return "<?xml version=\"1.0\"?>" + "<hello>XML Hello" + "</hello>";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<head><title>" + "HTML Hello" + "</title></head>"
		        + "<body><h1>" + "HTML Hello" + "</h1></body>" + "</html> ";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayJsonHello() {
		return "{\"json\":\"hello\"}";
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String putHello(String message) {
		return "{\"msg\":\"" + message + "\"}";
	}
}
