

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class ConexionImpl extends UnicastRemoteObject implements
		ConexionInterfaz {

	
	private static final long serialVersionUID = -4305345588180033587L;
	LinkedList<ClienteInterfaz> clientes;
	
	protected ConexionImpl(LinkedList<ClienteInterfaz> clientes) throws RemoteException {
		super();
		this.clientes = clientes;
	}
	
	@Override
	public User getData(String user, String pass, ClienteInterfaz cliente) throws RemoteException {
		if(user.equals("Perico") && pass.equals("cachalote")){
			this.clientes.add(cliente);
			return new User("Perico");
		}else if(user.equals("Antonio") && pass.equals("pajarito")){
			this.clientes.add(cliente);
			return new User("Antonio");
		}else
			return null;
	}

}
