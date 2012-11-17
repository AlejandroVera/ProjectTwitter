package servidor;

import java.awt.geom.Point2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;


import javaQuery.j2ee.GeoLocation;
import interfacesComunes.Conexion;
import interfacesComunes.Place;
import interfacesComunes.Twitter_Geo;

public class Twitter_GeoImpl implements Twitter_Geo{
	
	private static final long serialVersionUID = 1861803125097847414L;
	
	private Conexion con;
	/*Te devuelve la place donde estas*/
	Twitter_GeoImpl (Conexion con){
		this.con=con;
	}
	 
	public Place geoSearchByIP(String ipAddress) {
		Place lugar=null;
		boolean encontrado = false;
		InetAddress thisIP = null;
		try {
			thisIP = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			ServerCommon.TwitterWarning(e,"Geolocalización fallida");
			e.printStackTrace();
		}
        GeoLocation _gl = new GeoLocation();

        _gl.GetGeoLocation(thisIP);
        //String IP = _gl.IP;
        String Country = _gl.Country;
        String City = _gl.City;
        double Latitude = Double.valueOf(_gl.Latitude);//y
        double Longitude = Double.valueOf(_gl.Longitude);//x
        Point2D punto = new Point2D.Double(Longitude, Latitude);
        
        ResultSet res = con.query("SELECT id FROM places WHERE pais='"+Country+"' AND ciudad='"+City+"'");
        
        try {
			while (res.next() && !encontrado){
				lugar= new PlaceImpl(res.getInt(1), con);
				if (contains(punto, lugar.getBoundingBox())){
					encontrado=true;
				}
			}
		} catch (SQLException e) {
			ServerCommon.TwitterWarning(e, "Fallo en la geolocalizacion al obtener el place");
			e.printStackTrace();
		}
       return lugar;
    }
    
	//Para saber si un punto esta dentro de un polígono
	public boolean contains(Point2D test, Point2D[] boundingBox) {
		int i;
		int j;
		boolean result = false;
		Point2D [] points=boundingBox;
		for (i = 0, j = points.length - 1; i < points.length; j = i++) {
			if ((points[i].getY() > test.getY()) != (points[j].getY() > test.getY()) &&
					(test.getX() < (points[j].getX() - points[i].getX()) * (test.getX() - points[i].getY())
							/ (points[j].getY()-points[i].getX()) + points[i].getX())) {
				result = !result;
			}
		}
		return result;
	}
  

}
