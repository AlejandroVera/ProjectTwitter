package interfacesComunes;

import java.io.Serializable;
import java.rmi.Remote;


public interface Message extends Twitter.ITweet, Serializable, Remote {
	
    public java.lang.String getDisplayText();

    public boolean equals(java.lang.Object obj);
   
    public User getRecipient();
   
    public User getSender();

    public int hashCode();

}
