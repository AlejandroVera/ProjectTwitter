package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfacesComunes.AStream;
import interfacesComunes.Conexion;
import interfacesComunes.Status;
import interfacesComunes.Twitter;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;

public class AStreamImpl implements AStream{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1676700965821140844L;
	private Twitter tw;
	private Conexion con;
	
	public AStreamImpl(Twitter tw, Conexion con){
		this.tw=tw;
		this.con=con;
	}
	@Override
	public List<TwitterEvent> getEvents() {
		List <TwitterEvent> eventos = new ArrayList<TwitterEvent>();
		User usuario = tw.getSelf();
		
		ResultSet res= con.query("SELECT id FROM eventos WHERE id_destinatario = "+usuario.getId());
		try {
			while (res.next()){
				TwitterEvent evento=new TwitterEventImpl(res.getInt(1),con,usuario);
				eventos.add(evento);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return eventos;
		
	}

}
