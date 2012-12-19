package interfacesComunes;

import java.io.Serializable;

import com.winterwell.jgeoplanet.IGeoCode;
import com.winterwell.jgeoplanet.IPlace;


/**
 * Metodos para la geolocalizacion
 */
public interface Twitter_Geo extends Serializable, IGeoCode{
	
    //public Place geoSearch(double latitude,double longitude);
   
	/**Devuelve un place por geolocalizacion pasandole una direccion IP
	 * @param ipAddress la direccion IP
	 * @return Place el lugar donde estas
	 * */
	public Place geoSearchByIP(java.lang.String ipAddress);

    //public java.util.List<Place> getTrendRegions();

    //public void setAccuracy(double metres);
                                           

}
