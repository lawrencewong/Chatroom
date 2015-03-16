import javax.swing.JTextArea;

import java.io.*;
import java.net.*;


public class CommunicationServer {
	InThread inThread = new InThread();
	OutThread outThread =  new OutThread();
	private JTextArea clientGeneralChat;
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	DatagramSocket clientSocket = new DatagramSocket();
	InetAddress IPAddress;
	private String outMessage = "";
	
	
	public CommunicationServer(JTextArea generalChat, String ipAddress) throws IOException{
	
		IPAddress  = InetAddress.getByName(ipAddress);
		clientGeneralChat = generalChat;
		outThread.start();
		inThread.start();
		
	}
	public void getClientMessage(String message){
		System.out.println("CS " + message);
		outMessage = message;
		outThread.run();
	}
	
	public class InThread extends Thread{
		
		public void run(){
			byte[] receiveData = new byte[1024];
			
			while(true){
				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					clientSocket.receive(receivePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String modifiedSentence = new String(receivePacket.getData());
				clientGeneralChat.append(modifiedSentence+"\n");
			}
		}
	}
	
	public class OutThread extends Thread{
		
		public void run(){
			if(!outMessage.equals("")){
				byte[] sendData = new byte[1024];
				
				//String sentence = "Hello Server!";

				sendData = outMessage.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
				try {
					clientSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outMessage = "";
			}
		}
	}
}
