import java.io.*;
import java.net.*;

public class Server {

	public static void main(String args[]) throws Exception
    {

		DatagramSocket serverSocket = new DatagramSocket(9876);
          byte[] receiveData = new byte[1024];
          byte[] sendData = new byte[1024];
          messageOBJ receiveMessageOBJ = new messageOBJ();
          while(true)
             {
        	  System.out.println("WAITING");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                receiveData = receivePacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(receiveData);
                ObjectInputStream is = new ObjectInputStream(in);
                
                try{
                	receiveMessageOBJ = (messageOBJ) is.readObject();
                	System.out.println("MESSAGE OBJ GOT");
                	System.out.println("USERNAME: " + receiveMessageOBJ.getUsernameOBJMessage());
                	System.out.println("Message: " + receiveMessageOBJ.getMessageOBJMessage());
                	System.out.println("TARGET: " + receiveMessageOBJ.getTargetOBJMessage() );
                } catch (ClassNotFoundException e){
                	e.printStackTrace();
                }
                
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
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
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				receiveMessageOBJ = null;
                
//                String capitalizedSentence = sentence.toUpperCase();
//                sendData = capitalizedSentence.getBytes();
//                DatagramPacket sendPacket =
//                new DatagramPacket(sendData, sendData.length, IPAddress, port);
//                serverSocket.send(sendPacket);
             }
    }
}
