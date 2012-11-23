package winterwell.jtwitter;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import winterwell.json.JSONArray;
import winterwell.json.JSONException;
import winterwell.json.JSONObject;

/**
 * A Twitter user. Fields are null if unset.
 * 
 * @author daniel
 */
public final class UserImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Convert from a JSON array into a list of users.
	 * 
	 * @param json
	 * @throws TwitterException
	 */
	static List<User> getUsers(String json) throws TwitterException {
		if (json.trim().equals(""))
			return Collections.emptyList();
		try {
			JSONArray arr = new JSONArray(json);
			return getUsers2(arr);
		} catch (JSONException e) {
			throw new TwitterExceptionImpl.Parsing(json, e);
		}
	}

	static List<User> getUsers2(JSONArray arr) throws JSONException {
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			User u = new UserImpl(obj, null);
			users.add(u);
		}
		return users;
	}

	public final Date createdAt;
	public final String description;
	public final int favoritesCount;

	private final Boolean followedByYou;
	/**
	 * The number of people who follow this user.
	 */
	public int followersCount;
	private final Boolean followingYou;
	/**
	 * True if the authenticated user has requested to follow this user. This
	 * will be false unless the friendship request is pending. False if Twitter
	 * does not say otherwise.
	 */
	public final boolean followRequestSent;

	/**
	 * The number of people this user is following.
	 * <p>
	 * "following count" would be a better name, but historically Twitter calls
	 * this "friends count".
	 */
	public final int friendsCount;
	public final Long id;
	
	String lang;
	
	/**
	 * User's language (2 letter code)
	 * @return e.g. "en"
	 */
	public String getLang() {
		return lang;
	}
	
	/**
	 * The number of public lists a user is listed in. -1 if unknown.
	 */
	public final int listedCount;
	/**
	 * The location, as reported by the user. Can be metaphorical, e.g.
	 * "close to your heart"), or null; never blank. UberTwitter & similar
	 * lat/long references will be normalised using
	 * {@link InternalUtils#latLongLocn}.
	 */
	public final String location;
	/** The display name, e.g. "Daniel Winterstein" */
	public final String name;
	public final boolean notifications;
	private Place place;
	public final String profileBackgroundColor;
	public final URI profileBackgroundImageUrl;
	public final boolean profileBackgroundTile;

	/**
	 * The url for the user's Twitter profile picture.
	 * <p>
	 * Note: we allow this to be edited as a convenience for the User objects
	 * generated by search
	 */
	public URI profileImageUrl;

	public final String profileLinkColor;

	public final String profileSidebarBorderColor;

	public final String profileSidebarFillColor;

	public final String profileTextColor;

	/**
	 * true if this user keeps their updates private
	 */
	public final boolean protectedUser;

	/**
	 * The login name, e.g. "winterstein" This is the only thing used by
	 * equals() and hashcode(). This is always lower-case, as Twitter
	 * screen-names are case insensitive, *unless* you set
	 * {@link Twitter#CASE_SENSITIVE_SCREENNAMES}
	 */
	public final String screenName;

	/**
	 * The user's current status - *if* returned by Twitter. Not all calls
	 * return this, so can be null.
	 */
	public final Status status;

	public final int statusesCount;

	public final String timezone;

	/**
	 * Number of seconds between a user's registered time zone and Greenwich
	 * Mean Time (GMT) - aka Coordinated Universal Time or UTC. Can be positive
	 * or negative.
	 */
	public final double timezoneOffSet;

	public final boolean verified;
	public final URI website;

	/**
	 * Create a User from a json blob
	 * 
	 * @param obj
	 * @param status
	 *            can be null
	 * @throws TwitterException
	 */
	UserImpl(JSONObject obj, Status status) throws TwitterException {
		try {
			id = obj.getLong("id");
			name = InternalUtils.unencode(InternalUtils.jsonGet("name", obj));
			String sn = InternalUtils.jsonGet("screen_name", obj);
			screenName = Twitter.CASE_SENSITIVE_SCREENNAMES ? sn : sn
					.toLowerCase();						
			// location - normalise a bit
			Object _locn = Status.jsonGetLocn(obj);
			location = _locn == null ? null : _locn.toString();
			if (_locn instanceof Place) {
				place = (Place) _locn;
			}
			// language
			lang = InternalUtils.jsonGet("lang", obj);

			description = InternalUtils.unencode(InternalUtils.jsonGet(
					"description", obj));
			String img = InternalUtils.jsonGet("profile_image_url", obj);
			profileImageUrl = img == null ? null : InternalUtils.URI(img);
			String url = InternalUtils.jsonGet("url", obj);
			website = url == null ? null : InternalUtils.URI(url);
			protectedUser = obj.optBoolean("protected");
			followersCount = obj.optInt("followers_count");
			profileBackgroundColor = InternalUtils.jsonGet(
					"profile_background_color", obj);
			profileLinkColor = InternalUtils.jsonGet("profile_link_color", obj);
			profileTextColor = InternalUtils.jsonGet("profile_text_color", obj);
			profileSidebarFillColor = InternalUtils.jsonGet(
					"profile_sidebar_fill_color", obj);
			profileSidebarBorderColor = InternalUtils.jsonGet(
					"profile_sidebar_border_color", obj);
			friendsCount = obj.optInt("friends_count");
			// date
			String c = InternalUtils.jsonGet("created_at", obj);
			createdAt = c == null ? null : InternalUtils.parseDate(c); // null
																		// when
																		// fetching
																		// relationship-info
			favoritesCount = obj.optInt("favourites_count");
			String utcOffSet = InternalUtils.jsonGet("utc_offset", obj);
			timezoneOffSet = utcOffSet == null ? 0 : Double
					.parseDouble(utcOffSet);
			timezone = InternalUtils.jsonGet("time_zone", obj);
			img = InternalUtils.jsonGet("profile_background_image_url", obj);
			profileBackgroundImageUrl = img == null ? null : InternalUtils
					.URI(img);
			profileBackgroundTile = obj.optBoolean("profile_background_tile");
			statusesCount = obj.optInt("statuses_count");
			notifications = obj.optBoolean("notifications");
			verified = obj.optBoolean("verified");
			// relationship info -- can come in 2 formats...
			Object _cons = obj.opt("connections");
			if (_cons instanceof JSONArray) { // from a getRelationshipInfo call
				JSONArray cons = (JSONArray) _cons;
				boolean _following = false, _followedBy = false, _followRequested = false;
				for (int i = 0, n = cons.length(); i < n; i++) {
					String ci = cons.getString(i);
					if ("following".equals(ci)) {
						_following = true;
					} else if ("followed_by".equals(ci)) {
						_followedBy = true;
					} else if ("following_requested".equals(ci)) {
						_followRequested = true;
					}
				}
				followedByYou = _following;
				followingYou = _followedBy;
				followRequestSent = _followRequested;
			} else { // from a normal User call
				followedByYou = InternalUtils.getOptBoolean(obj, "following");
				// Warning: Twitter have stopped sending this in many cases.
				// Unfortunately, null cannot be interpreted as true/false.
				followingYou = InternalUtils.getOptBoolean(obj, "followed_by");
				followRequestSent = obj.optBoolean("follow_request_sent");
			}

			listedCount = obj.optInt("listed_count", -1);
			// status
			if (status == null) {
				JSONObject s = obj.optJSONObject("status");
				this.status = s == null ? null : new StatusImpl(s, this);
			} else {
				this.status = status;
			}
		} catch (JSONException e) {
			throw new TwitterExceptionImpl.Parsing(String.valueOf(obj), e);
		} catch (NullPointerException e) {
			throw new TwitterExceptionImpl(e + " from <" + obj + ">, <" + status
					+ ">\n\t" + e.getStackTrace()[0] + "\n\t"
					+ e.getStackTrace()[1]);
		}
	}

	/**
	 * Create a dummy User object. All fields are set to null. This will be
	 * equals() to an actual User object, so it can be used to query
	 * collections. E.g. <code><pre>
	 * // Test whether jtwit is a friend
	 * twitter.getFriends().contains(new UserImpl("jtwit"));
	 * </pre></code>
	 * 
	 * @param screenName
	 *            This will be converted to lower-case as Twitter screen-names
	 *            are case insensitive (unless
	 *            {@link Twitter#CASE_SENSITIVE_SCREENNAMES} is set)
	 */
	public UserImpl(String screenName) {
		this(screenName, null);
	}

	private UserImpl(String screenName, Long id) {
		this.id = id;
		name = null;
		if (screenName != null && !Twitter.CASE_SENSITIVE_SCREENNAMES) {
			screenName = screenName.toLowerCase();
		}
		this.screenName = screenName;
		status = null;
		location = null;
		description = null;
		profileImageUrl = null;
		website = null;
		protectedUser = false;
		followersCount = 0;
		profileBackgroundColor = null;
		profileLinkColor = null;
		profileTextColor = null;
		profileSidebarFillColor = null;
		profileSidebarBorderColor = null;
		friendsCount = 0;
		createdAt = null;
		favoritesCount = 0;
		timezoneOffSet = -1;
		timezone = null;
		profileBackgroundImageUrl = null;
		profileBackgroundTile = false;
		statusesCount = 0;
		notifications = false;
		verified = false;
		followedByYou = null;
		followingYou = null;
		followRequestSent = false;
		listedCount = -1;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other.getClass() != User.class)
			return false;
		User ou = (User) other;
		// normal case
		if (screenName != null && ou.screenName != null)
			return screenName.equals(ou.screenName);
		// fake user case
		if (id != null && ou.id != null)
			return id == ou.id;
		// can't compare = fail
		return false;
	}

	// /**
	// * A 2nd species of fake user. For internal use only.
	// * WARNING: these users break {@link #hashCode()}'s behaviour!
	// * @param id
	// */
	// User(Long id) {
	// this(null, id);
	// }

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Number of statuses a user has marked as favorite.<br>
	 * Warning: can be zero if Twitter did not supply the info (e.g. User
	 * objects from searches or RSS feeds)
	 * */
	public int getFavoritesCount() {
		return favoritesCount;
	}

	/**
	 * @return Number of followers.<br>
	 *         Warning: can be zero if Twitter did not supply the info (e.g.
	 *         User objects from searches or RSS feeds)
	 */
	public int getFollowersCount() {
		return followersCount;
	}

	/**
	 * @return number of people this user is following.<br>
	 *         Warning: can be zero if Twitter did not supply the info (e.g.
	 *         User objects from searches or RSS feeds)
	 */
	public int getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @return The Twitter id for this post. This is used by some API methods.
	 *         <p>
	 *         Note: this may switch to BigInteger in the future, if Twitter
	 *         change their id numbering scheme. Use Number (which is a
	 *         super-class for both Long and BigInteger) if you wish to
	 *         future-proof your code.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @see #location
	 * @see #getPlace()
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * The display name, e.g. "Daniel Winterstein"
	 * 
	 * @see #getScreenName()
	 * */
	public String getName() {
		return name;
	}

	/**
	 * @return the Place object for this location. 
	 * This provides more details than {@link #location}, but it is often null!
	 */
	public Place getPlace() {
		return place;
	}

	public String getProfileBackgroundColor() {
		return profileBackgroundColor;
	}

	public URI getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	public URI getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getProfileLinkColor() {
		return profileLinkColor;
	}

	public String getProfileSidebarBorderColor() {
		return profileSidebarBorderColor;
	}

	public String getProfileSidebarFillColor() {
		return profileSidebarFillColor;
	}

	public String getProfileTextColor() {
		return profileTextColor;
	}

	public boolean getProtectedUser() {
		return protectedUser;
	}

	/** The login name, e.g. "winterstein". Never null */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * The user's current status - *if* returned by Twitter. Not all calls
	 * return this, so can be null.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return number of status updates posted by this User.<br>
	 *         Warning: can be zero if Twitter did not supply the info (e.g.
	 *         User objects from searches or RSS feeds)
	 */
	public int getStatusesCount() {
		return statusesCount;
	}

	/**
	 * String version of the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * Number of seconds between a user's registered time zone and Greenwich
	 * Mean Time (GMT) - aka Coordinated Universal Time or UTC. Can be positive
	 * or negative.
	 */
	public double getTimezoneOffSet() {
		return timezoneOffSet;
	}

	public URI getWebsite() {
		return website;
	}

	@Override
	public int hashCode() {
		// normal case
		return screenName.hashCode();
	}

	/**
	 * @return true if this is a dummy User object, in which case almost all of
	 *         it's fields will be null - with the exception of screenName and
	 *         possibly {@link #profileImageUrl}. Dummy User objects are
	 *         equals() to full User objects.
	 */
	public boolean isDummyObject() {
		return name == null;
	}

	/**
	 * Are you following this person?
	 * 
	 * @return true if you are following this user. 
	 * null if unset -- though this is quite rare.
	 */
	public Boolean isFollowedByYou() {
		return followedByYou;
	}

	/**
	 * Is this person following you?
	 * 
	 * @return true if this user is following you. null if unset
	 * -- which is common!
	 */
	public Boolean isFollowingYou() {
		return followingYou;
	}

	public boolean isNotifications() {
		return notifications;
	}

	public boolean isProfileBackgroundTile() {
		return profileBackgroundTile;
	}

	/**
	 * true if this user keeps their updates private
	 */
	public boolean isProtectedUser() {
		return protectedUser;
	}

	/**
	 * @return true if the account has been verified by Twitter to really be who
	 *         it claims to be.
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * Returns the User's screenName (i.e. their Twitter login)
	 */
	@Override
	public String toString() {
		return screenName;
	}
}