package winterwell.jtwitter;

import interfacesComunes.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.winterwell.jgeoplanet.IGeoCode;
import com.winterwell.jgeoplanet.IPlace;
import com.winterwell.jgeoplanet.MFloat;

import winterwell.json.JSONArray;
import winterwell.json.JSONException;
import winterwell.json.JSONObject;
import winterwell.jtwitter.TwitterImpl.KRequestType;

/**
 * Twitter's geolocation support. Use {@link Twitter#geo()} to get one of these
 * objects.
 * <p>
 * Conceptually, this is an extension of {@link Twitter}. The methods are here
 * because Twitter was getting crowded.
 * 
 * @see Twitter#setMyLocation(double[])
 * @see Twitter#setSearchLocation(double, double, String)
 * @see Status#getLocation()
 * 
 * @author Daniel Winterstein
 * @testedby {@link Twitter_GeoTest}
 */
public class Twitter_GeoImpl implements interfacesComunes.Twitter_Geo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2915686474613421012L;

	private double accuracy;

	private final TwitterImpl jtwit;

	/**
	 * Use {@link Twitter#geo()} to get one.
	 * 
	 * @param jtwit
	 */
	Twitter_GeoImpl(TwitterImpl jtwit) {
		assert jtwit != null;
		this.jtwit = jtwit;
	}

	public List geoSearch(double latitude, double longitude) {
		throw new RuntimeException();
	}

	public List<Place> geoSearch(String query) {
		// quick-fail if we know we're rate limited??
//		if (jtwit.isRateLimited(KRequestType.NORMAL, 1)) {
//			throw new TwitterExceptionImpl.RateLimit("enhance your calm");
//		}		
		String url = jtwit.TWITTER_URL + "/geo/search.json";
		Map vars = InternalUtils.asMap("query", query);
		if (accuracy != 0) {
			vars.put("accuracy", String.valueOf(accuracy));
		}
		boolean auth = InternalUtils.authoriseIn11(jtwit);
		String json = jtwit.getHttpClient().getPage(url, vars, auth);
		try {
			JSONObject jo = new JSONObject(json);
			JSONObject jo2 = jo.getJSONObject("result");
			JSONArray arr = jo2.getJSONArray("places");
			List places = new ArrayList(arr.length());
			for (int i = 0; i < arr.length(); i++) {
				JSONObject _place = arr.getJSONObject(i);
				// interpret it - maybe pinch code from jGeoPlanet?
				// https://dev.twitter.com/docs/api/1/get/geo/id/%3Aplace_id
				Place place = new PlaceImpl(_place);
				places.add(place);
			}
			return places;
		} catch (JSONException e) {
			throw new TwitterExceptionImpl.Parsing(json, e);
		}
	}

	public Place geoSearchByIP(String ipAddress) {
		throw new RuntimeException();
	}

	/**
	 * @param woeid
	 * @return regions from which you can get trending info
	 * @see Twitter#getTrends(Number)
	 */
	public List<Place> getTrendRegions() {
		String json = jtwit.getHttpClient().getPage(
				jtwit.TWITTER_URL + "/trends/available.json", null, false);
		try {
			JSONArray json2 = new JSONArray(json);
			List<Place> trends = new ArrayList();
			for (int i = 0; i < json2.length(); i++) {
				JSONObject ti = json2.getJSONObject(i);
				Place place = new PlaceImpl(ti);
				trends.add(place);
			}
			return trends;
		} catch (JSONException e) {
			throw new TwitterExceptionImpl.Parsing(json, e);
		}
	}

	public void setAccuracy(double metres) {
		this.accuracy = metres;
	}

	@Override
	public IPlace getPlace(String locationDescription, MFloat confidence) {				
		List<Place> places = geoSearch(locationDescription);
		if (places.size()==0) return null;
		// a unique answer?
		if (places.size()==1) {
			if (confidence!=null) confidence.value = 0.8f;
			return places.get(0);
		}		
		return InternalUtils.prefer(places, IPlace.TYPE_CITY, confidence, 0.8f);
	}

	

}