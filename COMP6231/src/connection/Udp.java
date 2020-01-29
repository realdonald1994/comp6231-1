package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import sever.CenterServer;

public class Udp {

	public void UdpServer(CenterServer centerServer) {
		DatagramSocket aSocket = null;
		String localCount = null;
		try {
			aSocket = new DatagramSocket(centerServer.getUdpport());
			byte[] buffer = new byte[300];
			while (true) {
				DatagramPacket Request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(Request);

				localCount = centerServer.getLocalCount();
				byte[] localcount = localCount.getBytes();
				DatagramPacket Reply = new DatagramPacket(localcount, localcount.length, Request.getAddress(),
						Request.getPort());
				aSocket.send(Reply);
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}

}
