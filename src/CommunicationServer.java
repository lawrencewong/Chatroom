import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

import java.io.*;
import java.net.*;


public class CommunicationServer {
	InThread inThread = new InThread();
	OutThread outThread =  new OutThread();
	private JTextArea clientGeneralChat;
	private DefaultListModel<String> clientModel;
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	DatagramSocket clientSocket = new DatagramSocket();
	InetAddress IPAddress;
	String clientName;
	private messageOBJ outMessage = null;
	
	public CommunicationServer(JTextArea generalChat,  DefaultListModel<String> clientUsernameModel, String ipAddress,  String username) throws IOException{
		IPAddress  = InetAddress.getByName(ipAddress);
		clientGeneralChat = generalChat;
		clientModel = clientUsernameModel;
		clientName = username;
		outThread.start();
		inThread.start();
	}
	public void getClientMessage(String message, String username, String target, String messageType){
		messageOBJ outPacket = new messageOBJ();
		outPacket.setMessageOBJMessage(message);
		outPacket.setUsernameOBJMessage(username);
		outPacket.setTargetOBJMessage(target);
		outPacket.setTypeOBJMessage(messageType);
		outMessage = outPacket;
		outThread.run();
	}
	
	public void logoutChatroom(String username){
		messageOBJ outPacket = new messageOBJ();
		outPacket.setMessageOBJMessage(null);
		outPacket.setUsernameOBJMessage(username);
		outPacket.setTargetOBJMessage(null);
		outPacket.setTypeOBJMessage("LO");
		outMessage = outPacket;
		outThread.run();
	}

	public void loginChatroom(String username){
		messageOBJ outPacket = new messageOBJ();
		outPacket.setMessageOBJMessage(null);
		outPacket.setUsernameOBJMessage(username);
		outPacket.setTargetOBJMessage(null);
		outPacket.setTypeOBJMessage("LN");
		outMessage = outPacket;
		outThread.run();
	}
	
	public class InThread extends Thread{
		
		public void run(){
			byte[] receiveData = new byte[1024];
			
			while(true){
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
					clientSocket.receive(receivePacket);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                receiveData = receivePacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                ObjectInputStream is = null;
				try {
					is = new ObjectInputStream(in);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                
                try{
                	messageOBJ receiveMessage = (messageOBJ) is.readObject();
                	
                	if(receiveMessage.getTypeOBJMessage().equals("GC")){
                		clientGeneralChat.append(receiveMessage.getUsernameOBJMessage() + " - " + receiveMessage.getMessageOBJMessage() +"\n");
                	} else if(receiveMessage.getTypeOBJMessage().equals("LN") && !(receiveMessage.getUsernameOBJMessage().equals(clientName))){
                		clientModel.addElement(receiveMessage.getUsernameOBJMessage());
                		clientGeneralChat.append(receiveMessage.getUsernameOBJMessage() + " has logged in.\n");
                	} else if(receiveMessage.getTypeOBJMessage().equals("UL")){
                		clientModel.addElement(receiveMessage.getMessageOBJMessage());
                	} else if (receiveMessage.getTypeOBJMessage().equals("LO")){
                		clientModel.removeElement(receiveMessage.getUsernameOBJMessage());
                		clientGeneralChat.append(receiveMessage.getUsernameOBJMessage() + " has logged out.\n");
                	} else if(receiveMessage.getTypeOBJMessage().equals("PC")){
                		clientGeneralChat.append("PRIVATE MESSAGE FROM " + receiveMessage.getUsernameOBJMessage() + " - " + receiveMessage.getMessageOBJMessage() +"\n");
                	}
                } catch (ClassNotFoundException e){
                	e.printStackTrace();
                } catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public class OutThread extends Thread{
		
		public void run(){
			if(!(outMessage == null)){
				byte[] sendData;
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = null;
				try {
					os = new ObjectOutputStream(outputStream);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					os.writeObject(outMessage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				sendData = outputStream.toByteArray();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
				try {
					clientSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outMessage = null;
			}
		}
	}
}
