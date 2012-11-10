package interfacesComunes;


import java.io.Serializable;


public interface User extends Serializable{
	
	boolean equals(java.lang.Object other);
	java.util.Date 	getCreatedAt();
	String getDescription();
	int getFavoritesCount();
	int getFollowersCount();
	int getFriendsCount();
	int getId();
	String getLang();
	String getLocation();
	String getName();
	Place getPlace();
	String getProfileBackgroundColor();
	java.net.URI getProfileBackgroundImageUrl();
	java.net.URI getProfileImageUrl(); 
	java.lang.String getProfileLinkColor();
	java.lang.String getProfileSidebarBorderColor();
	java.lang.String getProfileSidebarFillColor();
	java.lang.String getProfileTextColor();
	boolean 	getProtectedUser();
	String getScreenName();
	Status getStatus();
	int getStatusesCount();
	java.net.URI getWebsite();
	boolean isFollowedByYou();
	java.lang.Boolean isFollowingYou();//TODO: No faltaria un parametro User?
	boolean isNotifications();
	boolean isProfileBackgroundTile(); 
	boolean isProtectedUser();
	boolean isVerified();
	java.lang.String toString();
	
}
