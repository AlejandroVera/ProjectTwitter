package interfacesComunes;


import java.io.Serializable;
import java.rmi.Remote;

/** 
 * Define las operaciones que se pueden realizar sobre usuarios
 */
public interface User extends Serializable,Remote{
	
	/**
	 * @param other usuario con el que se compara
	 * @return true si se trata del mismo usuario
	 */
	boolean equals(java.lang.Object other);
	
	/**Obtiene la descripcion del usuario. Esta ha tenido que ser escrita previamente
	 * en el perfil del usuario
	 * @return descripcion del usuario
	 */
	String getDescription();
	
	/**
	 * 
	 * @return cantidad de tweets señalados como favoritos
	 */
	int getFavoritesCount();
	
	/**
	 * 
	 * @return cantidad de seguidores
	 */
	int getFollowersCount();
	
	/**
	 * 
	 * @return cantidad de amigos 
	 */
	int getFriendsCount();
	
	/**
	 * 
	 * @return identificador de usuario
	 */
	Long getId();
	
	/**
	 * @return localización del usuario
	 */
	String getLocation();
	
	/**Obtiene el nombre del usuario. 
	 * Nombre completo, no confundir con screen name. Por ejemplo screen name: "@kmilo" 
	 * name: Camilo Pereira
	 * @return nombre de usuario
	 */
	String getName();
	
	/**
	 * 
	 * @return Objeto place, para geolocalización del usuario
	 */
	Place getPlace();
	
	/**
	 * 
	 * @return ruta donde se encuentra la imagen de fondo del perfil del usuario
	 */
	java.net.URI getProfileBackgroundImageUrl();
	
	/**
	 * 
	 * @return ruta donde se encuentra la imagen de perfil de usuario.
	 */
	java.net.URI getProfileImageUrl();
	
	/**Screen name, no confundir con el nombre completo del usuario.
	 *  Por ejemplo screen name: "@kmilo" 
	 * name: Camilo Pereira 
	 * @return screen name o nick del usuario. 
	 */
	String getScreenName();
	
	/**
	 * 
	 * @return ultimo tweet realizado por el usuario
	 */
	Status getStatus();
	
	/**
	 * 
	 * @return cantidad de tweets del usuario
	 */
	int getStatusesCount();
	

	/**
	 * @return true si el usuario logueado sigue a este usuario
	 */
	Boolean isFollowedByYou();
	
	/**
	 * 
	 * @return true si este usuario sigue al usuario logueado
	 */
	java.lang.Boolean isFollowingYou();
	
	/**
	 * Aumenta en uno la cantidad de tweets realizados por el usuario
	 */
	void aumentarContador();
	
	/**
	 * 
	 * @return true si es un usuario protegido
	 */
	boolean getProtectedUser();
	
	/**
	 * @param b establece el perfil del usuario como protegido o público.
	 * true=perfil protegido false= perfil publico
	 */
	void proteger(boolean b);
	
	/**
	 * Utilidad interna para establecer si el usuario loggueado ha enviado una solicitud
	 * para seguir a este usuario. Solo tiene sentido para usuarios protegidos.
	 * @param b true=solicitud enviada false=sin solicitud enviada pendiente de tratar
	 */
	public void setFollowRequestSent(boolean b);
	
	/**
	 * @return true si el usuario tiene una solicitud pendiente
	 *  por parte del usuario logueado
	 */
	public boolean getFollowRequestSent();
	
	/**
	 * Utilidad interna. Comprueba en la base de datos si el usuario ha recibido
	 * una petición de amistad por parte del usuario logueado
	 */
	public void compruebaFollowRequestSent();
}

