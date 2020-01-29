package sever;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;



import java.util.HashSet;

import java.util.List;


import connection.RmiInterface;
import connection.Udp;
import record.Record;




public class CenterServer extends Thread implements RmiInterface {
	private int teacherNumber = 0;
	private int studentNumber = 0;
	private String servername;
	private int serverport;
	private int udpport;
	HashMap<String, List<Record>> hm = new HashMap<String, List<Record>>();
/**
 * 
 * @param servername
 * @param serverport
 * @param udpport
 * @throws IOException 
 */
	public CenterServer(String servername,int serverport,int udpport) throws IOException {
		this.servername = servername;
		this.serverport = serverport;
		this.udpport = udpport;
		
		Set<String> initial = new HashSet<>();
		initial.add("Math");
		initial.add("Chinese");
		
		Record TR1 = new Record("donald", "Huang", "TR99999" , "3166rueduverdun", "4389798803", "Chinese", "MTL");
		Record SR1 = new Record("raintea", "Song", "TR99998" ,  initial, "active", "18th May");
		
		List<Record> t1 = new ArrayList<Record>();
		List<Record> s1 = new ArrayList<Record>();
		
		t1.add(TR1);
		s1.add(SR1);
		
		hm.put("H", t1);
		hm.put("S", s1);
		System.out.println(servername + " server is running!");
		File f = new File(servername + ".log");
		if (!f.exists())
			f.createNewFile();
		file(this.servername, " server is up and running!!! " + " "
				+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public int getServerport() {
		return serverport;
	}
	public void setServerport(int serverport) {
		this.serverport = serverport;
	}
	public int getUdpport() {
		return udpport;
	}
	public void setUdpport(int udpport) {
		this.udpport = udpport;
	}

	public static void main(String[] args) throws Exception {
		CenterServer MTL = new CenterServer("Montreal", 2000, 5000);
		MTL.start();
		MTL.exportServer("MTL", 2000);
		CenterServer LVL = new CenterServer("Laval", 2100, 5001);
		LVL.start();
		LVL.exportServer("LVL", 2100);
		
		CenterServer DDO = new CenterServer("Dollard-des-Ormeaux", 2200, 5002);
		DDO.start();
		DDO.exportServer("DDO", 2200);
}
	public void file(String location, String stream) throws IOException {
		File f = new File(this.servername + ".log");
		FileWriter fw = new FileWriter(f, true);
		fw.write(location + ":" + stream);
		fw.write("\r\n");
		fw.flush();
		
	}
	public void exportServer(String servername, int port) throws Exception {
		Remote obj = UnicastRemoteObject.exportObject(this, port);
		Registry registry = LocateRegistry.createRegistry(port);
		registry.bind(servername, obj);
	}
	public boolean VerifyExist(String ID, String firstName, String lastName) throws IOException {
		Set<String> keys = hm.keySet();
		for (String key : keys) {
			List<Record> list = hm.get(key);
			for (Record record : list) {
				if ((record.getFirstName().equals(firstName)) && (record.getLastName().equals(lastName))
						&& (record.getRecordId().substring(0, 2).equals(ID))) {
					file(this.servername, " the user is already exists ! can't add this record.  RecordId is " + record.getRecordId() + " "
							+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
					return true;
				}
			}
		}
		return false;
	}
	
	public  boolean createTRecord (String firstName, String lastName, String address, String phone, String specialization, String location) {
		String initial = String.valueOf(lastName.charAt(0));
		boolean result = false;
		try {
			if (VerifyExist("TR", firstName, lastName)) {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String recordID = "TR" + String.format("%05d", teacherNumber);
		Record r = new Record(firstName, lastName, recordID, address, phone, specialization, location);
		if (hm.containsKey(initial)) {
			List<Record> l = hm.get(initial);
			l.add(r);
			result = true;
		} else {
			List<Record> l = new ArrayList<Record>();
			l.add(r);
			synchronized (this) {
				hm.put(initial, l);
				result = true;
			}
		}
		try {
			file(this.servername, " add a record! " + recordID + " "
					+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		teacherNumber+=1;
		return result;
		
	}
	public boolean createSRecord (String firstName, String lastName, Set<String>courseRegistered, String status, String statusDate) {
		String initial = String.valueOf(lastName.charAt(0));
		boolean result = false;
		try {
			if (VerifyExist("SR", firstName, lastName)) {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String recordID = "SR" + String.format("%05d", studentNumber);
		Record r = new Record(firstName, lastName, recordID, courseRegistered, status, statusDate);
		if (hm.containsKey(initial)) {
			List<Record> l = hm.get(initial);
			l.add(r);
			result = true;
		} else {
			List<Record> l = new ArrayList<Record>();
			l.add(r);
			synchronized (this) {
				hm.put(initial, l);
				result = true;
			}
		}
		try {
			file(this.servername, " add a record! " + recordID + " "
					+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		studentNumber+=1;
		return result;
}
	public String getRecordCounts() {
		
		Set<String> keys = hm.keySet();
		String resultLocal = this.servername + ": " + String.valueOf(keys.size());
		try {
			file(this.servername, " The server is called for get server local count. used by UDP socket" + resultLocal
					+ "" +new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultLocal;
	}
	public String getLocalCount() {
		return servername;
	}
	
	public boolean editRecord (String recordID, String fieldName, String newValue) {
		try {
			file(this.servername, " Editing the record  "+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Set<String> keys = hm.keySet();
		synchronized(this){
		for (String key : keys) {
		
			List<Record> list = hm.get(key);
			for (Record record : list) {
				if (record.getRecordId().equals(recordID)) {
					try {
						file(this.servername, " Find the RecordID, go to modify function  "+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					boolean flag = false;
					flag = record.editRecord(fieldName, newValue);
					if(flag == true){
						try {
							file(this.servername, " modify sucessfully  "+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						try {
							file(this.servername, " modify failed  "+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return flag;
				}
			}
			System.out.println("no record.");
			try {
				file(this.servername, " No records  "+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		return false;
	}
	public void run() {
		Udp us = new Udp();
		us.UdpServer(this);
	}
	
	
}

