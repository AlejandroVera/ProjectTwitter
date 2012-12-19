package servidor;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfacesComunes.Conexion;
import interfacesComunes.Message;
import interfacesComunes.Place;
import interfacesComunes.Twitter.KEntityType;
import interfacesComunes.Twitter_Geo;
import interfacesComunes.Twitter.TweetEntity;
import interfacesComunes.User;

public class MessageImpl implements Message{

	private static final long serialVersionUID = 5010896843053361786L;

	private BigInteger id;
	private String text;
	private Conexion con;
	private BigInteger inReplyTo;
	private User loggedUser;
	private Date fecha;

	MessageImpl (BigInteger id, Conexion con, User loggedUser){
		this(id, 0, con, loggedUser);
	}

	MessageImpl(BigInteger id, int inReplyTo, Conexion con, User loggedUser){
		this.loggedUser=loggedUser;
		this.id=id;
		this.con=con;

		ResultSet res = con.query("SELECT * FROM mensajes WHERE id ="+id + " LIMIT 1");
		try {
			if (res.next()){
				this.text=res.getString("texto");
				this.fecha= new Date (res.getInt("fecha")*1000);
				this.inReplyTo=BigInteger.valueOf(res.getLong(1));				
			}

		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al obtener el mensaje");
			e.printStackTrace();
		}



	}


	public Date getCreatedAt(){
		return this.fecha;
	}


	public Number getId() {
		return id;
	}


	public String getLocation() {
		return this.loggedUser.getLocation();
	}



	public List<String> getMentions() {

		List<String> menciones = new ArrayList<String>();
		String mencion = new String();

		if (text.contains("@")){
			for (int i=0; i<text.length();i++){
				if (text.charAt(i) =='@'){
					i++;
					while (text.charAt(i) !=' '){
						mencion=mencion+text.charAt(i);
						i++;
					}
					menciones.add(mencion);
				}
			}
		}
		return menciones;	
	}



	public Place getPlace() {
		Twitter_Geo geo = new Twitter_GeoImpl(this.con);
		return (Place) geo.getPlace(null,null);
	}


	public String getText() {
		return text;
	}

	public User getUser() {
		ResultSet res = con.query("SELECT id_autor FROM mensajes WHERE id ="+id+" LIMIT 1");
		try{
			if (res.next()){
				Long id_autor = res.getLong(1);
				UserImpl user= new UserImpl(id_autor,this.con,this.loggedUser);
				return user;
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al obtener la Autor");
			e.printStackTrace();
		}
		return null;
	}


	public String getDisplayText() {
		return text;
	}

	@Override
	public User getRecipient() {
		ResultSet res = con.query("SELECT id_destinatario FROM mensajes WHERE id ="+id+" LIMIT 1");
		try{
			if (res.next()){
				Long id_receptor = res.getLong(1);
				UserImpl user= new UserImpl(id_receptor,this.con,this.loggedUser);
				return user;
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al obtener el receptor");
			e.printStackTrace();
		}

		return null;
	}

	public Message inReplyTo(){
		if (inReplyTo.equals(new BigInteger("0")))
			return new MessageImpl(inReplyTo, this.con, this.loggedUser);
		else
			return null;
	}

	@Override
	public User getSender() {
		User user = getUser();
		return user;
	}

	@Override
	public List<TweetEntity> getTweetEntities(KEntityType type) {
		return null;
	}
}

