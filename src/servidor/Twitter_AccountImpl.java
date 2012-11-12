package servidor;

import interfacesComunes.Twitter;
import interfacesComunes.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import excepcionesComunes.TwitterException;

import servidor.db.Conexion;

public class Twitter_AccountImpl implements interfacesComunes.Twitter_Account {
	
	Twitter twitter;
	Conexion con;
	
	public Twitter_AccountImpl (Twitter jtwit, Conexion con){
		twitter=jtwit;
		this.con=con;
		
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
		
		con.updateQuery("UPDATE usuario SET url= ?, location = ?, descripcion = ? WHERE name = ?", params);
		ResultSet res = con.query("SELECT id FROM usuario WHERE name ="+ name);
		try {
			//Si existe un usuario con esos datos, se devuelve un objeto
			if(res.next()){
				
				return new UserImpl(res.getInt(1), this.con);
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
			return twitter.getSelf();
		else
			throw new TwitterException("Credenciales fallidas");
	}

}
