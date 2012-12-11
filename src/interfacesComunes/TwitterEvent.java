package interfacesComunes;

import java.io.Serializable;
import java.util.Date;

public interface TwitterEvent extends Serializable{
	

	/**Fecha en la que se creo el evento*/
	public Date getCreatedAt(); 
	
	/**Usuario que creo el evento*/
	public User getSource();
	
	/**Usuario objetivo del evento, el que lo recibe*/
	public User getTarget();
	
	/**Objeto que recibe el evento. Puede ser un tweet o un user*/
	public Object getTargetObject();
	
	/**Tipo de evento. Puede ser:
	 * FOLLOW|
	 * FAVORITE|
	 * UNFAVORITE|
	 * FOLLOW_REQUEST|
	 * USER_UPDATE
	 * @return String del evento
	 */
	public String getType();
	
	/**
	 * Es o no de ese tipo
	 * @param type que es el tipo
	 * @return true si es de ese tipo, false si no lo es
	 */
	public boolean	is(String type);
	
	//public String toString();
	
	/**Los tipos de evento con sus valores
	 * */
	public interface Type{
		
		static String	FAVORITE="1"; 
		static String	FOLLOW="2";
		static String	UNFAVORITE="3";
		static String	USER_UPDATE="4";
		static String	FOLLOW_REQUEST="5";
		static String	UNFOLLOW="6";
		
	}
	
}
