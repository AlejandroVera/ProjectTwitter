package servidor;

import interfacesComunes.Conexion;
import interfacesComunes.Twitter;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import excepcionesComunes.TwitterException;


public class Twitter_AccountImplSuscriptor implements interfacesComunes.Twitter_Account {
	
	private static final long serialVersionUID = 1062348327090738818L;
	
	Twitter twitter;
	Conexion con;
	User loggedUser;
	
	public Twitter_AccountImplSuscriptor (Twitter jtwit, Conexion con, User loggedUser){
		twitter=jtwit;
		this.con=con;
		this.loggedUser=loggedUser;		
	}
	
	//Nivel de acceso del login, lo pongo como login normal
	
	public KAccessLevel getAccesLevel() {
		return KAccessLevel.READ_WRITE_DM; 
		
	}
	
	//Cambia cosas del perfil _ Se necesita implementar el constructor User
	public User setProfile(String name, String url, String profileImageUrl, String location, String description) {
		
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(name);
		params.add(url);
		params.add(profileImageUrl);
		params.add(location);
		params.add(description);
		params.add(loggedUser.getId());
		
		con.updateQuery("UPDATE usuario SET name = ?, web_link= ?, profileImageUrl = ?, location = ?, descripcion = ? WHERE id = ? LIMIT 1", params);

		try {
			TwitterEvent event = new TwitterEventImpl(loggedUser.getId(), TwitterEvent.Type.USER_UPDATE, this.con,loggedUser);
			TwitterInitImplSuscriptor.sendThroughTopic(event, loggedUser.getId());
		} catch (SQLException e) { }
			
		return new UserImpl(this.loggedUser.getId(), this.con, this.loggedUser);
		
	}
		
		
	public User setProfileColors(Map<String, String> colorName2hexCode) {

		return null;
	}

	/*Tiene que haber alguna excepcion para las credenciales, se vera con el metodo
	is valid login*/
	public User verifyCredentials() throws Exception {
		
		if (twitter.isValidLogin())
			return twitter.getSelf();
		else
			throw new TwitterException("Credenciales fallidas");
	}

}
