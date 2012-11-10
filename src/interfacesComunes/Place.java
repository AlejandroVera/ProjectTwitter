package interfacesComunes;

import java.io.Serializable;

public interface Place extends Serializable {
	
    //public com.winterwell.jgeoplanet.BoundingBox getBoundingBox();
    //public java.lang.String getCountryCode();
    public java.lang.String getCountryName();
    //public java.util.List<com.winterwell.jgeoplanet.Location> getGeometry();
    public java.lang.String getId();
    //public java.lang.String getInfoUrl();
    public java.lang.String getName();
    public java.lang.String getType();
    public java.lang.String toString();
    //public com.winterwell.jgeoplanet.Location getCentroid();
    public java.lang.String getUID();
    public int[] getBoundingBox(); //Cuadro de coordenadas, la BoundingBox;
    
	
}
