package servidor;

import interfacesComunes.ClienteCallback;
import interfacesComunes.Twitter;
import interfacesComunes.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import excepcionesComunes.TwitterException;

import servidor.db.Conexion;

public class Twitter_AccountImpl implements interfacesComunes.Twitter_Account {
	
	Twitter twitter;
	
	public Twitter_AccountImpl (Twitter jtwit){
		twitter=jtwit;
		
	}
	Conexion con=twitter.getConexion();
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
		
		LinkedList<Object> params2 = new LinkedList<Object>();
		params2.add(name);
		con.updateQuery("UPDATE usuario SET url= ?, location = ?, descripcion = ? WHERE name = ?", params);
		ResultSet res = con.query("SELECT Name FROM usuario WHERE name = ? ", (params2));
		try {
			//Si existe un usuario con esos datos, se devuelve un objeto
			if(res.next()){
				
				return new UserImpl(name);
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "No se ha podido actualizar usuario " + name);
		}
		
		return null;
	}
		
		
	/*No se como vamos a meter los colores en la base de datos, hay que usar
	las constantes de COLOR_XXX y el codigo hexadecimal del color como key
	para lo que es el mapa, pero no se como actualizar esto en el usuario*/
	public User setProfileColors(Map<String, String> colorName2hexCode) {
		
		return null;
	}

	/*Tiene que haber alguna excepcion para las credenciales, se vera con el metodo
	is valid login*/
	public User verifyCredentials() throws Exception {
		
		if (twitter.isValidLogin())
			return twitter.self();
		else
			throw new TwitterException("Credenciales fallidas");
	}

}
