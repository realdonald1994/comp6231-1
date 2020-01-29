package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import connection.RmiInterface;



public class ManagerClient {
	public static void MutiCreateTRecord() throws RemoteException, NotBoundException {
		
		    Boolean result = false;
			String servername = "MTL";
			Registry registry = LocateRegistry.getRegistry(2000);
			RmiInterface centerServer = (RmiInterface) registry.lookup(servername);
			result = centerServer.createTRecord("chenwei", "Song", "3166rueduverdun", "123456789", "English", "MTL");
			if(result) {
				System.out.println(result);
			}
			
	}
	public static void getRecord() throws RemoteException, NotBoundException {
		
	    Boolean result = false;
		String servername = "MTL";
		Registry registry = LocateRegistry.getRegistry(2000);
		RmiInterface centerServer = (RmiInterface) registry.lookup(servername);
		
		System.out.println(centerServer.getRecordCounts());
		
}
	
	
	
	public static void main(String[] args) throws RemoteException, NotBoundException {
		getRecord();
		MutiCreateTRecord();
	}

}
