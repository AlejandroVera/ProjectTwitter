package servidor.db;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import servidor.ServerCommon;

public class Conexion {

	private static final String CONFIG_FILE = "../dbconfig.cnf";
	private static String dbName;
	private static String dbUser;
	private static String dbPassword;
	private static String dbPort;

	private Connection con;
	private Statement est;

	public Conexion() {

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

		Properties connectionProps = new Properties();
		connectionProps.put("user", dbUser);
		connectionProps.put("password", dbPassword);

		try {
			//this.con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/"+dbName+"?useUnicode=true&characterEncoding=utf8", "root", "opelcorsa");
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:" + dbPort + "/"+ dbName , connectionProps);
			this.est = this.con.createStatement();
		} catch (SQLException e) {
			ServerCommon.TwitterError(e, "Error durante la conexión a la base de datos", 3);
		}

	}

	public Conexion(String name, String user, String pass, String port) {

		Properties connectionProps = new Properties();
		connectionProps.put("user", user);
		connectionProps.put("password", pass);

		try {
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/"+ name, connectionProps);
			this.est = this.con.createStatement();
		} catch (SQLException e) {
			ServerCommon.TwitterError("Error durante la conexión a la base de datos", 3);
		}

	}

	/**
	 * Ejecuta una query de consulta (inseguro si no se ha filtrado la query previamente).
	 * @param query Texto de la query a ejecutar (debe ser un SELECT).
	 * @return Un ResultSet si la query se ha ejecutado correctamente; null en caso de error.
	 */
	public ResultSet query(String query) {
		try {
			return est.executeQuery(query);

		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}

	/**
	 * Ejecuta una query de actualización (inseguro si no se ha filtrado la query previamente).
	 * @param query Texto de la query de actualización a ejecutar (UPDATE, INSERT, DELETE...).
	 * @return Un entero con el número de filas afectadas o 0 en caso de que esa consulta no devuelva nada. Null en caso de error.
	 */
	public Integer updateQuery(String query) {
		try {
			return est.executeUpdate(query);
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}
	
	/**
	 * Ejecuta una query de consulta de manera segura (filtrando parámetros).
	 * @param query Texto de la query a ejecutar  conteniendo '?' en donde van los parámetros.
	 * @param params Parámetros de la query (debe haber el mismo número que '?' hay en la query).
	 * @return Un ResultSet si la query se ha ejecutado correctamente; null en caso de error.
	 */
	public ResultSet query(String query, List<Object> params) {
		try {
			PreparedStatement est = con.prepareStatement(query);
			fillPreparedStatementCall(est,  params);
			return est.executeQuery();

		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}

	/**
	 * Ejecuta una query de actualización de manera segura (filtrando parámetros).
	 * @param query Texto de la query a ejecutar  conteniendo '?' en donde van los parámetros.
	 * @param params Parámetros de la query (debe haber el mismo número que '?' hay en la query).
	 * @return Un entero con el número de filas afectadas o 0 en caso de que esa consulta no devuelva nada. Null en caso de error.
	 */
	public Integer updateQuery(String query, List<Object> params) {
		try {
			PreparedStatement est = con.prepareStatement(query);
			fillPreparedStatementCall(est,  params);
			return est.executeUpdate();
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo al ejecutar la query "+ query);
			return null;
		}
	}
	
	private void fillPreparedStatementCall(PreparedStatement pre, List<Object> params) throws SQLException{
		Iterator<Object> it = params.iterator();
		while(it.hasNext()){
			Object param = it.next();
			int x = 0;
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
		Conexion con = new Conexion();
		int val = con.updateQuery("INSERT INTO usuario (screenName, name, email, password) VALUES ('Camilo', 'Camilo', 'c.perei@gmail.com', 'cuba88')");
		System.out.println("Devolvió el valor "+ val);
		ResultSet res = con.query("SELECT name FROM usuario");
		try {
			res.next();
			System.out.println("Se ha encontrado un usuario: " + res.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
