package servidor;

import interfacesComunes.User;




public class UserImpl implements User{
	
	private static final long serialVersionUID = -4749433293227574768L;

	private String name;
	private int id;
	private String location;
	
	//Constructor 1
	public UserImpl(String name, String location){
		this.name = name;
		this.location=location;
	}
	//Constructor 2
	public UserImpl(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
	
	public String getLocation(){
		return location;
	}
}
