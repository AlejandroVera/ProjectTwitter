package interfacesComunes;

import java.io.Serializable;

/**
 * Clase que contiene los metodos para actualizar los valores de un usario asociados a su cuenta, como la
 * imagen de perfil o su descripcion.
 *
 */
public interface Twitter_Account extends Serializable{

	static String 	COLOR_BG= "0000"; 
	static String 	COLOR_LINK= "0001"; 
	static String 	COLOR_SIDEBAR_BORDER="0010"; 
	static String 	COLOR_SIDEBAR_FILL="0100"; 
	static String 	COLOR_TEXT="1000"; 
	
	/**
	 * 
	 * Los distintos tipos de accesos para un usario
	 *
	 */
	static enum KAccessLevel{
		NONE,					//No puede leer ni escribir tweets publicos
		READ_ONLY,				//Solo puede leer tweets publicos		
		READ_WRITE,				//Puede leer y escribir tweets publicos
		READ_WRITE_DM			//Puede leer y escribir tweets publicos y mensajes privados
	}
		
	/**Devuelve el tipo de acceso del usuario*/
	public KAccessLevel getAccesLevel();
		
	/**Cambia los valores del usuario
	 * @param name El nombre
	 * @param url La url asociada al usuario
	 * @param profileImageUrl La imagen de perfil
	 * @param location Cambia la location del usuario
	 * @param description La descripcion del usuario
	 * @return User Objeto usuario con información actualizada
	 */
	public User setProfile(String name, String url, String profileImageUrl, String location, String description);
	
	public User setProfile(String name,String profileImageUrl, String location, String description, int protectedUser);
	
	/**DEPRECATED: No nos era util, no hemos entrado en detalles de colores de fuente
	 * 
	 */
	public User setProfileColors(java.util.Map<String,String> colorName2hexCode);
	
	
	/**
	 * Comprueba las credenciales del usuario logueado
	 * @return User el usuario logueado
	 * @throws Exception
	 */
	public User verifyCredentials() throws Exception;
	
	
		
}
