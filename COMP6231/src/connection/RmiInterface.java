package connection;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;


public interface RmiInterface extends Remote {
	public boolean createTRecord(String firstName, String lastName, String address, String phone, String specialization,
			String location) throws RemoteException;

	public boolean createSRecord(String firstName, String lastName, Set<String> courseRegistered, String status,
			String statusDate) throws RemoteException;

	public String getRecordCounts() throws RemoteException;

	public boolean editRecord(String recordID, String fieldName, String newValue) throws RemoteException;
}


