package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import servidor.db.ConexionImpl;

import excepcionesComunes.TwitterException;


import interfacesComunes.Conexion;
import interfacesComunes.Twitter_Users;
import interfacesComunes.User;

public class Twitter_UsersImpl implements Twitter_Users {

	private static final long serialVersionUID = -8500527458187084020L;
	
	private User loggedUser;
	private Conexion con;

	public Twitter_UsersImpl(Conexion con,User loggedUser){
		this.loggedUser=loggedUser;
		this.con=con;
	}


	public List<Long> getFollowerIDs(){
		List<Long> seguidores=new ArrayList<Long>();
		Conexion con = new ConexionImpl();	
		ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+loggedUser.getId());
		try {
			while(res.next()){
				seguidores.add(res.getLong(1));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return seguidores;
	}
	public List<Integer> getFollowerIDs(String screenName){
		List<Integer> seguidores=new ArrayList<Integer>();
		Conexion con = new ConexionImpl();	
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
		Conexion con = new ConexionImpl();	
		try {
			int idUser = con.query("SELECT id FROM usuario WHERE screenName="+screenName).getInt("id");

			ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+idUser);

			while(res.next()){
				seguidores.add(new UserImpl(res.getLong(1),con,loggedUser));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return seguidores;
	}

	public List<Integer> getFriendIDs(){
		List<Integer> amigos=new ArrayList<Integer>();
		Conexion con = new ConexionImpl();	
		ResultSet res = con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.loggedUser.getId());
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
		Conexion con = new ConexionImpl();	
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
		Conexion con = new ConexionImpl();	
		try {
			int idUser = con.query("SELECT id FROM usuario WHERE screenName="+screenName).getInt("id");

			ResultSet res = con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+idUser);

			while(res.next()){
				amigos.add(new UserImpl(res.getLong(1),con,loggedUser));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return amigos;

	}

	public User follow(String screenName){
		return follow(new UserImpl(screenName,con,loggedUser));
	}

	public User follow(User user){
		Conexion con = new ConexionImpl();	
		con.query("INSERT INTO seguidores  VALUES ("+this.loggedUser.getId()+", "+user.getId()+")");
		return user;
	}

    public User getUser(long userId){
    	return show(userId);
    }

    public User getUser(String screenName){
    	return show(screenName);
    }

    public boolean isFollower(String userB){
    	User u=new UserImpl(userB,con,loggedUser);
    	return u.isFollowingYou();
    }

    public boolean isFollower(String followerScreenName,String followedScreenName){
    	boolean sol=true;
    	Conexion con=new ConexionImpl();
    	User u1=new UserImpl(followerScreenName,con,loggedUser);
    	User u2=new UserImpl(followedScreenName,con,loggedUser);
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
    	return isFollowing(new UserImpl(userB,con,loggedUser));
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
    	Conexion con = new ConexionImpl();
    	ResultSet res = con.query("SELECT screenName FROM usuario");
    	try {
			while(res.next()){
				if(res.getString(1).matches("*"+search+"*") ){
					sol.add(new UserImpl(res.getString(1),con,loggedUser));
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
    	return sol;
    }

    public User show(Number userId){
    	return new UserImpl(userId.longValue(),con,loggedUser);
    }
 
    public User show(String screenName) throws TwitterException{
    	return new UserImpl(screenName,con,loggedUser);
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
    	return stopFollowing(new UserImpl(username,con,loggedUser));
    }
    
    public User stopFollowing(User user){
    	User sol=null;
    	Conexion con = new ConexionImpl();	
    	ResultSet res=con.query("DELETE FROM seguidores WHERE id_seguidor="+this.loggedUser.getId()+" AND id_seguido="+user.getId());
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
		Conexion con = new ConexionImpl();	
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
