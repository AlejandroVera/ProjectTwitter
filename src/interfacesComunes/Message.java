package interfacesComunes;

import java.io.Serializable;
import java.rmi.Remote;


public interface Message extends Twitter.ITweet, Serializable, Remote {
	
	/**Devuelve el texto del Mensaje*/
    public java.lang.String getDisplayText();

    /**Equals, compara los ids*/
    public boolean equals(java.lang.Object obj);
    
    /**Devuelve el usuario, objeto User que envió el mensaje*/
    public User getRecipient();
   
    /**Usuario User que recibio el mensaje*/
    public User getSender();
    
    /**Hashcode()*/
    public int hashCode();

}
