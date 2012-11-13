package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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

    public User getUser(long userId){
    	return show(userId);
    }

    public User getUser(String screenName){
    	return show(screenName);
    }

    public boolean isFollower(String userB){
    	User u=new UserImpl("userB");
    	return u.isFollowingYou();
    }

    public boolean isFollower(String followerScreenName,String followedScreenName){
    	boolean sol=true;
    	Conexion con=new Conexion();
    	User u1=new UserImpl("followerScreenName");
    	User u2=new UserImpl("followedScreenName");
		ResultSet res = con.query("SELECT * FROM seguidores WHERE id_seguidor="+u1.getId()+"AND id_seguido"+u2.getId());
			try {
				if(!res.next()){
					sol=false;
				}
			} catch (SQLException e) {
				ServerCommon.TwitterWarning(e, "Error de BD");
			}
    	 return sol;
    }
   

    public boolean isFollowing(java.lang.String userB){
    	return isFollowing(new UserImpl("userB"));
    }

    public boolean isFollowing(User user){
    	return user.isFollowedByYou();
    }
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
    public java.util.List<User> searchUsers(String search){
    	List<User> sol = new ArrayList<User>();
    	Conexion con = new Conexion();
    	ResultSet res = con.query("SELECT screenName FROM usuario");
    	try {
			while(res.next()){
				if(res.getString(1).matches("*"+search+"*") ){
					sol.add(new UserImpl(res.getString(1)));
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
    	return sol;
    }

    public User show(Number userId){
    	return new UserImpl(userId.intValue());
    }
 
    public User show(String screenName) throws TwitterException{
    	return new UserImpl(screenName);
    }
    
    public List<User> showById(java.util.Collection<? extends Number> userIds){
    	List<User> sol= new ArrayList<User>();
    	Iterator<? extends Number> it= userIds.iterator();
    	while(it.hasNext()){
    		sol.add((User)it.next());
    	}
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
