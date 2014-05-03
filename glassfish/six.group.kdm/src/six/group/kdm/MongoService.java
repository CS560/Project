package six.group.kdm;

import java.net.UnknownHostException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import six.group.kdm.mongo.AppUser;
import six.group.kdm.mongo.MongoUtil;
import six.group.kdm.mongo.TiruStaticNhtsaRecommender;

@Path("/mongo")
public class MongoService {
	@GET
	@Path("recommend")
	@Produces("application/json")
	public String recommend(@QueryParam("username") String username) {
		TiruStaticNhtsaRecommender recommender = new TiruStaticNhtsaRecommender();
		try {
			AppUser user= MongoUtil.getAppUser(username);
			String recommendation = recommender.recommend(user.username);

			return "{\"recommendation\":\"" + recommendation + "\"}";
		} catch (UnknownHostException e) {
			//do nothing
			//have the app degrade gracefully by simply not producing a recommendation
		}
		

		return "{\"error\":\"error\"}";

	}

}
