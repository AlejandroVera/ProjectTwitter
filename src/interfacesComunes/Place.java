package interfacesComunes;

import java.io.Serializable;

import com.winterwell.jgeoplanet.BoundingBox;
import com.winterwell.jgeoplanet.IPlace;

/**
 * Representa un lugar con distintos campos de informacion útiles
 * para la geolocalización * 
 *
 */
public interface Place extends IPlace, Serializable {
	
    
    /**
	 * Nombre del pais
	 * @return String
	 */
    public java.lang.String getCountryName();
    
    
    /**
     * Id
     * @return String id en forma de String
     */
    public String getUID();
    
    
    /**
     * Nombre especifico del place
     * @return String
     */
    public java.lang.String getName();
    
    /**
     * Tipo de lugar
     * @return String tipo
     */
    public java.lang.String getType();
    
    /**
     * Devuelve el place en forma de String. Muy util para interfaz gráfica
     * @return String Nombre,Ciudad,Pais
     */
    public java.lang.String toString();
    
    /**
     * Centro del place
     * @return Location (latitud, longitud)
     */
      
    public com.winterwell.jgeoplanet.Location getCentroid();
  
    
    /**
     * La boundingBox del lugar, el cuadrado que ocupa
     * @return BoundingBox
     */
     
    public BoundingBox getBoundingBox();
    
	
}
