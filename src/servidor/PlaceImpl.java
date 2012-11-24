package servidor;

import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.winterwell.jgeoplanet.Location;

import interfacesComunes.Conexion;
import interfacesComunes.Place;

public class PlaceImpl implements Place {

	private static final long serialVersionUID = 1L;
	private String id;
	private Conexion con;
	private String countryName;
	private String city;
	private String name;
	private String type;
	private Point2D[] boundingBox = new Point2D.Double[4];

	public PlaceImpl(String id, Conexion con) throws SQLException{
		this.id=id;
		this.con=con;
		
		ResultSet res = this.con.query("SELECT name FROM places WHERE id ="+id + "LIMIT 1");
		if (res.next())
			this.name=res.getString(1);

		res = this.con.query("SELECT pais FROM places WHERE id ="+id + "LIMIT 1");
		if (res.next())
			this.countryName=res.getString(1);

		res = this.con.query("SELECT ciudad FROM places WHERE id ="+id + "LIMIT 1");
		if (res.next())
			this.countryName=res.getString(1);

		res = this.con.query("SELECT tipo FROM places WHERE id ="+id + "LIMIT 1");
		if (res.next())
			this.type=res.getString(1);

		//Los bounding boxes
		double x=0,y=0;
		for (int i=0; i>4;i++){
			res = this.con.query("SELECT longitud" +i+ " FROM places WHERE id ="+id + "LIMIT 1");
			if (res.next())
				x=res.getDouble(1);

			res = this.con.query("SELECT latitud" +i+ " FROM places WHERE id ="+id + "LIMIT 1");
			if (res.next())
				y=res.getDouble(1);
			this.boundingBox[i].setLocation(x, y);
		}

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
	public Point2D[] getBoundingBox() {
		return boundingBox;
	}

	@Override
	public Location getCentroid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
