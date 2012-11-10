package servidor;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import excepcionesComunes.TwitterException;

import servidor.db.Conexion;

import interfacesComunes.Message;
import interfacesComunes.Place;
import interfacesComunes.Twitter;
import interfacesComunes.Twitter.KEntityType;
import interfacesComunes.Twitter.TweetEntity;
import interfacesComunes.User;

public class MessageImpl implements Message{

	private int id;
	private int	inReplyToMessageId; //-1 si no es respuesta
	private String 	text;
	private Conexion con;
	
	//Constructor para no respuesta
	MessageImpl(int id, String text, Conexion con){
		this.id=id;
		this.inReplyToMessageId=-1;
		this.text=text;
		this.con=con;
	}
	
	MessageImpl(int id, int reply, String text, Conexion con){
		this.id=id;
		this.inReplyToMessageId=reply;
		this.text=text;
		this.con=con;
	}
	
	@Override
	public Date getCreatedAt() throws TwitterException{
		ResultSet res = con.query("SELECT fecha FROM mensajes WHERE id ="+id);
		if (res.next()){
			Date date = res.getDate(id);
		}
		else throw new TwitterException ("Error al obtener la fecha");
		
		return null;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getLocation() {
		int id_user=getSender().getId();
		ResultSet res = con.query("SELECT location FROM users WHERE id ="+id_user);
		if (res.next()){
			String location = res.toString();
		}
		else throw new TwitterException ("Error al obtener la location");
		
		return null;
	}
	

	@Override
	public List<String> getMentions() {
		
		List<String> menciones;
		String mencion;
		
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
	

	/*Lo dejo para Place*/
	public Place getPlace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		return text;
	}

	//No lo veo utilidad, porque pone en la API que ni siquiera tiene soporte con Twitter
	public List<TweetEntity> getTweetEntities(Twitter.KEntityType type) {
		
		return null;
	}

	@Override
	public User getUser() {
		ResultSet res = con.query("SELECT id_autor FROM mensajes WHERE id ="+id);
		if (res.next()){
			int id_autor = Integer.parseInt( res.toString());
			return UserImpl(id_autor);
		}
		else throw new TwitterException ("Error al obtener el autor del mensaje");
		
		return null;
	}

	@Override
	public String getDisplayText() {
		return text;
	}

	@Override
	public User getRecipient() {
		ResultSet res = con.query("SELECT id_receptor FROM mensajes WHERE id ="+id);
		if (res.next()){
			int id_receptor = Integer.parseInt( res.toString());
			return UserImpl(id_receptor);
		}
		else throw new TwitterException ("Error al obtener el autor del mensaje");
		
		return null;
	}

	@Override
	public User getSender() {
		User user = getUser();
		return user;
	}

}
