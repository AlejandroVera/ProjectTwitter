package interfacesComunes;

import java.util.Date;

public interface TwitterEvent {
	
	public int getId();
	
	public Date getCreatedAt(); 
	
	public User getSource();
	
	public User getTarget();
	
	public Object getTargetObject();
	
	public String getType();
	
	public boolean	is(java.lang.String type);
	
	public String toString();
	
}
