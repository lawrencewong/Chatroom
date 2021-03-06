import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	static ArrayList<clientInformation> clientInfoList = new ArrayList<clientInformation>();
	static DatagramSocket serverSocket;
	
	public static void main(String args[]) throws Exception{
		
		serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        messageOBJ receiveMessageOBJ = new messageOBJ();
         
        while(true){
        	clientInformation inClient = new clientInformation();
	        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	        serverSocket.receive(receivePacket);
	        receiveData = receivePacket.getData();
	        ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
	        ObjectInputStream is = new ObjectInputStream(in);
	        
	        InetAddress IPAddress = receivePacket.getAddress();
	        int port = receivePacket.getPort();
	        
	        try{
	        	receiveMessageOBJ = (messageOBJ) is.readObject();    
				inClient.setUsernameCI(receiveMessageOBJ.getUsernameOBJMessage());
				inClient.setIPCI(IPAddress);
				inClient.setPortCI(port);
				
				if( receiveMessageOBJ.getTypeOBJMessage().equals("LN")){
			    	clientInfoList.add(inClient);
			    	currentUsersList(inClient);
				}else if(receiveMessageOBJ.getTypeOBJMessage().equals("LO")){
					for(int i = 0; i < clientInfoList.size(); i++){
						if(clientInfoList.get(i).getUsernameCI().equals(receiveMessageOBJ.getUsernameOBJMessage())){
							 clientInfoList.remove(i);
						}
					}
				}
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
	
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = null;
			try {
				os = new ObjectOutputStream(outputStream);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				os.writeObject(receiveMessageOBJ);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			sendData = outputStream.toByteArray();
			
			if(receiveMessageOBJ.getTypeOBJMessage().equals("LN") || receiveMessageOBJ.getTypeOBJMessage().equals("GC") || receiveMessageOBJ.getTypeOBJMessage().equals("LO")){
				for(int i = 0; i < clientInfoList.size(); i++){
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientInfoList.get(i).getIPCI(), clientInfoList.get(i).getPortCI());
					try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else if(receiveMessageOBJ.getTypeOBJMessage().equals("PC")){
				for( int i =0; i < clientInfoList.size(); i++){
					if(receiveMessageOBJ.getTargetOBJMessage().equals( clientInfoList.get(i).getUsernameCI()) ){
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientInfoList.get(i).getIPCI(), clientInfoList.get(i).getPortCI());
						try {
							serverSocket.send(sendPacket);
						} catch (IOException e) {
							e.printStackTrace();
						}
							break;
						}
					}
					
				}
				receiveMessageOBJ = null;
				inClient = null;
			    
			 }
    }
	
	public static void currentUsersList(clientInformation client){
		for(int i =0; i < clientInfoList.size(); i++){
			
			byte[] sendData = new byte[1024];
			messageOBJ tempMessage = new messageOBJ();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = null;
			tempMessage.setTargetOBJMessage(null);
			tempMessage.setTypeOBJMessage("UL");
			tempMessage.setUsernameOBJMessage(null);
			tempMessage.setMessageOBJMessage(clientInfoList.get(i).getUsernameCI());
			
			try {
				os = new ObjectOutputStream(outputStream);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				os.writeObject(tempMessage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			sendData = outputStream.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client.getIPCI(), client.getPortCI());
			
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
