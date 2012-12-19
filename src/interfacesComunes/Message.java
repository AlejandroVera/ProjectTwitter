package interfacesComunes;

import java.io.Serializable;
import java.rmi.Remote;


public interface Message extends Twitter.ITweet, Serializable, Remote {
	
	/**
	 * Devuelve el texto del Mensaje
	 * @return displayText String que es el texto del mensaje	 
	 */
    public java.lang.String getDisplayText();

    /**
     * Equals, compara los id
     * @param obj
     * @return true si tienen el mismo id, false si no lo tienen
     */
    public boolean equals(java.lang.Object obj);
    
    /**
     * Usuario que recibe el mensaje
     * @return User objeto usuario que recibio el mensaje
     */
    public User getRecipient();
   
    /**
     * Usuario que envio el mensaje
     * @return User objeto usuario que envio el mensaje
     */
    public User getSender();
    
    /**
     * Hashcode para serializable
     * @return int hashcode
     */
    public int hashCode();

}
