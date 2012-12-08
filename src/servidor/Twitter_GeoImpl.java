package servidor;

import java.net.InetAddress;
import java.sql.ResultSet;

import com.winterwell.jgeoplanet.IPlace;
import com.winterwell.jgeoplanet.Location;
import com.winterwell.jgeoplanet.MFloat;

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
		try {		
			Place lugar=null;
			boolean encontrado = false;


			InetAddress thisIP = InetAddress.getLocalHost();

			GeoLocation _gl = new GeoLocation();

			_gl.GetGeoLocation(thisIP);
		
			String Country = _gl.Country;
			String City = _gl.City;
			String Latitude = _gl.Latitude;
			String Longitude = _gl.Longitude;   
			Location punto= new Location(Double.parseDouble(Latitude), Double.parseDouble(Longitude));

			ResultSet res = con.query("SELECT id FROM places WHERE pais='"+Country+"' AND ciudad='"+City+"'");


			while (res.next() && !encontrado){
				lugar= new PlaceImpl(res.getString(1), con);
				if (lugar.getBoundingBox().contains(punto)){
					encontrado=true;
				}
			}
			return lugar;
		} catch (Exception e) {
			ServerCommon.TwitterWarning(e, "Fallo en la geolocalizacion al obtener el place");
			return null;
		}
		
	}

	@Override
	public IPlace getPlace(String locationDescription, MFloat confidence) {
		return geoSearchByIP("www.google.com");
	}
	
}

