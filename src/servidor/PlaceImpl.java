package servidor;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.winterwell.jgeoplanet.BoundingBox;
import com.winterwell.jgeoplanet.Location;

import interfacesComunes.Conexion;
import interfacesComunes.Place;
import java.util.ArrayList;


public class PlaceImpl implements Place {

	private static final long serialVersionUID = 1L;
	private String id;
	private Conexion con;
	private String countryName;
	private String city;
	private String name;
	private String type;
	private BoundingBox boundingBox;

	/**
	 * Constructor para obtener un place de la base de datos
	 * @param id
	 * @param con
	 * @throws SQLException
	 */
	public PlaceImpl(String id, Conexion con) throws SQLException{
		this.id=id;
		this.con=con;
		
		ResultSet res = this.con.query("SELECT name FROM places WHERE id = "+id + " LIMIT 1");
		if (res.next())
			this.name=res.getString(1);

		res = this.con.query("SELECT pais FROM places WHERE id = "+id + " LIMIT 1");
		if (res.next())
			this.countryName=res.getString(1);

		res = this.con.query("SELECT ciudad FROM places WHERE id = "+id + " LIMIT 1");
		if (res.next())
			this.city=res.getString(1);

		res = this.con.query("SELECT tipo FROM places WHERE id = "+id + " LIMIT 1");
		if (res.next())
			this.type=res.getString(1);

		//Los bounding boxes
		double[] x= new double[2],y= new double[2];
		List<Location> bb = new ArrayList<Location>();
		
		/*Obtenemos las coordenadas de la base de datos*/
		for (int i=0; i<2;i++){
			res = this.con.query("SELECT longitud" +(i+1)+ " FROM places WHERE id = "+id + " LIMIT 1");
			if (res.next())
				x[i]=res.getDouble(1);

			res = this.con.query("SELECT latitud" +(i+1)+ " FROM places WHERE id = "+id + " LIMIT 1");
			if (res.next())
				y[i]=res.getDouble(1);
			
			Location loc = new Location(y[i], x[i]);
			bb.add(loc);
							
		}
		this.boundingBox=new BoundingBox(bb.get(0), bb.get(1));

	}
	
	/**
	 * Constructor para meter un place en la base de datos
	 * @param con
	 * @param punto
	 * @param Country
	 * @param City
	 */
	public PlaceImpl (Conexion con, Location punto, String Country, String City){
		List<Object> params=new ArrayList<Object>();
		
		this.city=City;
		this.countryName=Country;
		this.con=con;
		
		double longitud1= punto.getLongitude()+0.001;
		double latitud1= punto.getLatitude()+0.001;
		double longitud2= punto.getLongitude()-0.001;
		double latitud2= punto.getLatitude()-0.001;
				
		params.add(Country);
		params.add(City);
		params.add(longitud1);
		params.add(latitud1);
		params.add(longitud2);
		params.add(latitud2);
		Location l1= new Location(latitud1 ,longitud1);
		Location l2= new Location(latitud2 ,longitud2);
		this.boundingBox= new BoundingBox(l1,l2);
		//La metemos en la base de datos
		con.updateQuery("INSERT INTO places (pais, ciudad, longitud1, latitud1, longitud2, latitud2)" +
				" VALUES (?, ?, ?, ?, ?, ?)", params);
			
	}

	public String getCountryName() {
		return countryName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}
	
	public String getCity() {
		return city;
	}
	
	/*public String getUID() {
		return serialVersionUID;
	}*/

	@Override
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	@Override
	public Location getCentroid() {
		return this.boundingBox.getCenter();
	}

	@Override
	public String getUID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		if (getName()!=null)	
			return new String(this.getName()+", "+this.getCity()+", "+this.getCountryName());
		else
			return new String(this.getCity()+", "+this.getCountryName());
	}
	
	
	
}
