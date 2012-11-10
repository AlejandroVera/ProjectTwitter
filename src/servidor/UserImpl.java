package servidor;

import java.net.URI;
import java.util.Date;

import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.User;




public class UserImpl implements User{
	
	private static final long serialVersionUID = -4749433293227574768L;

	private String name;
	private int id;
	private String location;
	
	//Constructor 1
	public UserImpl(String name, String location){
		this.name = name;
		this.location=location;
	}
	//Constructor 2
	public UserImpl(int id){
		this.id = id;
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
	@Override
	public Date getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getFavoritesCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getFollowersCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getFriendsCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getLang() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Place getPlace() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProfileBackgroundColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public URI getProfileBackgroundImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public URI getProfileImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProfileLinkColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProfileSidebarBorderColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProfileSidebarFillColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProfileTextColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean getProtectedUser() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getScreenName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getStatusesCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public URI getWebsite() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isFollowedByYou() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Boolean isFollowingYou() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isNotifications() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isProfileBackgroundTile() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isProtectedUser() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isVerified() {
		// TODO Auto-generated method stub
		return false;
	}
}
