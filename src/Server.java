import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

	public static void main(String args[]) throws Exception
    {

		DatagramSocket serverSocket = new DatagramSocket(9876);
          byte[] receiveData = new byte[1024];
          byte[] sendData = new byte[1024];
          messageOBJ receiveMessageOBJ = new messageOBJ();
          ArrayList<clientInformation> clientInfoList = new ArrayList<clientInformation>();
//          clientInformation tempClientInfo = null;
          while(true)
             {
        	  clientInformation inClient = new clientInformation();
        	  System.out.println("WAITING");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                receiveData = receivePacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                ObjectInputStream is = new ObjectInputStream(in);
                
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                try{
                	receiveMessageOBJ = (messageOBJ) is.readObject();

                	System.out.println("MESSAGE OBJ GOT");
                	System.out.println("USERNAME: " + receiveMessageOBJ.getUsernameOBJMessage());
                	System.out.println("Message: " + receiveMessageOBJ.getMessageOBJMessage());
                	System.out.println("TARGET: " + receiveMessageOBJ.getTargetOBJMessage() );
                	System.out.println("TYPE: " + receiveMessageOBJ.getTypeOBJMessage() );
                	// IF LOG IN
                	if( receiveMessageOBJ.getTypeOBJMessage().equals("LN")){
                		inClient.setUsernameCI(receiveMessageOBJ.getUsernameOBJMessage());
//                		tempClientInfo.setUsernameCI("EE");
                		inClient.setIPCI(IPAddress);
                		inClient.setPortCI(port);
                    	clientInfoList.add(inClient);
            		}
                	
                	// Find client
//                	if(receiveMessageOBJ.getTypeOBJMessage().equals("GC")){
//                		
//	                	for(int i = 0; i < clientInfoList.size(); i++){
//	                		if(clientInfoList.get(i).getUsernameCI().equals(receiveMessageOBJ.getUsernameOBJMessage())){
//	                			tempClientInfo = clientInfoList.get(i);
//	                			break;
//	                		}
//	                	}
//                	}
                	
                
                } catch (ClassNotFoundException e){
                	e.printStackTrace();
                }
                
                
                
                	
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = null;
				try {
					os = new ObjectOutputStream(outputStream);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					os.writeObject(receiveMessageOBJ);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sendData = outputStream.toByteArray();
				if(receiveMessageOBJ.getTypeOBJMessage().equals("LN") || receiveMessageOBJ.getTypeOBJMessage().equals("GC")){
					System.out.println("BROADCASTING");
					for(int i = 0; i < clientInfoList.size(); i++){
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientInfoList.get(i).getIPCI(), clientInfoList.get(i).getPortCI());
						try {
							serverSocket.send(sendPacket);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				receiveMessageOBJ = null;
				inClient = null;
                
             }
    }
}
