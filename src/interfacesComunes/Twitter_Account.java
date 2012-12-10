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
		
	/**Devuelve el tipo de acceso del usuario*/
	public KAccessLevel getAccesLevel();
		
	/**Cambia los valores del usuario
	 * @param name : El nombre
	 * @param url: La url asociada al usuario
	 * @param profileImageUrl: La imagen de perfil
	 * @param Location: Cambia la location del usuario
	 * @param Description: La descripcion del usuario
	 * @*/
	public User setProfile(String name, String url, String profileImageUrl, String location, String description);
	
	/**DEPRECATED*/
	public User setProfileColors(java.util.Map<String,String> colorName2hexCode);
	
	
	//public String toString();

	//public User verifyCredentials();
	
	public User verifyCredentials() throws Exception;
	
	
		
}
