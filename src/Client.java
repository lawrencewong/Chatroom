import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;


public class Client {

	private JFrame frame;
	private JTextField generalChatMessage;
	private JTextArea generalChat;
	private CommunicationServer comServer;
	private JTextField generalChatMessage2;
	private DefaultListModel<String> usernameListModel;

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Client(String ipAddress, String clientName) throws IOException {
		initialize(ipAddress, clientName);
		comServer = new CommunicationServer(generalChat, usernameListModel, ipAddress, clientName);
		comServer.loginChatroom(clientName);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String ipAddress, String clientName) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 654, 548);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle("The Chatroom @ " + ipAddress);
		frame.getContentPane().setLayout(null);
		
		frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                comServer.logoutChatroom(clientName);
            	System.exit(0);
            }


        });
		
		JLabel lblChatroom = new JLabel("The Chatroom");
		lblChatroom.setBounds(10, 3, 113, 35);
		frame.getContentPane().add(lblChatroom);
		
		generalChat = new JTextArea("");
		generalChat.setEditable(false);
		generalChat.setBounds(10, 49, 448, 368);
		frame.getContentPane().add(generalChat);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setBounds(468, 13, 46, 14);
		frame.getContentPane().add(lblUsers);
		
		JTextArea generalChatMessage = new JTextArea();
		generalChatMessage.setLineWrap(true);
		generalChatMessage.setBounds(10, 428, 308, 65);
		frame.getContentPane().add(generalChatMessage);

		JButton generalChatSend = new JButton("Send");
		generalChatSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				sendMessage(generalChatMessage.getText(), clientName, null, "GC");
				generalChatMessage.setText("");
			}
		});
		generalChatSend.setBounds(328, 447, 130, 35);
		frame.getContentPane().add(generalChatSend);
		
		usernameListModel = new DefaultListModel<String>();
		//usernameListModel.addElement(clientName);
				
		JList<String> usernameList = new JList<String>(usernameListModel);
		usernameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		usernameList.setBounds(468, 49, 160, 368);
		frame.getContentPane().add(usernameList);
		
		JLabel label = new JLabel("Logged in as " + clientName);
		label.setBounds(10, 505, 618, 14);
		frame.getContentPane().add(label);
		
		JButton btnNewButton = new JButton("Send Private Message");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(usernameList.getSelectedValue() != null && !(usernameList.getSelectedValue().equals(clientName))){
					sendMessage(generalChatMessage.getText(), clientName,  usernameList.getSelectedValue(), "PC");
					generalChatMessage.setText("");
				}
			}
		});
		btnNewButton.setBounds(468, 447, 160, 35);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
		
	}
	
	private void sendMessage(String message, String clientName, String targetClient, String messageType){
		System.out.println(message);
		if(messageType.equals("PC")){
			generalChat.append("PRIVATE MESSAGE TO " + targetClient + " - " + message +"\n");
		}
		comServer.getClientMessage(message, clientName, targetClient, messageType);
	}
}
