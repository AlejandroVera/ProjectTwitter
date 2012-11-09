package interfacesComunes;


public interface Message extends Twitter.ITweet{
	
    public java.lang.String getDisplayText();

    public boolean equals(java.lang.Object obj);
   
    public User getRecipient();
   
    public User getSender();

    public int hashCode();

}
