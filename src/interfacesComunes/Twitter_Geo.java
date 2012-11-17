package interfacesComunes;

import java.io.Serializable;



public interface Twitter_Geo extends Serializable{
	
    //public Place geoSearch(double latitude,double longitude);
    public Place geoSearchByIP(java.lang.String ipAddress);

    //public java.util.List<Place> getTrendRegions();

    //public void setAccuracy(double metres);

    //public com.winterwell.jgeoplanet.IPlace getPlace(java.lang.String locationDescription,
    //                                        com.winterwell.jgeoplanet.MFloat confidence)

}
