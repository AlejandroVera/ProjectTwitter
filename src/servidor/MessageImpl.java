package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import servidor.db.Conexion;
import interfacesComunes.Message;
import interfacesComunes.Place;
import interfacesComunes.Twitter_Geo;
import servidor.TwitterImpl;
import servidor.TwitterImpl.KEntityType;
import interfacesComunes.Twitter.TweetEntity;
import interfacesComunes.User;

public class MessageImpl implements Message{

	private int id;
	private String text;
	private Conexion con;
	private int inReplyTo;
	private User loggedUser;
	
	MessageImpl (int id, Conexion con, User loggedUser){
		this(id, 0, con, loggedUser);
	}
	
	MessageImpl(int id, int inReplyTo, Conexion con, User loggedUser){
		this.loggedUser=loggedUser;
		ResultSet res = con.query("SELECT texto FROM mensajes WHERE id ="+id + "LIMIT 1");
		this.id=id;
		this.con=con;
		try {
			if (res.next())
				this.text=res.getString(1);
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al obtener el texto");
			e.printStackTrace();
		}
		res = con.query("SELECT inReplyTo FROM mensajes WHERE id ="+id + "LIMIT 1");
		try {
			if (res.next())
				this.inReplyTo=res.getInt(1);
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al obtener el texto");
			e.printStackTrace();
		}
	}
		
	
	public Date getCreatedAt(){
		ResultSet res = con.query("SELECT fecha FROM mensajes WHERE id ="+id + "LIMIT 1");
		try {
			if (res.next()){
				Date date= new Date(res.getInt(1)*1000);
				return date;
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al obtener la fecha");
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	public int getId() {
		return id;
	}

	
	public String getLocation() {
		int id_user=getSender().getId();
		ResultSet res = con.query("SELECT location FROM users WHERE id ="+id_user +"LIMIT 1");
		try {
			if (res.next()){
				String location = res.getString(1);
				return location;
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Error al obtener la location");
			e.printStackTrace();
		}
		
		return null;
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
		return geo.geoSearchByIP("www.google.com");
	}

	
	public String getText() {
		return text;
	}

	
	public List<TweetEntity> getTweetEntities(KEntityType type) {
		
		List<TweetEntity> entities=new ArrayList<interfacesComunes.Twitter.TweetEntity>();
		int inicio;
		Pattern p=null;
		Matcher m=null;
		if(type==KEntityType.urls){
			p=Pattern.compile("((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+");
		}
		else if(type==KEntityType.hashtags){
			p=Pattern.compile("(^|\\s)#[a-zA-Z0-9]+");
		}
		else if(type==KEntityType.user_mentions){
			p=Pattern.compile("(^|\\s)@[a-zA-Z0-9]+");
		}
		m= p.matcher(this.text);
		while(m.find()){
			if(this.text.substring(m.start(),m.end()).charAt(0)==' '){
				inicio=m.start()+1;
			}
			else{
				inicio=m.start();
			}
			entities.add(new TwitterImpl.TweetEntity(this.id, type,inicio,m.end(),this.con));
		}
		return entities;
	}
	
	

	
	public User getUser() {
		ResultSet res = con.query("SELECT id_autor FROM mensajes WHERE id ="+id+"LIMIT 1");
		try{
			if (res.next()){
				int id_autor = res.getInt(1);
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
		ResultSet res = con.query("SELECT id_receptor FROM mensajes WHERE id ="+id+"LIMIT 1");
		try{
			if (res.next()){
				int id_receptor = res.getInt(1);
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
		if (inReplyTo!=0)
			return new MessageImpl(inReplyTo, this.con, this.loggedUser);
		else
			return null;
	}
	
	@Override
	public User getSender() {
		User user = getUser();
		return user;
	}

}

