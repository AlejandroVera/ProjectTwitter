package servidor;

import interfacesComunes.User;
import servidor.db.Conexion;

public class LoggedConection {

	private Conexion con;
	private User usuario;
	
	public LoggedConection(User usuario, Conexion con){
		this.con=con;
		this.usuario=usuario;
	}
	public Conexion getConexion(){
		return con;
	}
	public User getUsuario(){
		return usuario;
	}
}
