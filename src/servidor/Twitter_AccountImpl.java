package servidor;

import interfacesComunes.Conexion;
import interfacesComunes.Twitter;
import interfacesComunes.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import excepcionesComunes.TwitterException;


public class Twitter_AccountImpl implements interfacesComunes.Twitter_Account {
	
	private static final long serialVersionUID = 1062348327090738818L;
	
	Twitter twitter;
	Conexion con;
	User loggedUser;
	
	public Twitter_AccountImpl (Twitter jtwit, Conexion con, User loggedUser){
		twitter=jtwit;
		this.con=con;
		this.loggedUser=loggedUser;
		
	}
	
	//Nivel de acceso del login, lo pongo como login normal
	
	public KAccessLevel getAccesLevel() {
		return KAccessLevel.READ_WRITE_DM; 
		
	}
	
	//Cambia cosas del perfil _ Se necesita implementar el constructor User
	public User setProfile(String name, String url, String location,
			String description) {
		
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(url);
		params.add(location);
		params.add(description);
		params.add(name);
		
		con.updateQuery("UPDATE usuario SET web_link= ?, location = ?, descripcion = ? WHERE name = ? LIMIT=1", params);
		ResultSet res = con.query("SELECT id FROM usuario WHERE name ="+ name);
		try {
			//Si existe un usuario con esos datos, se devuelve un objeto
			if(res.next()){
				
				return new UserImpl(res.getLong(1), this.con, this.loggedUser);
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "No se ha podido actualizar usuario " + name);
		}
		
		return null;
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
