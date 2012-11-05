import java.io.Serializable;


public class User implements Serializable{
	
	private static final long serialVersionUID = -4749433293227574768L;

	private String name;
	
	public User(String name){
		this.name = name;
	}
	
	String getName(){
		return name;
	}
	
}
