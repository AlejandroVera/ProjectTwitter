package servidor;

import interfacesComunes.Tweeter_Users;
import interfacesComunes.User;
//TODO
public class Tweeter_UsersImpl implements Tweeter_Users {

	private User user;
	
	public Tweeter_UsersImpl(User user){
		
		this.user=user;
	}
}
