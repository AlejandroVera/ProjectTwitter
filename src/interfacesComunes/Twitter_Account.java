package interfacesComunes;

import java.io.Serializable;

public interface Twitter_Account extends Serializable{

	static String 	COLOR_BG= "0000"; 
	static String 	COLOR_LINK= "0001"; 
	static String 	COLOR_SIDEBAR_BORDER="0010"; 
	static String 	COLOR_SIDEBAR_FILL="0100"; 
	static String 	COLOR_TEXT="1000"; 
	
	static enum KAccessLevel{
		NONE,					//No puede leer ni escribir tweets publicos
		READ_ONLY,				//Solo puede leer tweets publicos		
		READ_WRITE,				//Puede leer y escribir tweets publicos
		READ_WRITE_DM			//Puede leer y escribir tweets publicos y mensajes privados
	}
		
	public KAccessLevel getAccesLevel();
		
	public User setProfile(String name, String url, String profileImageUrl, String location, String description);
	
	public User setProfileColors(java.util.Map<String,String> colorName2hexCode);
	
	public String toString();

	//public User verifyCredentials();
	
	public User verifyCredentials() throws Exception;
	
	
		
}
