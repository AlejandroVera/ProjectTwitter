package interfacesComunes;

public interface Twitter_Account {

	static java.lang.String 	COLOR_BG= "0000"; 
	static java.lang.String 	COLOR_LINK= "0001"; 
	static java.lang.String 	COLOR_SIDEBAR_BORDER="0010"; 
	static java.lang.String 	COLOR_SIDEBAR_FILL="0100"; 
	static java.lang.String 	COLOR_TEXT="1000"; 
	
	static enum KAccessLevel{
		NONE,					//No puede leer ni escribir tweets publicos
		READ_ONLY,				//Solo puede leer tweets publicos		
		READ_WRITE,				//Puede leer y escribir tweets publicos
		READ_WRITE_DM			//Puede leer y escribir tweets publicos y mensajes privados
	}
		
	public KAccessLevel getAccesLevel();
		
	public User setProfile(java.lang.String name,
            java.lang.String url,
            java.lang.String location,
            java.lang.String description);
	
	public User setProfileColors(java.util.Map<java.lang.String,java.lang.String> colorName2hexCode);
	
	public java.lang.String toString();
	
	/*public User verifyCredentials() throws Exception;*/
	
	
		
}
