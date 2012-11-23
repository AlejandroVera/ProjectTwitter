package interfacesComunes;


import java.io.Serializable;
import java.rmi.Remote;


public interface User extends Serializable,Remote{
	
	boolean equals(java.lang.Object other);
	java.util.Date 	getCreatedAt();
	String getDescription();
	int getFavoritesCount();
	int getFollowersCount();
	int getFriendsCount();
	Long getId();
	String getLang();
	String getLocation();
	String getName();
	Place getPlace();
	java.net.URI getProfileBackgroundImageUrl();
	java.net.URI getProfileImageUrl(); 
	String getScreenName();
	Status getStatus();
	int getStatusesCount();
	java.net.URI getWebsite();
	Boolean isFollowedByYou();
	java.lang.Boolean isFollowingYou();
	void aumentarContador();
}
