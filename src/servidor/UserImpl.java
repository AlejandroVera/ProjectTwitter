package servidor;

import java.net.URI;
import java.util.Date;

import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.User;


public class UserImpl implements User{
	
	private static final long serialVersionUID = -4749433293227574768L;

	private java.util.Date 	createdAt;
	private String 	description; 
	private int favoritesCount;
	private int followersCount;
	private boolean followRequestSent;//True if the authenticated user has requested to follow this user.
	private int friendsCount;	//The number of people this user is following.
	private int id; 
	private int listedCount;//The number of public lists a user is listed in.
	private String 	location;//The location, as reported by the user.
	private String 	name; //The display name, ejemplo: "Camilo Pereira"
	private boolean notifications;
	private String 	profileBackgroundColor;
	private java.net.URI profileBackgroundImageUrl;
	private boolean profileBackgroundTile ;
	private java.net.URI profileImageUrl;//The url for the user's Twitter profile picture.
	private String profileLinkColor; 
	private String profileSidebarBorderColor;
	private String profileSidebarFillColor;
	private String profileTextColor; 
	private boolean protectedUser; //true if this user keeps their updates private
	private String screenName; //The login name, ejemplo: "kmilinho"	
	private Status 	status; //The user's current status - *if* returned by Twitter.
	private int statusesCount;
	private java.net.URI 	website;
	
	//Constructor 1
	public UserImpl(String name, String location){
		this.name = name;
		this.location=location;
	}
	//Constructor 2
	public UserImpl(int id){
		this.id = id;
	}
	//Constructor 3
	public UserImpl(String name){
		this.name = name;
	}
			
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
	
	public String getLocation(){
		return location;
	}

	public Date getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFavoritesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFollowersCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFriendsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getLang() {
		// TODO Auto-generated method stub
		return null;
	}

	public Place getPlace() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getProfileBackgroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public URI getProfileBackgroundImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public URI getProfileImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileLinkColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileSidebarBorderColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileSidebarFillColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileTextColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getProtectedUser() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getScreenName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStatusesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public URI getWebsite() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isFollowedByYou() {
		// TODO Auto-generated method stub
		return false;
	}

	public Boolean isFollowingYou() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNotifications() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isProfileBackgroundTile() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isProtectedUser() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isVerified() {
		// TODO Auto-generated method stub
		return false;
	}
}
