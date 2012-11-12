package servidor;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import servidor.db.Conexion;

import interfacesComunes.Place;
import interfacesComunes.Status;
import interfacesComunes.User;


public class UserImpl implements User{
	//TODO: notificaciones del usuario
	private static final long serialVersionUID = -4749433293227574768L;

	private User loggedUser;
	private java.util.Date 	createdAt;
	private String 	description; 
	private int favoritesCount;
	private int followersCount;
	private boolean followRequestSent;//True if the authenticated user has requested to follow this user.
	private int friendsCount;	//The number of people this user is following.
	private int id; 
	private String 	location;//The location, as reported by the user.
	private String 	name; //The display name, ejemplo: "Camilo Pereira"
	private boolean notifications;//TODO: pero pero pero esto q eeeeees
	private String 	profileBackgroundColor;
	private java.net.URI profileBackgroundImageUrl;
	private boolean profileBackgroundTile ;
	private java.net.URI profileImageUrl;//The url for the user's Twitter profile picture.
	private String profileLinkColor; 
	private String profileSidebarBorderColor;
	private String profileSidebarFillColor;
	private String profileTextColor; 
	private boolean protectedUser; //true if this user keeps their updates private
	private String screenName; //The login name, ejemplo: "kmilinho"	
	private Status 	status; //The user's current status - *if* returned by Twitter.
	private int statusesCount;
	private boolean verified;
	private java.net.URI 	website;

