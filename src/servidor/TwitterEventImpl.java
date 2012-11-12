package servidor;

import java.sql.SQLException;
import java.util.Date;

import servidor.db.Conexion;

import interfacesComunes.Status;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;




/*Tipos de eventos:
    follow
    favorite, unfavorite
    user_update: Changes to the user's profile*/

public class TwitterEventImpl implements TwitterEvent{

	private int id;
	private User source;
	private User target;
	private Status status;
	private Date createdAt;
	private String type;
	private Conexion con;
	
	public TwitterEventImpl(int id, int id_source, int id_target, int id_status, String type, Conexion con) throws SQLException{
		this.id=id;
		this.con=con;
		this.createdAt=new Date();
		this.type=type;
		source= new UserImpl(id_source, this.con);
		target= new UserImpl(id_target, this.con);
		
		if (id_status>0) //Si el id_status se guarda como 0 es que el Event no afecta a un tweet sino a un User
			status=new StatusImpl(id_status, this.con);
		else status=null;
		
		//Lo anadimos a la base de datos
		this.con.updateQuery("INSERT INTO eventos (id, id_autor, id_destinatario, id_tweet,tipo, fecha)" +
				"VALUES ("+id+","+id_source+","+id_target+","+id_status+","+type+","+createdAt+")");
	}
	
	
	public int getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public User getSource() {
		return source;
	}

	public User getTarget() {
		return target;
	}

	public Object getTargetObject() {
		if (status==null)
			return getTarget();
		else
			return status;
	}

	
	public String getType() {
		return type;
	}

	public boolean is(String type) {
		if (this.type.compareTo(type)==0)
			return true;
		else
			return false;
	}

}
