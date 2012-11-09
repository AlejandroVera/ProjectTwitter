package interfacesComunes;

import excepcionesComunes.TwitterException;

public interface Twitter {

	public Twitter_Account account();
	
	/**
	 * Devuelve el número de caracteres de un texto teniendo en cuenta que Twitter trata las urls como cadenas de 20 caracteres.
	 * @param statusText Texto del que obtener la longitud
	 * @return Número de caracteres del texto
	 */
	public int countCharacters(String statusText);
	
	public void destroy(Twitter.ITweet tweet) throws TwitterException;
	
	public void destroyMessage(java.lang.Number id); //Y ESTE POR QUÉ COJONES NO LANZA EXCEPCIÓN??
	
	public void destroyStatus(java.lang.Number id) throws TwitterException;
	
	
}
