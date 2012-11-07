package servidor;





import interfacesComunes.ClienteCallback;
import interfacesComunes.Login;
import interfacesComunes.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;





public class LoginImpl extends UnicastRemoteObject implements
		Login {

	
	private static final long serialVersionUID = -4305345588180033587L;
	LinkedList<ClienteCallback> clientes;
	
	public LoginImpl(LinkedList<ClienteCallback> clientes) throws RemoteException {
		super();
		this.clientes = clientes;
	}
	
	@Override
	public User getData(String user, String pass, ClienteCallback cliente) throws RemoteException {
		if(user.equals("Perico") && pass.equals("cachalote")){
			this.clientes.add(cliente);
			return new UserImpl("Perico");
		}else if(user.equals("Antonio") && pass.equals("pajarito")){
			this.clientes.add(cliente);
			return new UserImpl("Antonio");
		}else
			return null;
	}

}
