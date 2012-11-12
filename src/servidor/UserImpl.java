package servidor;

import java.net.URI;
import java.util.Date;

import servidor.db.Conexion;

import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.User;


public class UserImpl implements User{
	
	private static final long serialVersionUID = -4749433293227574768L;

	private User loggedUser;
	
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
	private boolean verified;
	private java.net.URI 	website;
	
	//Constructor 1
	public UserImpl(String name, String location){
		this.name = name;
		this.location=location;
	}
	//Constructor 2
	UserImpl(int id, User loggedUser){
		this.id = id;
		this.loggedUser=loggedUser;
	}
	//Constructor 3
	public UserImpl(String name){
		this.name = name;
	}
			
	public UserImpl(int int1, Conexion con) {
		// TODO Auto-generated constructor stub
	}
	public UserImpl(int int1) {
		// TODO Auto-generated constructor stub
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
		return createdAt;
	}

	public String getDescription() {
		return description;
	}

	public int getFavoritesCount() {
		return favoritesCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
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

	public String getProfileSidebarBorderColor(){
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

	public String getScreenName() {
		return screenName;
	}

	public Status getStatus() {
		return status;
	}

	public int getStatusesCount() {
		return statusesCount;
	}

	public URI getWebsite() {
		return website;
	}

	public boolean isFollowedByYou() {

		return false;
	}

	public Boolean isFollowingYou() {
		
		return null;
	}

	public boolean isNotifications() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isProfileBackgroundTile() {
		return profileBackgroundTile;
	}

	public boolean isProtectedUser() {
		return protectedUser;
	}

	public boolean isVerified() {
		return verified;
	}
	
	public String getLang() {
		return "es";
	}

	public Place getPlace() {
		// TODO Auto-generated method stub
		return null;
	}
}
