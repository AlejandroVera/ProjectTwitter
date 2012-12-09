package interfacesComunes;

import java.io.Serializable;
import java.util.Date;

public interface TwitterEvent extends Serializable{
	

	
	public Date getCreatedAt(); 
	
	public User getSource();
	
	public User getTarget();
	
	public Object getTargetObject();
	
	public String getType();
	
	public boolean	is(String type);
	
	//public String toString();
	
	public interface Type{
		
		static String	FAVORITE="1"; 
		static String	FOLLOW="2";
		static String	UNFAVORITE="3";
		static String	USER_UPDATE="4";
		static String	FOLLOW_REQUEST="5";
		
	}
	
}
