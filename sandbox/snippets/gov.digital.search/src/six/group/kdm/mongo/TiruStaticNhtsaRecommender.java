package six.group.kdm.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TiruStaticNhtsaRecommender {

	Map<String,String> recommender;
	
	public TiruStaticNhtsaRecommender() {
		recommender = new HashMap<String,String>();
		recommender.put("Auto","Auto::0,Auto::10,Children::0,Company::-6,Others::-6,USA::-4,Limited liability company::-8,Buisiness::-8,Engineering::-4,Coaches::-6,Manufacturing company::-8,Industries::-8,Equipment::-8,Trailer::-8,Corporation::-10,California::-10,International::-10,Interiors::-10,Private Ltd company::-10,National::-10,Buildings::-10,Men::-10,Electronics::-10,Commercial::-10,");
		recommender.put("Children","Children::0,Auto::3,Children::7,Company::-5,Others::-3,USA::-3,Limited liability company::-5,Buisiness::-7,Engineering::-3,Coaches::-3,Manufacturing company::-7,Industries::-5,Equipment::-7,Trailer::-7,Corporation::-5,California::-7,International::-7,Interiors::-7,Private Ltd company::-7,National::-7,Buildings::-7,Men::-7,Electronics::-7,Commercial::-7,");
		recommender.put("Company","Company::0,Auto::-15,Children::-17,Company::19,Others::-15,USA::-17,Limited liability company::-17,Buisiness::-19,Engineering::-17,Coaches::-19,Manufacturing company::-17,Industries::-19,Equipment::-11,Trailer::-15,Corporation::-19,California::-19,International::-17,Interiors::-17,Private Ltd company::-17,National::-17,Buildings::-13,Men::-17,Electronics::-17,Commercial::-17,");
		recommender.put("Others","Others::0,Auto::-13,Children::-13,Company::-13,Others::17,USA::-15,Limited liability company::-17,Buisiness::-17,Engineering::-13,Coaches::-15,Manufacturing company::-7,Industries::-17,Equipment::-7,Trailer::-3,Corporation::-7,California::-11,International::-11,Interiors::-15,Private Ltd company::-13,National::-13,Buildings::-17,Men::-17,Electronics::-17,Commercial::-17,");
		recommender.put("USA","USA::0,Auto::-1,Children::-3,Company::-5,Others::-5,USA::7,Limited liability company::-5,Buisiness::-5,Engineering::-1,Coaches::-5,Manufacturing company::-5,Industries::-3,Equipment::-5,Trailer::-7,Corporation::-7,California::-7,International::-7,Interiors::-7,Private Ltd company::-7,National::-7,Buildings::-7,Men::-7,Electronics::-7,Commercial::-7,");
		recommender.put("Limited liability company","Limited liability company::0,Auto::-1,Children::-1,Company::-1,Others::-3,USA::-1,Limited liability company::3,Buisiness::-3,Engineering::-3,Coaches::-3,Manufacturing company::-3,Industries::-3,Equipment::-3,Trailer::-3,Corporation::-3,California::-3,International::-3,Interiors::-3,Private Ltd company::-3,National::-3,Buildings::-3,Men::-3,Electronics::-3,Commercial::-3,");
		recommender.put("Buisiness","Buisiness::0,Auto::0,Children::-2,Company::-2,Others::-2,USA::0,Limited liability company::-2,Buisiness::2,Engineering::-2,Coaches::-2,Manufacturing company::-2,Industries::-2,Equipment::-2,Trailer::-2,Corporation::-2,California::-2,International::-2,Interiors::-2,Private Ltd company::-2,National::-2,Buildings::-2,Men::-2,Electronics::-2,Commercial::-2,");
		recommender.put("Engineering","Engineering::0,Auto::1,Children::-1,Company::-3,Others::-1,USA::1,Limited liability company::-5,Buisiness::-5,Engineering::5,Coaches::-3,Manufacturing company::-5,Industries::-3,Equipment::-3,Trailer::-3,Corporation::-3,California::-5,International::-5,Interiors::-5,Private Ltd company::-5,National::-5,Buildings::-5,Men::-5,Electronics::-5,Commercial::-5,");
		recommender.put("Coaches","Coaches::0,Auto::0,Children::0,Company::-4,Others::-2,USA::-2,Limited liability company::-4,Buisiness::-4,Engineering::-2,Coaches::4,Manufacturing company::-2,Industries::-4,Equipment::-4,Trailer::-4,Corporation::0,California::0,International::-2,Interiors::-4,Private Ltd company::-4,National::-2,Buildings::-4,Men::-4,Electronics::-4,Commercial::-4,");
		recommender.put("Manufacturing company","Manufacturing company::0,Auto::-7,Children::-9,Company::-7,Others::1,USA::-7,Limited liability company::-9,Buisiness::-9,Engineering::-9,Coaches::-7,Manufacturing company::9,Industries::-9,Equipment::-7,Trailer::-5,Corporation::1,California::-7,International::-7,Interiors::-9,Private Ltd company::-7,National::-5,Buildings::-7,Men::-9,Electronics::-9,Commercial::-7,");
		recommender.put("Industries","Industries::0,Auto::0,Children::0,Company::-2,Others::-2,USA::2,Limited liability company::-2,Buisiness::-2,Engineering::0,Coaches::-2,Manufacturing company::-2,Industries::2,Equipment::-2,Trailer::-2,Corporation::-2,California::-2,International::-2,Interiors::-2,Private Ltd company::-2,National::-2,Buildings::-2,Men::-2,Electronics::-2,Commercial::-2,");
		recommender.put("Equipment","Equipment::0,Auto::-6,Children::-8,Company::0,Others::2,USA::-6,Limited liability company::-8,Buisiness::-8,Engineering::-6,Coaches::-8,Manufacturing company::-6,Industries::-8,Equipment::8,Trailer::0,Corporation::-6,California::-6,International::-6,Interiors::-8,Private Ltd company::-6,National::-8,Buildings::-2,Men::-6,Electronics::-6,Commercial::-8,");
		recommender.put("Trailer","Trailer::0,Auto::-10,Children::-12,Company::-8,Others::2,USA::-12,Limited liability company::-12,Buisiness::-12,Engineering::-10,Coaches::-12,Manufacturing company::-8,Industries::-12,Equipment::-4,Trailer::12,Corporation::-8,California::-10,International::-8,Interiors::-8,Private Ltd company::-8,National::-8,Buildings::-12,Men::-12,Electronics::-12,Commercial::-12,");
		recommender.put("Corporation","Corporation::0,Auto::-10,Children::-8,Company::-10,Others::0,USA::-10,Limited liability company::-10,Buisiness::-10,Engineering::-8,Coaches::-6,Manufacturing company::0,Industries::-10,Equipment::-8,Trailer::-6,Corporation::10,California::-6,International::-8,Interiors::-8,Private Ltd company::-8,National::-4,Buildings::-10,Men::-10,Electronics::-10,Commercial::-10,");
		recommender.put("California","California::0,Auto::-6,Children::-6,Company::-6,Others::0,USA::-6,Limited liability company::-6,Buisiness::-6,Engineering::-6,Coaches::-2,Manufacturing company::-4,Industries::-6,Equipment::-4,Trailer::-4,Corporation::-2,California::6,International::0,Interiors::-4,Private Ltd company::-4,National::-4,Buildings::-6,Men::-6,Electronics::-6,Commercial::-6,");
		recommender.put("International","International::0,Auto::-4,Children::-4,Company::-2,Others::2,USA::-4,Limited liability company::-4,Buisiness::-4,Engineering::-4,Coaches::-2,Manufacturing company::-2,Industries::-4,Equipment::-2,Trailer::0,Corporation::-2,California::2,International::4,Interiors::-4,Private Ltd company::-4,National::-4,Buildings::-4,Men::-4,Electronics::-4,Commercial::-4,");
		recommender.put("Interiors","Interiors::0,Auto::-5,Children::-5,Company::-3,Others::-3,USA::-5,Limited liability company::-5,Buisiness::-5,Engineering::-5,Coaches::-5,Manufacturing company::-5,Industries::-5,Equipment::-5,Trailer::-1,Corporation::-3,California::-3,International::-5,Interiors::5,Private Ltd company::-5,National::-5,Buildings::-5,Men::-5,Electronics::-5,Commercial::-5,");
		recommender.put("Private Ltd company","Private Ltd company::0,Auto::-5,Children::-5,Company::-3,Others::-1,USA::-5,Limited liability company::-5,Buisiness::-5,Engineering::-5,Coaches::-5,Manufacturing company::-3,Industries::-5,Equipment::-3,Trailer::-1,Corporation::-3,California::-3,International::-5,Interiors::-5,Private Ltd company::5,National::-5,Buildings::-5,Men::-5,Electronics::-5,Commercial::-5,");
		recommender.put("National","National::0,Auto::-5,Children::-5,Company::-3,Others::-1,USA::-5,Limited liability company::-5,Buisiness::-5,Engineering::-5,Coaches::-3,Manufacturing company::-1,Industries::-5,Equipment::-5,Trailer::-1,Corporation::1,California::-3,International::-5,Interiors::-5,Private Ltd company::-5,National::5,Buildings::-5,Men::-5,Electronics::-5,Commercial::-5,");
		recommender.put("Buildings","Buildings::0,Auto::-6,Children::-6,Company::0,Others::-6,USA::-6,Limited liability company::-6,Buisiness::-6,Engineering::-6,Coaches::-6,Manufacturing company::-4,Industries::-6,Equipment::0,Trailer::-6,Corporation::-6,California::-6,International::-6,Interiors::-6,Private Ltd company::-6,National::-6,Buildings::6,Men::-4,Electronics::-4,Commercial::-6,");
		recommender.put("Men","Men::0,Auto::-2,Children::-2,Company::0,Others::-2,USA::-2,Limited liability company::-2,Buisiness::-2,Engineering::-2,Coaches::-2,Manufacturing company::-2,Industries::-2,Equipment::0,Trailer::-2,Corporation::-2,California::-2,International::-2,Interiors::-2,Private Ltd company::-2,National::-2,Buildings::0,Men::2,Electronics::-2,Commercial::-2,");
		recommender.put("Electronics","Electronics::0,Auto::-1,Children::-1,Company::1,Others::-1,USA::-1,Limited liability company::-1,Buisiness::-1,Engineering::-1,Coaches::-1,Manufacturing company::-1,Industries::-1,Equipment::1,Trailer::-1,Corporation::-1,California::-1,International::-1,Interiors::-1,Private Ltd company::-1,National::-1,Buildings::1,Men::-1,Electronics::1,Commercial::-1,");
		recommender.put("Commercial","Commercial::0,Auto::-1,Children::-1,Company::1,Others::-1,USA::-1,Limited liability company::-1,Buisiness::-1,Engineering::-1,Coaches::-1,Manufacturing company::1,Industries::-1,Equipment::-1,Trailer::-1,Corporation::-1,California::-1,International::-1,Interiors::-1,Private Ltd company::-1,National::-1,Buildings::-1,Men::-1,Electronics::-1,Commercial::1,");
	}
	public ArrayList<WeightedLabel> recommend(String username) throws UnknownHostException {
		//sorter to produce a weighted value
		ArrayList<WeightedLabel> sorter = new ArrayList<WeightedLabel>();
		//the keys used in the recommender structure
		Set<String> keys = recommender.keySet();
		Iterator<String> it = keys.iterator();
		//easy access to the labels we will be adding weights to
		Map<String, WeightedLabel> categories = new HashMap<String, WeightedLabel>();
		
		//build the categories map based on recommender keys
		while(it.hasNext()) {
			String key = it.next();
			categories.put(key, new WeightedLabel(key));
		}
		
		//get user from mongo
		AppUser user = MongoUtil.getAppUser(username);
		//get user subscription list
		Set<String> subscriptions = user.subscriptions;
		Iterator<String> subs = subscriptions.iterator();
		//while list has items do
		while(subs.hasNext()) {
			//each item in subs is a key
			String key = subs.next();
			//each key accesses one line from the recommender
			String line = recommender.get(key);
			//every line is delimited by commas
			String[] commaSplit = line.split(",");
			//for every delimited colon, there is an internal string,int pair
			for (String s : commaSplit) {
				String[] colonSplit = s.split("::");
				String innerKey = colonSplit[0];
				int weight = Integer.parseInt(colonSplit[1]);
				//as long as the keys don't match, use the data
				if(!key.equals(innerKey)) {
					//weigh the categories
					categories.get(innerKey).weight += weight;
				}
			}
		}
		//add the categories to the sorter and sort
		sorter.addAll(categories.values());
		Collections.sort(sorter, new WeightedLabelComparator());
		return sorter;
	}	
}
