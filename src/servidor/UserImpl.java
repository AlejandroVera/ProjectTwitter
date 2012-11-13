package servidor;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import excepcionesComunes.TwitterException;

import servidor.db.Conexion;

import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.User;


public class UserImpl implements User{
	//TODO: notificaciones del usuario
	private static final long serialVersionUID = -4749433293227574768L;

	private User loggedUser;
	private int id; 
	private java.util.Date 	createdAt;
	private String 	description; 
	private int favoritesCount;
	private int followersCount;
	private boolean followRequestSent;//True if the authenticated user has requested to follow this user.
	private int friendsCount;	//The number of people this user is following.
	private String 	location;//The location, as reported by the user.
	private String 	name; //The display name, ejemplo: "Camilo Pereira"
	private java.net.URI profileBackgroundImageUrl;
	private java.net.URI profileImageUrl;//The url for the user's Twitter profile picture.
	private String screenName; //The login name, ejemplo: "kmilinho"	
	private Status 	status; //The user's current status - *if* returned by Twitter.
	private int statusesCount;
	private java.net.URI 	website;
	private Conexion con;

	public UserImpl(String screenName, User loggedUser, Conexion con){
		this(0,screenName,loggedUser,con);
	}

	public UserImpl(int id, String screenName,User loggedUser, Conexion con) {
		this.loggedUser=loggedUser;
		this.con=con;
		ResultSet res=null;
		if(screenName==null){
			this.id = id;
			res = con.query("SELECT * FROM usuario WHERE id="+this.id);
		}else{
			res = con.query("SELECT * FROM usuario WHERE screenName="+screenName);
		}
		try {
			if(!res.next()){
				throw new TwitterException("Usuario no existe");
			}
			this.favoritesCount=con.query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=con.query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			this.name=res.getString("name");
			this.status=new StatusImpl(res.getInt("id_status"),this.con);
			this.createdAt=res.getTimestamp("fecha_registro");
			this.profileBackgroundImageUrl=new URI(res.getString("profileBackgroundImageUrl"));
			this.profileImageUrl=new URI(res.getString("profileImageUrl"));//The url for the user's Twitter profile picture.
			this.website=new URI(res.getString("web_link"));
			this.description=res.getString("descripcion");
			this.screenName = res.getString("screenName");
			this.location = res.getString("location");
			if(screenName!=null){
					this.id = res.getInt("id");
			}
		} 
		catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al buscar info en BD");
		}
		catch (URISyntaxException e) {
			ServerCommon.TwitterWarning(e, "Error al crear la URL");
		}
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
	
	public URI getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	public URI getProfileImageUrl() {
		return profileImageUrl;
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
		boolean sol=false;
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id);
		try {
			while(res.next()){
				if(res.getInt(1)==this.loggedUser.getId()){
					sol=true;
					break;
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return sol;	
	}

	public Boolean isFollowingYou() {
		boolean sol=false;
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.loggedUser.getId());
		try {
			while(res.next()){
				if(res.getInt(1)==this.id){
					sol=true;
					break;
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error de BD");
		}
		return sol;
	}
	
	public String getLang() {
		return "es";
	}

	public Place getPlace() {
		// TODO pero pero pero esto que eeees
		return null;
	}
}
