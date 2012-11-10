package servidor;

import interfacesComunes.Twitter_Users;
import interfacesComunes.User;
//TODO
public class Twitter_UsersImpl implements Twitter_Users {

	private User user;
	
	public Twitter_UsersImpl(User user){
		
		this.user=user;
	}
}
