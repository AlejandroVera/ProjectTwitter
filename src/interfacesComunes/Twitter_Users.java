package interfacesComunes;


import java.io.Serializable;
import java.util.List;

import excepcionesComunes.TwitterException;
/**
 *Clase usada para las operaciones con usuarios
 *
 */
public interface Twitter_Users extends Serializable{

	/**
	 * @return Lista con los id de los seguidores del usuario loggueado
	 */
	public abstract List<Long> getFollowerIDs();

	/**
	 * @param screenName del usuario para el que se quiere obtener la lista de seguidores
	 * @return lista de indentificadores de los seguidores del usuario indicado.
	 */
	public abstract List<Long> getFollowerIDs(String screenName);
	
	/**
	 * @param screenName del usuario para el que se quiere obtener la lista de seguidores
	 * @return lista usuarios seguidores del usuario indicado.
	 */
	public abstract List<User> getFollowers(String screenName);

	/**
	 * @return lista de indentificadores de los amigos del usuario logueado
	 */
	public abstract List<Long> getFriendIDs();

	/**
	 * @param screenName screen name del usuario para el que se quiere obtener la lista de amigos
	 * @return lista de indentificadores de los amigos del usuario indicado
	 */
	public abstract List<Long> getFriendIDs(String screenName);

	/**
	 * @param screenName screen name del usuario para el que se quiere obtener la lista de amigos
	 * @return lista de amigos del usuario indicado
	 */
	public abstract List<User> getFriends(String screenName);

	/**
	 * @param screenName del usuario al que se desea seguir
	 * @return Usuario seguido
	 */
	public abstract User follow(String screenName);

	/**
	 * @param usuario al que se desea seguir
	 * @return Usuario seguido
	 */
	public abstract User follow(User user);
	
	/**Se obtiene el usuario identificado por userId
	 * @param userId id del usuario que se quiere obtener
	 * @return objeto usuario con identificador userId
	 */
	public abstract User getUser(long userId);

	/**Se obtiene el usuario identificado por screenName
	 * @param screenName screen name del usuario que se quiere obtener
	 * @return objeto usuario con identificador screenName
	 */
	public abstract User getUser(String screenName);

	/**
	 * @param userB screen name del usuario 
	 * @return true si el usuario es seguidor de usuario logueado
	 */
	public abstract boolean isFollower(String userB);

	/**
	 * @param followerScreenName
	 * @param followedScreenName
	 * @return true si followerScreenName sigue a followedScreenName
	 */
	public abstract boolean isFollower(String followerScreenName,
			String followedScreenName);

	/**
	 * @param userB screen name del usuario 
	 * @return true si el usuario es seguido por el usuario logueado
	 */
	public abstract boolean isFollowing(java.lang.String userB);

	/**
	 * @param usuario
	 * @return true si el usuario es seguido por el usuario logueado
	 */
	public abstract boolean isFollowing(User user);
	
	/**
	 * Confirma la petición, aceptandola. Esto significa que el usuario u
	 * comienza a seguir al usuario logueado
	 * @param u usuario aceptado
	 */
	public void confirmarAmistad(User u/* usuario aceptado*/);

	/**
	 * Busqueda por screenName, devuelve una lista con usuarios cuyos screenNames 
	 * contengan el string pasado
	 * @param search parametro de busqueda
	 * @return lista de usuarios cuyo screen name contiene el parametro
	 */
	public abstract java.util.List<User> searchUsers(String search);

	/**
	 * 
	 * @param userId id del usuario
	 * @return obtiene informacion del usuario indicado
	 */
	public abstract User show(Number userId);

	/**
	 * 
	 * @param screenName del usuario
	 * @return obtiene informacion del usuario indicado
	 * @throws TwitterException si el usuario no existe
	 */
	public abstract User show(String screenName) throws TwitterException;

/**
 * @param userIds lista de usuarios 
 * @return obtiene la lista de usuarios representados por los ids pasados como parametro
 */
	public abstract List<User> showById(
			java.util.Collection<? extends Number> userIds);

	/**
	 * Deja de seguir a un usuario 
	 * @param username usuario que deja de seguir
	 * @return usuario que deja de seguir
	 */
	public abstract User stopFollowing(String username);

	/**
	 * Deja de seguir a un usuario 
	 * @param usuario que deja de seguir
	 * @return usuario que deja de seguir
	 */
	public abstract User stopFollowing(User user);

	/**
	 * @param screenName screen name del usuario
	 * @return true si el usuario existe
	 */
	public abstract boolean userExists(String screenName);

}