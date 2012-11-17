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
	private Conexion con;
	private User loggedUser;
	
	public UserImpl(String screenName, Conexion con, User loggedUser){
		this(0,screenName,con,loggedUser);
	}

	public UserImpl(int id,Conexion con, User loggedUser){
		this(id,null,con, loggedUser);
	}
	
	public UserImpl(int id, String screenName,Conexion con, User loggedUser) {
		this.con=con;
		this.loggedUser=loggedUser;
		ResultSet res=null;
		if(screenName==null){
			this.id = id;
			res = con.query("SELECT * FROM usuario WHERE id="+this.id+" LIMIT 1");
		}else{
			res = con.query("SELECT * FROM usuario WHERE screenName="+screenName+" LIMIT 1");
		}
		try {
			if(!res.next()){
				throw new TwitterException("Usuario no existe");
			}
			this.name=res.getString("name");
			this.createdAt=new Date(res.getInt("fecha_registro")*1000);
			this.profileBackgroundImageUrl=new URI(res.getString("profileBackgroundImageUrl"));
			this.profileImageUrl=new URI(res.getString("profileImageUrl"));//The url for the user's Twitter profile picture.
			this.website=new URI(res.getString("web_link"));
			this.description=res.getString("descripcion");
			this.screenName = res.getString("screenName");
			this.location = res.getString("location");
			if(screenName!=null)
				this.id = res.getInt("id");
			this.status=new StatusImpl(res.getInt("id_status"),this.con,this.loggedUser);
			this.favoritesCount=con.query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=con.query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			
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
		Twitter_Geo geo = new Twitter_GeoImpl(this.con);
		return geo.geoSearchByIP("www.google.com");
	}
}
