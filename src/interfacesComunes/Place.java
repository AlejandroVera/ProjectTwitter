package interfacesComunes;

import java.awt.geom.Point2D;
import java.io.Serializable;

import com.winterwell.jgeoplanet.BoundingBox;
import com.winterwell.jgeoplanet.IPlace;

public interface Place extends IPlace, Serializable {
	
    //public com.winterwell.jgeoplanet.BoundingBox getBoundingBox();
    //public java.lang.String getCountryCode();
    public java.lang.String getCountryName();
    //public java.util.List<com.winterwell.jgeoplanet.Location> getGeometry();
    public String getId();
    //public java.lang.String getInfoUrl();
    public java.lang.String getName();
    public java.lang.String getType();
    public java.lang.String toString();
    //public com.winterwell.jgeoplanet.Location getCentroid();
   // public java.lang.String getUID();
    public BoundingBox getBoundingBox(); //La Bounding Box del lugar, el cuadradito que ocupa
    
	
}
