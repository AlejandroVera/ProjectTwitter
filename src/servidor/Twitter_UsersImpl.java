package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import servidor.db.Conexion;

import interfacesComunes.Twitter_Users;
import interfacesComunes.User;

public class Twitter_UsersImpl implements Twitter_Users {

	private User user;

	public Twitter_UsersImpl(User user){

		this.user=user;
	}


	public List<Integer> getFollowerIDs(){
		List<Integer> seguidores=new ArrayList<Integer>();
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+user.getId());
		try {
			while(res.next()){
				seguidores.add(res.getInt(1));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return seguidores;
	}
	public List<Integer> getFollowerIDs(String screenName){
		List<Integer> seguidores=new ArrayList<Integer>();
		Conexion con = new Conexion();	
		try {
			int idUser = con.query("SELECT id FROM usuario WHERE screenName="+screenName).getInt("id");

			ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+idUser);

			while(res.next()){
				seguidores.add(res.getInt(1));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return seguidores;
	}


	public List<User> getFollowers(String screenName){
		List<User> seguidores=new ArrayList<User>();
		Conexion con = new Conexion();	
		try {
			int idUser = con.query("SELECT id FROM usuario WHERE screenName="+screenName).getInt("id");

			ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+idUser);

			while(res.next()){
				seguidores.add(new UserImpl(res.getInt(1)));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return seguidores;
	}

	public List<Integer> getFriendIDs(){
		List<Integer> amigos=new ArrayList<Integer>();
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.user.getId());
		try {
			while(res.next()){
				amigos.add(res.getInt(1));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return amigos;
	}

	public List<Integer> getFriendIDs(String screenName){
		List<Integer> amigos=new ArrayList<Integer>();
		Conexion con = new Conexion();	
		try {
			int idUser = con.query("SELECT id FROM usuario WHERE screenName="+screenName).getInt("id");

			ResultSet res = con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+idUser);

			while(res.next()){
				amigos.add(res.getInt(1));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return amigos;
	}


	public List<User> getFriends(String screenName){
		List<User> amigos=new ArrayList<User>();
		Conexion con = new Conexion();	
		try {
			int idUser = con.query("SELECT id FROM usuario WHERE screenName="+screenName).getInt("id");

			ResultSet res = con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+idUser);

			while(res.next()){
				amigos.add(new UserImpl(res.getInt(1)));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return amigos;

	}


/*
follow

	public User follow(java.lang.String username)


		Start following a user.

		Parameters:
			username - Required. The ID or screen name of the user to befriend.
			Returns:
			The befriended user, or null if (a) they were already being followed, or (b) they protect their tweets & you already requested to follow them.
			Throws:
		TwitterException - if the user does not exist or has been suspended.
			See Also:
	stopFollowing(String)

follow

public User follow(User user)

Convenience for follow(String)

	Parameters:
		user - 
	Returns:
fresh user object, or null if (a) they were already being followed, or (b) they protect their tweets & you already requested to follow them.

getBlockedIds

public java.util.List<java.lang.Number> getBlockedIds()

	Returns:
an array of numeric user ids the authenticating user is blocking. Use showById(Collection) if you want to convert thse into User objects.



public java.util.List<User> getRelationshipInfo(java.util.List<java.lang.String> screenNames)

Bulk-fetch relationship info by screen-name.

Parameters:
screenNames - Can be empty
Returns:
User objects which are mostly blank, but do have User.isFollowingYou() and User.isFollowedByYou() set (plus name, screenname and id).
See Also:
getRelationshipInfoById(List)

getRelationshipInfoById

																																	public java.util.List<User> getRelationshipInfoById(java.util.List<? extends java.lang.Number> userIDs)

																																	Bulk-fetch relationship info by user-id.

																																	Parameters:
																																		userIDs - Can be empty
																																		Returns:
																																			User objects which are mostly blank, but which have User.isFollowingYou() and User.isFollowedByYou() set (plus name, screenname and id).
																																			See Also:
																																				getRelationshipInfo(List)

																																				getUser

																																				public User getUser(long userId)

																																			Synonym for #show(long). show is the Twitter API name, getUser feels more Java-like.

																																			Parameters:
																																				userId - The user-id of a user.
																																				Returns:
																																					the user info
																																					See Also:
																																						getUser(String)

																																						getUser

																																						public User getUser(java.lang.String screenName)

																																			Synonym for show(String). show is the Twitter API name, getUser feels more Java-like.

																																			Parameters:
																																				screenName - The screen name of a user.
																																				Returns:
																																					the user info

																																					isBlocked

																																					public boolean isBlocked(java.lang.Long userId)

																																			isBlocked

																																			public boolean isBlocked(java.lang.String screenName)

																																			isFollower

																																			public boolean isFollower(java.lang.String userB)

																																			Is the authenticating user followed by userB?

																																					Parameters:
																																						userB - The screen name of a Twitter user.
																																						Returns:
																																							Whether or not the user is followed by userB.

																																							isFollower

																																							public boolean isFollower(java.lang.String followerScreenName,
																																									java.lang.String followedScreenName)

																																			Returns:
																																				true if followerScreenName is following followedScreenName
																																				Throws:
																																					TwitterException.E403 - if one of the users has protected their updates and you don't have access. This can be counter-intuitive (and annoying) at times! Also throws E403 if one of the users has been suspended (we use the TwitterException.SuspendedUser exception sub-class for this).
																																					TwitterException.E404 - if one of the users does not exist

																																					isFollowing

																																					public boolean isFollowing(java.lang.String userB)

																																							Does the authenticating user follow userB?

																																									Parameters:
																																										userB - The screen name of a Twitter user.
																																										Returns:
																																											Whether or not the user follows userB.

																																											isFollowing

																																											public boolean isFollowing(User user)

																																							Convenience for isFollowing(String)

																																							Parameters:
																																								user - 

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

																																									reportSpammer

																																									public User reportSpammer(java.lang.String screenName)

																																							searchUsers

																																							public java.util.List<User> searchUsers(java.lang.String searchTerm)

																																							Warning: there is a bug within twitter.com which means that location-based searches are treated as OR. E.g. "John near:Scotland" will happily return "Andrew from Aberdeen" :(

																																									Unlike tweet search, this method does not support any operators. Only the first 1000 matches are available.

																																									Does not do paging-to-max-results. But does support using #setPageNumber(Integer), and #setMaxResults(int) for less than the standard 20.

																																									Parameters:
																																										searchTerm - 
																																										Returns:

																																											show

																																											public java.util.List<User> show(java.util.Collection<java.lang.String> screenNames)

																																											Lookup user info. This is done in batches of 100. Users can look up at most 1000 users in an hour.

																																											Parameters:
																																												screenNames - Can be empty (in which case we avoid wasting an API call). Bogus names & deleted users will be quietly filtered out.
																																												Returns:
																																													user objects for screenNames. Warning 1: This may be less than the full set if Twitter returns an error part-way through (e.g. you hit your rate limit). Warning 2: the ordering may be different from the screenNames parameter
																																													See Also:
																																														#showById(List)

																																														show

																																														public User show(java.lang.Number userId)

																																												Returns information of a given user, specified by user-id.

																																												Parameters:
																																													userId - The user-id of a user.
																																													Throws:
																																														exception - if the user does not exist - or has been terminated (as happens to spam bots).

																																														show

																																														public User show(java.lang.String screenName)
																																																throws TwitterException,
																																																TwitterException.SuspendedUser

																																																Returns information of a given user, specified by screen name.

																																																Parameters:
																																																	screenName - The screen name of a user.
																																																	Throws:
																																																		exception - if the user does not exist
																																																		TwitterException.SuspendedUser - if the user has been terminated (as happens to spam bots).
																																																		TwitterException
																																																		See Also:
																																																			#show(long)

																																																			showById

																																																			public java.util.List<User> showById(java.util.Collection<? extends java.lang.Number> userIds)

																																																			Lookup user info. Same as #show(List), but works with Twitter user-ID numbers. Done in batches of 100, limited to 1000 an hour.

																																																			Parameters:
																																																				userIds - . Can be empty (in which case we avoid making a wasted API call).

																																																				stopFollowing

																																																				public User stopFollowing(java.lang.String username)

																																																		Destroy: Discontinues friendship with the user specified in the ID parameter as the authenticating user.

																																																		Parameters:
																																																			username - The screen name of the user with whom to discontinue friendship.
																																																			Returns:
																																																				the un-friended user (if they were a friend), or null if the method fails because the specified user was not a friend.

																																																				stopFollowing

																																																				public User stopFollowing(User user)

																																																				Convenience for stopFollowing(String)

																																																				Parameters:
																																																					user - 
																																																					Returns:

																																																						unblock

																																																						public User unblock(java.lang.String screenName)

																																																				blocks/destroy: Un-blocks screenName for the authenticating user. Returns the un-blocked user when successful. If relationships existed before the block was instated, they will not be restored.

																																																				Parameters:
																																																					screenName - 
																																																					Returns:
																																																						the now un-blocked User
																																																						See Also:
																																																							block(String)

																																																							userExists

																																																							public boolean userExists(java.lang.String screenName)

																																																						Does a user with the specified name or id exist?

																																																								Parameters:
																																																									screenName - The screen name or user id of the suspected user.
																																																									Returns:
																																																										False if the user doesn't exist or has been suspended, true otherwise.

	 */
}
