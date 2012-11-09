package servidor;

import interfacesComunes.User;




public class UserImpl implements User{
	
	private static final long serialVersionUID = -4749433293227574768L;

	private String name;
	private int id;
	
	//Constrcutor 1
	public UserImpl(String name){
		this.name = name;
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
}
