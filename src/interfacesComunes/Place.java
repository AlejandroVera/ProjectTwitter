package interfacesComunes;

import java.io.Serializable;

import com.winterwell.jgeoplanet.BoundingBox;
import com.winterwell.jgeoplanet.IPlace;

public interface Place extends IPlace, Serializable {
	
    //public com.winterwell.jgeoplanet.BoundingBox getBoundingBox();
    //public java.lang.String getCountryCode();
	
	/**Nombre del pais*/
    public java.lang.String getCountryName();
    //public java.util.List<com.winterwell.jgeoplanet.Location> getGeometry();
    
    /**Id del place*/
    public String getId();
    //public java.lang.String getInfoUrl();
    
    /**Devuelve el nombre del place*/
    public java.lang.String getName();
    
    /**Devuelve el tipo del place, puede ser cualquier String*/
    public java.lang.String getType();
    
    /**Devuelve el place en forma de String. Muy util para interfaz gráfica*/
    public java.lang.String toString();
    
    /**Centro del place, en forma de Location (latitud, longitud)*/
    public com.winterwell.jgeoplanet.Location getCentroid();
   // public java.lang.String getUID();
    
    /**La boundingBox del lugar, el cuadrado que ocupa*/
    public BoundingBox getBoundingBox(); //La Bounding Box del lugar, el cuadradito que ocupa
    
	
}
