package servidor;

import java.io.Serializable;

import interfacesComunes.User;
import servidor.db.Conexion;

public class LoggedConection implements Serializable{

	private static final long serialVersionUID = 2958604802695422640L;
	
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
