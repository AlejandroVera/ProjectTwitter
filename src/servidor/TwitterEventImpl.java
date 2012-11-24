package servidor;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


import interfacesComunes.Conexion;
import interfacesComunes.Status;
import interfacesComunes.TwitterEvent;
import interfacesComunes.User;




/*Tipos de eventos:
    follow
    favorite, unfavorite
    user_update: Changes to the user's profile*/

public class TwitterEventImpl implements TwitterEvent{

	private static final long serialVersionUID = -6687615946432278756L;
	
	private int id;
	private User source;
	private User target;
	private Status status;
	private Date createdAt;
	private String type;
	private Conexion con;
	private User loggedUser;
	/** Constructor para actualizacion de cuenta*/
	public TwitterEventImpl(Long id_source,String type, Conexion con, User loggedUser) throws SQLException{
		this(id_source,Long.parseLong("0"), null, type, con,loggedUser);
	}
	
	/**Constructor para el follow*/
	public TwitterEventImpl(Long id_source, Long id_target, String type, Conexion con,User loggedUser) throws SQLException{
		this(id_source, id_target, null, type, con,loggedUser);
	}
	
	/**Tocho con todo, necesitamos este para favorite/unfavorite*/
	public TwitterEventImpl(Long id_source, Long id_target,Status status, String type, Conexion con,User loggedUser) throws SQLException{
		
		this.loggedUser=loggedUser;
		BigInteger id_status=BigInteger.valueOf(Long.parseLong("0"));
		this.con=con;
		this.createdAt=new Date();
		this.type=type;
		this.source= new UserImpl(id_source, this.con, this.loggedUser);
		
		if (id_target==0)
			this.target=null;
		else	
			this.target= new UserImpl(id_target, this.con,this.loggedUser);
		
		this.status=status;
		
		if (this.status==null) //Si el id_status se guarda como 0 es que el Event no afecta a un tweet sino a un User
			id_status=BigInteger.valueOf(Long.parseLong("0"));
		else id_status=this.status.getId();
		
		//Lo anadimos a la base de datos
		this.con.updateQuery("INSERT INTO eventos (id_autor, id_destinatario, id_tweet,tipo, fecha)" +
				"VALUES ("+id_source+","+id_target+","+id_status+","+type+","+(createdAt.getTime()/1000)+")");
		ResultSet last_id = this.con.query("SELECT LAST_INSERT_ID()");
		if (last_id.next())
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

	
	public String getType() {
		return type;
	}

	public boolean is(String type) {
		if (this.type==type)
			return true;
		else
			return false;
	}

}
