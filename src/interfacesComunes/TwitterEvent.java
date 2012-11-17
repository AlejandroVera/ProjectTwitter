package interfacesComunes;

import java.io.Serializable;
import java.util.Date;

public interface TwitterEvent extends Serializable{
	
	public int getId();
	
	public Date getCreatedAt(); 
	
	public User getSource();
	
	public User getTarget();
	
	public Object getTargetObject();
	
	public byte getType();
	
	public boolean	is(byte type);
	
	//public String toString();
	
	public interface Type{
		
		static byte	FAVORITE=1; 
		static byte	FOLLOW=2;
		static byte	UNFAVORITE=3;
		static byte	USER_UPDATE=4;
		
	}
	
}
