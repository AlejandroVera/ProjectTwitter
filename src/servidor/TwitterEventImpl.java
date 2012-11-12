package servidor;

import java.sql.ResultSet;
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
	private byte type;
	private Conexion con;
	/** Constructor para actualizacion de cuenta*/
	public TwitterEventImpl(int id_source,byte type, Conexion con) throws SQLException{
		TwitterEventImpl(id_source, 0, null, type, con);
	}
	
	/**Constructor para el follow*/
	public TwitterEventImpl(int id_source, int id_target, byte type, Conexion con) throws SQLException{
		TwitterEventImpl(id_source, id_target, null, type, con);
	}
	
	/**Tocho con todo, necesitamos este para favorite/unfavorite*/
	public TwitterEventImpl(int id_source, int id_target,Status status, byte type, Conexion con) throws SQLException{
		
		int id_status=0;
		this.con=con;
		this.createdAt=new Date();
		this.type=type;
		source= new UserImpl(id_source, this.con);
		
		if (id_target==0)
			target=null;
		else	
			target= new UserImpl(id_target, this.con);
		
		this.status=status;
		
		if (this.status==null) //Si el id_status se guarda como 0 es que el Event no afecta a un tweet sino a un User
			id_status=0;
		else id_status=this.status.getId();
		
		//Lo anadimos a la base de datos
		this.con.updateQuery("INSERT INTO eventos (id_autor, id_destinatario, id_tweet,tipo, fecha)" +
				"VALUES ("+id_source+","+id_target+","+id_status+","+type+","+createdAt+")");
		ResultSet last_id = this.con.query("SELECT LAST_INSERT_ID()");
		this.id=last_id.getInt(1);
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

	
	public byte getType() {
		return type;
	}

	public boolean is(byte type) {
		if (this.type==type)
			return true;
		else
			return false;
	}

}
