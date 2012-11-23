package servidor;

import interfacesComunes.AStream.IListen;
import interfacesComunes.Conexion;
import interfacesComunes.Twitter;
import interfacesComunes.TwitterInit;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import servidor.db.ConexionImpl;



public class TwitterInitImpl extends UnicastRemoteObject implements TwitterInit {

	
	private static final long serialVersionUID = -4305345588180033587L;
	private Conexion con;
	private HashMap<Long, LinkedList<IListen>> callbackArray;
	
	public TwitterInitImpl(LinkedList<IListen> clientes) throws RemoteException {
		super();
		this.con = new ConexionImpl();
		this.callbackArray = new HashMap<Long, LinkedList<IListen>>();
	}
	
	@Override
	public Twitter login(String screenName, String pass, IListen cliente) throws RemoteException {

		//Hacemos el hash de la contraseña
		try {
	        pass = sha1String(pass);
		} catch (Exception e) {
			ServerCommon.TwitterWarning("Imposible obtener hash MD5 de la contraseña.");
			return null;
		}
		
		//Preparamos los parámetros a pasarle a la query
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(screenName);
		params.add(pass);
		
		ResultSet res = con.query("SELECT id FROM usuario WHERE screenName = ? AND password = ?", params);
		try {
			//Si existe un usuario con esos datos, se devuelve un objeto
			if(res.next()){
				Long accountId = res.getLong(1);
				if(this.callbackArray.get(accountId) == null)
					this.callbackArray.put(accountId, new LinkedList<IListen>());
				this.callbackArray.get(accountId).add(cliente);
				return new TwitterImpl(accountId, this.callbackArray);
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "No se ha podido autenticar al usuario " + screenName);
		}
		
		return null;
	}

	@Override
	public int register(String screenName, String pass, String email)
			throws RemoteException {
		
		//Hacemos el hash de la contraseña
		try {
			pass = sha1String(pass);
		} catch (Exception e) {
			ServerCommon.TwitterWarning("Imposible obtener hash MD5 de la contraseña.");
			return TwitterInit.REG_WRONG_UNKNOWN;
		}
		
		if(screenName.isEmpty())
			return TwitterInit.REG_WRONG_USER;
		else if(email.isEmpty())
			return TwitterInit.REG_WRONG_EMAIL;
			
		//Preparamos los parámetros a pasarle a la query
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(screenName);
		params.add(pass);
		
		ResultSet res = con.query("SELECT id FROM usuario WHERE screenName = ? OR email = ? LIMIT 1", params);
		
		try {
			//Si existe ya un usuario con esos datos, no se puede registrar
			if(res.next()){
				if(res.getString("name").equals(screenName))
					return TwitterInit.REG_WRONG_USER; // Ya existe un usuario con ese nombre
				else
					return TwitterInit.REG_WRONG_EMAIL; //Ya existe un usuario con ese email
			}else{
				params.clear();
				params.add(screenName);
				params.add(email);
				params.add(pass);
				params.add((int)(new Date().getTime()/1000));
				int ins = con.updateQuery("INSERT INTO usuario (screenName, email, password, fecha_registro) VALUES (?, ?, ?, ?)", params);
				if(ins > 0)
					return TwitterInit.REG_OK; //Usuario registrado correctamente
				else
					return TwitterInit.REG_WRONG_UNKNOWN; //Error desconocido
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "No se ha podido autenticar al usuario " + screenName);
			return TwitterInit.REG_WRONG_UNKNOWN; //Error desconocido
		}
		
	}
	
		@Override
	public void logout(Long userId, IListen client) throws RemoteException {
			LinkedList<IListen> userRow = this.callbackArray.get(userId);
			if(userRow != null){
				userRow.remove(client);
				if(userRow.size() == 0) //Liberamos memoria
					this.callbackArray.remove(userRow);
			}
	}
		
	/**
	 * Calcula el hash de un String dado.
	 * @param str Cadena de la cual calcular el hash SHA-1
	 * @return Cadena de 40 caracteres con el hash.
	 * @throws NoSuchAlgorithmException
	 */
	private String sha1String(String str) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
 
        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb.toString();
	}



}
