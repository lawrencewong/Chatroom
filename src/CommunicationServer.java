import javax.swing.JTextArea;


public class CommunicationServer {
	InThread inThread = new InThread();
	OutThread outThread =  new OutThread();
	private JTextArea clientGeneralChat;
	
	public CommunicationServer(JTextArea generalChat){
		clientGeneralChat = generalChat;
		inThread.start();
	}
	public void getClientMessage(String message){
		System.out.println("CS " + message);
	}
	
	public class InThread extends Thread{
		
		public void run(){
			System.out.println("My Thread is running");
			clientGeneralChat.setText("APPPENED FROM CS");
			
			System.out.println("CS: " + clientGeneralChat.getText());
		}
	}
	
	public class OutThread extends Thread{
		
		public void run(){
			
		}
	}
}
