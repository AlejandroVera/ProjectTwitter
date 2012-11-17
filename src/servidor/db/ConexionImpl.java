package servidor.db;

import interfacesComunes.Conexion;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import servidor.ServerCommon;

public class ConexionImpl implements Serializable, Conexion{


	private static final long serialVersionUID = -6834351832065831226L;
	
	private  final String CONFIG_FILE = "../dbconfig.cnf";
	private  String dbName;
	private  String dbUser;
	private  String dbPassword;
	private  String dbPort;

	public ConexionImpl() {

		if (dbName == null || dbUser == null || dbPassword == null) {
			BufferedReader buffer = null;
			try {
				buffer = new BufferedReader(new InputStreamReader(
						new DataInputStream(new FileInputStream(CONFIG_FILE))));

				dbName = buffer.readLine(); // Primera linea del archivo
				dbUser = buffer.readLine(); // Segunda linea del archivo
				dbPassword = buffer.readLine(); // Tercera linea del archivo
				dbPort = buffer.readLine(); // Cuarta linea del archivo
											// (opcional)
			} catch (Exception e) {
				ServerCommon.TwitterError(e, "No se ha podido encontrar el archivo de configuración "
						+ CONFIG_FILE + " en el directorio de trabajo " + System.getProperty("user.dir"), 1);
			}

			if (dbName == null || dbUser == null || dbPassword == null) {
				ServerCommon.TwitterError("La sintaxis del archivo de configuración es incorrecta. Debe contener tres lineas "
						+ "con el nombre de la BD, el usuario y la contraseña. Opcionalmente puede contener una cuerta linea con el número de puerto.", 2);
			} else if (dbPort == null)
				dbPort = "3306";
		}

		testConnectionAndSolve();

	}

	public ConexionImpl(String name, String user, String pass, String port) {

		if(ConnectionContainer.getCon() == null){
			 connect(name, user, pass, port);
		}else{
			try {
				if(ConnectionContainer.getCon().isClosed()) { //Reconectar
					connect(name, user, pass, port);
				}
			} catch (SQLException e) {
				ServerCommon.TwitterError(e, "Error durante la conexión a la base de datos", 3);
				connect(name, user, pass, port); //Reconectar
			}
		}

	}
	
	/**
	 * Método auxiliar para conectar con la BD.
	 * @param name Nombre de la BD con la que conectar.
	 * @param user Usuario con el que acceder.
	 * @param pass Contraseña de ese usuario.
	 * @param port Puerto en el que está corriendo la BD.
	 */
	private void connect(String name, String user, String pass, String port){
		
		ConnectionContainer.setCon(null);
		
		Properties connectionProps = new Properties();
		connectionProps.put("user", user);
		connectionProps.put("password", pass);

		try {
			ConnectionContainer.setCon(DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/"+ name, connectionProps));
		} catch (SQLException e) {
			ServerCommon.TwitterError("Error durante la conexión a la base de datos", 3);
		}		
	}
	
	/**
	 * 	Comprueba si es necesario reconectar con la BD y, en caso de serlo, realiza la reconexión.
	 */
	private void testConnectionAndSolve(){
		if(ConnectionContainer.getCon() == null){
			 connect(dbName, dbUser, dbPassword, dbPort);
		}else{
			try {
				if(ConnectionContainer.getCon().isClosed()) { //Reconectar
					 connect(dbName, dbUser, dbPassword, dbPort);
				}
			} catch (SQLException e) {
				ServerCommon.TwitterError(e, "Error durante la conexión a la base de datos", 3);
				 connect(dbName, dbUser, dbPassword, dbPort); //Reconectar
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see servidor.db.Conexion#query(java.lang.String)
	 */
	@Override
	public ResultSet query(String query) {
		
		testConnectionAndSolve();
		
		try {
			return ConnectionContainer.getCon().createStatement().executeQuery(query);

		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see servidor.db.Conexion#updateQuery(java.lang.String)
	 */
	@Override
	public Integer updateQuery(String query) {
		
		testConnectionAndSolve();
		
		try {
			return ConnectionContainer.getCon().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see servidor.db.Conexion#query(java.lang.String, java.util.List)
	 */
	@Override
	public ResultSet query(String query, List<Object> params) {
		
		testConnectionAndSolve();
		
		try {
			PreparedStatement est = ConnectionContainer.getCon().prepareStatement(query);
			fillPreparedStatementCall(est,  params);
			return est.executeQuery();

		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see servidor.db.Conexion#updateQuery(java.lang.String, java.util.List)
	 */
	@Override
	public Integer updateQuery(String query, List<Object> params) {
		
		testConnectionAndSolve();
		
		try {
			PreparedStatement est = ConnectionContainer.getCon().prepareStatement(query);
			fillPreparedStatementCall(est,  params);
			return est.executeUpdate();
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}
	
	private void fillPreparedStatementCall(PreparedStatement pre, List<Object> params) throws SQLException{
		Iterator<Object> it = params.iterator();
		Object param;
		int x = 1;
		while(it.hasNext()){
			param = it.next();
			if(param instanceof String)
				pre.setString(x++, (String) param);
			else if(param instanceof Integer)
				pre.setInt(x++, (Integer) param);
			else if(param instanceof Short)
				pre.setShort(x++, (Short) param);
			else if(param instanceof Byte)
				pre.setByte(x++, (Byte) param);
			else if(param instanceof Long)
				pre.setLong(x++, (Long) param);
			else if(param instanceof Float)
				pre.setFloat(x++, (Float) param);
			else if(param instanceof Double)
				pre.setDouble(x++, (Double) param);
			else 
				throw new SQLException("Tipo de dato "+param.getClass().getName() + " no soportado en el método fillPreparedStatementCall.");
		}
		
	}
	
	public static void main(String[] args){
		Conexion con = new ConexionImpl();
		int val = con.updateQuery("INSERT INTO usuario (screenName, name, email, password) VALUES ('Alex', 'Alex', 'xafilox@gmail.com', 'cacahuete')");
		System.out.println("Devolvió el valor "+ val);
		ResultSet res = con.query("SELECT name FROM usuario");
		try {
			res.next();
			System.out.println("Se ha encontrado un usuario: " + res.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
