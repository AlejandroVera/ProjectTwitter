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
import interfacesComunes.Twitter_Geo;
import interfacesComunes.User;


public class UserImpl implements User{
	
	private static final long serialVersionUID = -4749433293227574768L;
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
	private LoggedConection loggedConection;
	
	public UserImpl(String screenName, LoggedConection loggedConection){
		this(0,screenName,loggedConection);
	}

	public UserImpl(int id,LoggedConection loggedConection){
		this(id,null,loggedConection);
	}
	
	public UserImpl(int id, String screenName,LoggedConection loggedConection) {
		this.loggedConection=loggedConection;
		ResultSet res=null;
		if(screenName==null){
			this.id = id;
			res = loggedConection.getConexion().query("SELECT * FROM usuario WHERE id="+this.id+"LIMIT 1");
		}else{
			res = loggedConection.getConexion().query("SELECT * FROM usuario WHERE screenName="+screenName+"LIMIT 1");
		}
		try {
			if(!res.next()){
				throw new TwitterException("Usuario no existe");
			}
			this.favoritesCount=loggedConection.getConexion().query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=loggedConection.getConexion().query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=loggedConection.getConexion().query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=loggedConection.getConexion().query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			this.name=res.getString("name");
			this.status=new StatusImpl(res.getInt("id_status"),this.loggedConection);
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
	
	public boolean getFollowRequestSent(){
		return followRequestSent;
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
				if(res.getInt(1)==this.loggedConection.getUsuario().getId()){
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
		ResultSet res = con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.loggedConection.getUsuario().getId());
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
		Twitter_Geo geo = new Twitter_GeoImpl(loggedConection.getConexion());
		return geo.geoSearchByIP("www.google.com");
	}
}
