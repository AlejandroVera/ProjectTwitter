package servidor;

import interfacesComunes.User;




public class UserImpl implements User{
	
	private static final long serialVersionUID = -4749433293227574768L;

	private String name;
	
	public UserImpl(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
