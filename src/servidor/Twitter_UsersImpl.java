package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepcionesComunes.TwitterException;

import servidor.db.Conexion;

import interfacesComunes.Twitter_Users;
import interfacesComunes.User;

public class Twitter_UsersImpl implements Twitter_Users {

	private User user;

	public Twitter_UsersImpl(User user){

		this.user=user;
	}


	public List<Number> getFollowerIDs(){
		List<Number> seguidores=new ArrayList<Number>();
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




	public User follow(String screenName){
		return follow(new UserImpl(screenName));
	}

	public User follow(User user){
		Conexion con = new Conexion();	
		con.query("INSERT INTO seguidores  VALUES ("+this.user.getId()+", "+user.getId()+")");
		return user;
	}

/*

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
*/

    public User show(Number userId){
    	User sol=null;
    	return sol;
    }
 
    public User show(String screenName) throws TwitterException{
    	User sol=null;
    	
    	return sol;
    }
    
    public List<User> showById(java.util.Collection<? extends Number> userIds){
    	List<User> sol= new ArrayList<User>();
    	
    	return sol;
    }


    public User stopFollowing(String username){
    	return stopFollowing(new UserImpl(username));
    }
    
    public User stopFollowing(User user){
    	User sol=null;
    	Conexion con = new Conexion();	
    	ResultSet res=con.query("DELETE FROM seguidores WHERE id_seguidor="+this.user.getId()+" AND id_seguido="+user.getId());
    	try {
			if(res.next()){
				sol=user;
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
    	return sol;
    }



	
    public boolean userExists(String screenName){
    	boolean sol=false;
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT id FROM usuario WHERE screenName="+screenName);
		try {
			if(res.next()){
				sol=true;
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
    	return sol;
    }


}