	//Constructor 1
	public UserImpl(String screeName, String location){
		this.screenName = screeName;
		this.location=location;
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT * FROM usuario WHERE screenName="+screeName);
		
		try {
			this.id=res.getInt("id");
			this.favoritesCount=con.query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=con.query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			this.name=res.getString("name");
			this.status=new StatusImpl(res.getInt("id_status"));
			this.createdAt=res.getTimestamp("fecha_registro");
			this.profileBackgroundColor=res.getString("profileBackgroundColor");
			this.profileBackgroundImageUrl=new URI(res.getString("profileBackgroundImageUrl"));
			this.profileLinkColor=res.getString("profileLinkColor");
			this.profileSidebarBorderColor=res.getString("profileSidebarBorderColor");
			this.profileSidebarFillColor=res.getString("profileSidebarFillColor");
			this.profileTextColor=res.getString("profileTextColor");
			this.website=new URI(res.getString("web_link"));
			this.description=res.getString("descripcion");
		} 
		catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al buscar info en BD");
		}
		catch (URISyntaxException e) {
			ServerCommon.TwitterWarning(e, "Error al crear la URL");
		}
	}
	//Constructor 2
	UserImpl(int id, User loggedUser){
		this.id = id;
		this.loggedUser=loggedUser;
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT * FROM usuario WHERE id="+this.id);
		
		try {
			this.favoritesCount=con.query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=con.query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			this.name=res.getString("name");
			this.status=new StatusImpl(res.getInt("id_status"));
			this.createdAt=res.getTimestamp("fecha_registro");
			this.location=res.getString("Localizacion");
			this.profileBackgroundColor=res.getString("profileBackgroundColor");
			this.profileBackgroundImageUrl=new URI(res.getString("profileBackgroundImageUrl"));
			this.profileLinkColor=res.getString("profileLinkColor");
			this.profileSidebarBorderColor=res.getString("profileSidebarBorderColor");
			this.profileSidebarFillColor=res.getString("profileSidebarFillColor");
			this.profileTextColor=res.getString("profileTextColor");
			this.website=new URI(res.getString("web_link"));
			this.description=res.getString("descripcion");
			this.screenName = res.getString("screenName");
		} 
		catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al buscar info en BD");
		}
		catch (URISyntaxException e) {
			ServerCommon.TwitterWarning(e, "Error al crear la URL");
		}
	}
	
	
	//Constructor 3
	public UserImpl(String name){
		this.name = name;
		
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT * FROM usuario WHERE name="+this.name);
		
		try {
			this.id=res.getInt("id");
			this.favoritesCount=con.query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=con.query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			this.status=new StatusImpl(res.getInt("id_status"));
			this.createdAt=res.getTimestamp("fecha_registro");
			this.location=res.getString("Localizacion");
			this.profileBackgroundColor=res.getString("profileBackgroundColor");
			this.profileBackgroundImageUrl=new URI(res.getString("profileBackgroundImageUrl"));
			this.profileLinkColor=res.getString("profileLinkColor");
			this.profileSidebarBorderColor=res.getString("profileSidebarBorderColor");
			this.profileSidebarFillColor=res.getString("profileSidebarFillColor");
			this.profileTextColor=res.getString("profileTextColor");
			this.website=new URI(res.getString("web_link"));
			this.description=res.getString("descripcion");
			this.screenName = res.getString("screenName");
		} 
		catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al buscar info en BD");
		}
		catch (URISyntaxException e) {
			ServerCommon.TwitterWarning(e, "Error al crear la URL");
		}
	}
	//Constructor 4		
	public UserImpl(int id, Conexion con) {
		this.id = id;
		ResultSet res = con.query("SELECT * FROM usuario WHERE id="+this.id);
		
		try {
			this.favoritesCount=con.query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=con.query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			this.name=res.getString("name");
			this.status=new StatusImpl(res.getInt("id_status"));
			this.createdAt=res.getTimestamp("fecha_registro");
			this.location=res.getString("Localizacion");
			this.profileBackgroundColor=res.getString("profileBackgroundColor");
			this.profileBackgroundImageUrl=new URI(res.getString("profileBackgroundImageUrl"));
			this.profileLinkColor=res.getString("profileLinkColor");
			this.profileSidebarBorderColor=res.getString("profileSidebarBorderColor");
			this.profileSidebarFillColor=res.getString("profileSidebarFillColor");
			this.profileTextColor=res.getString("profileTextColor");
			this.website=new URI(res.getString("web_link"));
			this.description=res.getString("descripcion");
			this.screenName = res.getString("screenName");
		} 
		catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al buscar info en BD");
		}
		catch (URISyntaxException e) {
			ServerCommon.TwitterWarning(e, "Error al crear la URL");
		}
	}
	//Constructor 5
	public UserImpl(int id) {
		this.id = id;
		Conexion con = new Conexion();	
		ResultSet res = con.query("SELECT * FROM usuario WHERE id="+this.id);
		
		try {
			this.favoritesCount=con.query("SELECT id_tweet FROM favoritos WHERE id_usuario="+this.id).getFetchSize();
			this.followersCount=con.query("SELECT id_seguidor FROM seguidores WHERE id_seguido="+this.id).getFetchSize();
			this.friendsCount=con.query("SELECT id_seguido FROM seguidores WHERE id_seguidor="+this.id).getFetchSize();
			this.statusesCount=con.query("SELECT id FROM tweet WHERE autor="+this.id).getFetchSize();
			this.name=res.getString("name");
			this.status=new StatusImpl(res.getInt("id_status"));
			this.createdAt=res.getTimestamp("fecha_registro");
			this.location=res.getString("Localizacion");
			this.profileBackgroundColor=res.getString("profileBackgroundColor");
			this.profileBackgroundImageUrl=new URI(res.getString("profileBackgroundImageUrl"));
			this.profileLinkColor=res.getString("profileLinkColor");
			this.profileSidebarBorderColor=res.getString("profileSidebarBorderColor");
			this.profileSidebarFillColor=res.getString("profileSidebarFillColor");
			this.profileTextColor=res.getString("profileTextColor");
			this.website=new URI(res.getString("web_link"));
			this.description=res.getString("descripcion");
			this.screenName = res.getString("screenName");
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

	public String getProfileBackgroundColor() {
		return profileBackgroundColor;
	}

	public URI getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	public URI getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getProfileLinkColor() {
		return profileLinkColor;
	}

	public String getProfileSidebarBorderColor(){
		return profileSidebarBorderColor;
	}

	public String getProfileSidebarFillColor() {
		return profileSidebarFillColor;
	}

	public String getProfileTextColor() {
		return profileTextColor;
	}

	public boolean getProtectedUser() {
		return protectedUser;
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

	public boolean isNotifications() {
		// TODO pero pero pero esto que eeeeees
		return false;
	}

	public boolean isProfileBackgroundTile() {
		return profileBackgroundTile;
	}

	public boolean isProtectedUser() {
		return protectedUser;
	}

	public boolean isVerified() {
		return verified;
	}

	public String getLang() {
		return "es";
	}

	public Place getPlace() {
		// TODO Esperando la tabla places
		return null;
	}
}
