package servidor;

import interfacesComunes.ClienteCallback;
import interfacesComunes.TwitterInit;
import interfacesComunes.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import servidor.db.Conexion;


public class TwitterInitImpl extends UnicastRemoteObject implements
		TwitterInit {

	
	private static final long serialVersionUID = -4305345588180033587L;
	LinkedList<ClienteCallback> clientes;
	Conexion con;
	
	public TwitterInitImpl(LinkedList<ClienteCallback> clientes) throws RemoteException {
		super();
		this.clientes = clientes;
		this.con = new Conexion();
	}
	
	@Override
	public User login(String user, String pass, ClienteCallback cliente) throws RemoteException {

		//Hacemos el hash de la contraseña
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			pass = md.digest(pass.getBytes("UTF-8")).toString();
		} catch (Exception e) {
			ServerCommon.TwitterWarning("Imposible obtener hash MD5 de la contraseña.");
			return null;
		}
		
		
		System.out.println("La contraseña en MD5: " + pass);
		
		//Preparamos los parámetros a pasarle a la query
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(user);
		params.add(pass);
		
		ResultSet res = con.query("SELECT FROM usuario WHERE name = ? AND password = ? LIMIT 1", params);
		try {
			//Si existe un usuario con esos datos, se devuelve un objeto
			if(res.next()){
				this.clientes.add(cliente);
				return new UserImpl(user);
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "No se ha podido autenticar al usuario " + user);
		}
		
		return null;
	}

	@Override
	public int register(String user, String pass, String email)
			throws RemoteException {
		
		//Preparamos los parámetros a pasarle a la query
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(user);
		params.add(pass);
		
		ResultSet res = con.query("SELECT FROM usuario WHERE name = ? OR email = ? LIMIT 1", params);
		
		try {
			//Si existe ya un usuario con esos datos, no se puede registrar
			if(res.next()){
				if(res.getString("name").equals(user))
					return 1; // Ya existe un usuario con ese nombre
				else
					return 2; //Ya existe un usuario con ese email
			}else{
				params.clear();
				params.add(user);
				params.add(email);
				params.add(pass);
				int ins = con.updateQuery("INSERT INTO usuario (screenName, email, password) VALUES (?, ?, ?)", params);
				if(ins > 1)
					return 0; //Usuario registrado correctamente
				else
					return -1; //Error desconocido
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "No se ha podido autenticar al usuario " + user);
			return -1; //Error desconocido
		}
		
	}

}
