package servidor;

import java.awt.Point;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javaQuery.j2ee.GeoLocation;

import interfacesComunes.Place;
import interfacesComunes.Twitter_Geo;

public class Twitter_GeoImpl implements Twitter_Geo{

	@Override
	public Place geoSearchByIP(String ipAddress) {
		InetAddress thisIP;
		try {
			thisIP = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			excepcionesComunes.TwitterException(e,"Geolocalización fallida");
			e.printStackTrace();
		}
        GeoLocation _gl = new GeoLocation();

        _gl.GetGeoLocation(thisIP);
        String IP = _gl.IP;
        String Country = _gl.Country;
        String City = _gl.City;
        String Latitude = _gl.Latitude;
        String Longitude = _gl.Longitude;
	}
	

}
