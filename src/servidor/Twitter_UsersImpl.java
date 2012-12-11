package servidor;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import servidor.db.ConexionImpl;

import excepcionesComunes.TwitterException;


import interfacesComunes.Conexion;
import interfacesComunes.TwitterEvent;
import interfacesComunes.TwitterInit;
import interfacesComunes.Twitter_Users;
import interfacesComunes.User;

public class Twitter_UsersImpl implements Twitter_Users {

	private static final long serialVersionUID = -8500527458187084020L;

	private User loggedUser;
	private Conexion con;
	private TwitterInit init;

	public Twitter_UsersImpl(Conexion con,User loggedUser, TwitterInit init){
		this.loggedUser=loggedUser;
		this.con=con;
		this.init=init;
	}


	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getFollowerIDs()
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getFollowerIDs(java.lang.String)
	 */
	@Override
	public List<Long> getFollowerIDs(String screenName){
		List<Long> seguidores=new ArrayList<Long>();
		Conexion con = new ConexionImpl();	
		try {
			ResultSet resu = con.query("SELECT id FROM usuario WHERE screenName='"+screenName+"' LIMIT 1");
			if(resu.next()){
				int idUser = resu.getInt("id");
				ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+idUser);

				while(res.next()){
					seguidores.add((long) res.getInt(1));
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return seguidores;
	}


	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getFollowers(java.lang.String)
	 */
	@Override
	public List<User> getFollowers(String screenName){
		List<User> seguidores=new ArrayList<User>();
		Conexion con = new ConexionImpl();	
		try {
			ResultSet resu = con.query("SELECT id FROM usuario WHERE screenName='"+screenName+"' LIMIT 1");
			if(resu.next()){
				int idUser = resu.getInt("id");

				ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+idUser);

				while(res.next()){
					seguidores.add(new UserImpl(res.getLong(1),con,loggedUser));
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return seguidores;
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getFriendIDs()
	 */
	@Override
	public List<Long> getFriendIDs(){
		List<Long> amigos=new ArrayList<Long>();
		Conexion con = new ConexionImpl();	
		ResultSet res = con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.loggedUser.getId());
		try {
			while(res.next()){
				amigos.add(res.getLong(1));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return amigos;
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getFriendIDs(java.lang.String)
	 */
	@Override
	public List<Long> getFriendIDs(String screenName){
		List<Long> amigos=new ArrayList<Long>();
		Conexion con = new ConexionImpl();	
		try {
			ResultSet re = con.query("SELECT id FROM usuario WHERE screenName='"+screenName+"'");
			if(re.next()){
				int idUser=re.getInt("id");

				ResultSet res = con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+idUser);

				while(res.next()){
					amigos.add((long) res.getInt(1));
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return amigos; //puede ser null, forever alone xD;
	}


	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getFriends(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#follow(java.lang.String)
	 */
	@Override
	public User follow(String screenName){
		return follow(new UserImpl(screenName,con,loggedUser));
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#follow(interfacesComunes.User)
	 */
	@Override
	public User follow(User user){
		TwitterEvent evento;
		User sol;
		String tipoevento;
		if(user.getProtectedUser()){
			sol=null;
			tipoevento=TwitterEvent.Type.FOLLOW_REQUEST;
		}
		else{
			sol= user;
			tipoevento=TwitterEvent.Type.FOLLOW;
			con.updateQuery("INSERT INTO seguidores  VALUES ("+this.loggedUser.getId()+", "+user.getId()+")");
		}
		try{
			
			//Creamos el evento
			evento= new TwitterEventImpl(this.loggedUser.getId(),user.getId(), tipoevento, this.con, this.loggedUser);
			
			//Enviamos el evento al usuario que acabamos de seguir y a nosotros mismos
			this.init.sendThroughCallback(evento, user.getId());
			this.init.sendThroughCallback(evento, this.loggedUser.getId());
			
		}catch(SQLException | RemoteException e){
			ServerCommon.TwitterWarning(e, "No se ha podido crear el evento");
		}
		return sol;
	}

	public void confirmarAmistad(User user/* usuario aceptado*/){
		TwitterEvent evento;

		con.updateQuery("INSERT INTO seguidores  VALUES ("+this.loggedUser.getId()+", "+user.getId()+")");
		try{
			//Creamos el evento
			evento= new TwitterEventImpl(this.loggedUser.getId(),user.getId(), TwitterEvent.Type.FOLLOW, this.con, this.loggedUser);
			
			//Enviamos el evento al usuario que acabamos de seguir y a nosotros mismos
			this.init.sendThroughCallback(evento, user.getId());
			this.init.sendThroughCallback(evento, this.loggedUser.getId());
		}catch(SQLException | RemoteException e){
			ServerCommon.TwitterWarning(e, "No se ha podido crear el evento");
		}
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getUser(long)
	 */
	@Override
	public User getUser(long userId){
		return show(userId);
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String screenName){
		return show(screenName);
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#isFollower(java.lang.String)
	 */
	@Override
	public boolean isFollower(String userB){
		User u=new UserImpl(userB,con,loggedUser);
		return u.isFollowingYou();
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#isFollower(java.lang.String, java.lang.String)
	 */
	@Override
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


	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#isFollowing(java.lang.String)
	 */
	@Override
	public boolean isFollowing(java.lang.String userB){
		return isFollowing(new UserImpl(userB,con,loggedUser));
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#isFollowing(interfacesComunes.User)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#searchUsers(java.lang.String)
	 */
	@Override
	public java.util.List<User> searchUsers(String search){
		if((search==null) || (search=="")){
			return null;
		}
		List<User> sol = new ArrayList<User>();
		Conexion con = new ConexionImpl();
		ResultSet res = con.query("SELECT id FROM usuario WHERE screenName LIKE '%"+search+"%'");
		try {
			while(res.next()){
				sol.add(new UserImpl(new Long(res.getInt(1)),con,loggedUser));
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return sol;
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#show(java.lang.Number)
	 */
	@Override
	public User show(Number userId){
		return new UserImpl(userId.longValue(),con,loggedUser);
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#show(java.lang.String)
	 */
	@Override
	public User show(String screenName) throws TwitterException{
		return new UserImpl(screenName,con,loggedUser);
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#showById(java.util.Collection)
	 */
	@Override
	public List<User> showById(java.util.Collection<? extends Number> userIds){
		List<User> sol= new ArrayList<User>();
		Iterator<? extends Number> it= userIds.iterator();
		while(it.hasNext()){
			sol.add((User)it.next());
		}
		return sol;
	}


	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#stopFollowing(java.lang.String)
	 */
	@Override
	public User stopFollowing(String username){
		return stopFollowing(new UserImpl(username,con,loggedUser));
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#stopFollowing(interfacesComunes.User)
	 */
	@Override
	public User stopFollowing(User user){
		Conexion con = new ConexionImpl();	
		int res=con.updateQuery("DELETE FROM seguidores WHERE id_seguidor="+this.loggedUser.getId()+" AND id_seguido="+user.getId());
		if(res > 0){
			try {
				//Creamos el evento
				TwitterEvent evento = new TwitterEventImpl(this.loggedUser.getId(),user.getId(), TwitterEvent.Type.UNFOLLOW, this.con, this.loggedUser);
				
				//Enviamos el evento al usuario que acabamos de seguir y a nosotros mismos
				this.init.sendThroughCallback(evento, user.getId());
				this.init.sendThroughCallback(evento, this.loggedUser.getId());
				
			} catch (SQLException | RemoteException e) {
				ServerCommon.TwitterWarning(e, "Error de evento en stopFollowing");
			}

			return user;
		}else
			return null;
	}

	/* (non-Javadoc)
	 * @see servidor.Twitter_Users#userExists(java.lang.String)
	 */
	@Override
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
