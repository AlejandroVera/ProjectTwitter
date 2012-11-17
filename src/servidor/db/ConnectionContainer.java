package servidor.db;

import java.sql.Connection;

public class ConnectionContainer {
	
	static private Connection con;

	static protected Connection getCon() {
		return con;
	}

	static protected void setCon(Connection con) {
		ConnectionContainer.con = con;
	}
	

}
