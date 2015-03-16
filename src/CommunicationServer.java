import javax.swing.JTextArea;

import java.io.*;
import java.net.*;


public class CommunicationServer {
	InThread inThread = new InThread();
	OutThread outThread =  new OutThread();
	private JTextArea clientGeneralChat;
	
	
	
	public CommunicationServer(JTextArea generalChat) throws IOException{
	
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = inFromUser.readLine();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
		      
		clientGeneralChat = generalChat;
		inThread.start();
	}
	public void getClientMessage(String message){
		System.out.println("CS " + message);
	}
	
	public class InThread extends Thread{
		
		public void run(){
			System.out.println("My Thread is running");
			clientGeneralChat.append("APPPENED FROM CS");
			
			System.out.println("CS: " + clientGeneralChat.getText());
		}
	}
	
	public class OutThread extends Thread{
		
		public void run(){
			
		}
	}
}
