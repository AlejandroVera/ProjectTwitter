package interfacesComunes;


import java.io.Serializable;
import java.util.List;

import excepcionesComunes.TwitterException;

public interface Twitter_Users extends Serializable{

	public abstract List<Long> getFollowerIDs();

	public abstract List<Long> getFollowerIDs(String screenName);

	public abstract List<User> getFollowers(String screenName);

	public abstract List<Long> getFriendIDs();

	public abstract List<Long> getFriendIDs(String screenName);

	public abstract List<User> getFriends(String screenName);

	public abstract User follow(String screenName);

	public abstract User follow(User user);

	public abstract User getUser(long userId);

	public abstract User getUser(String screenName);

	public abstract boolean isFollower(String userB);

	public abstract boolean isFollower(String followerScreenName,
			String followedScreenName);

	public abstract boolean isFollowing(java.lang.String userB);

	public abstract boolean isFollowing(User user);
	public void confirmarAmistad(User u/* usuario aceptado*/);

	/*
	leaveNotifications
	
	public User leaveNotifications(java.lang.String screenName)
	
	Switches off notifications for updates from the specified user who must already be a friend.
	
	Parameters:
	screenName - Stop getting notifications from this user, who must already be one of your friends.
	Returns:
	the specified user
	
	notify
	
	public User notify(java.lang.String username)
	
	Enables notifications for updates from the specified user who must already be a friend.
	
	Parameters:
	username - Get notifications from this user, who must already be one of your friends.
	Returns:
	the specified user
	
	 */

	//Busqueda por screenName, devuelve una lista con usuarios cuyos screenNames contengan el string pasado
	public abstract java.util.List<User> searchUsers(String search);

	public abstract User show(Number userId);

	public abstract User show(String screenName) throws TwitterException;

	public abstract List<User> showById(
			java.util.Collection<? extends Number> userIds);

	public abstract User stopFollowing(String username);

	public abstract User stopFollowing(User user);

	public abstract boolean userExists(String screenName);

}