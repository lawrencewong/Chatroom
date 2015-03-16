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
import java.io.IOException;


public class Client {

	private JFrame frame;
	private JTextField generalChatMessage;
	private JTextArea generalChat;
	private CommunicationServer comServer;
	

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Client(String ipAddress, String clientName) throws IOException {
		initialize(ipAddress, clientName);
		comServer = new CommunicationServer(generalChat, ipAddress);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String ipAddress, String clientName) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 654, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("The Chatroom @ " + ipAddress);
		frame.getContentPane().setLayout(null);
		
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
		
		generalChatMessage = new JTextField();
		generalChatMessage.setHorizontalAlignment(SwingConstants.LEFT);
		generalChatMessage.setBounds(10, 428, 308, 72);
		frame.getContentPane().add(generalChatMessage);
		generalChatMessage.setColumns(10);
		
		JButton generalChatSend = new JButton("Send");
		generalChatSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				sendMessage(generalChatMessage.getText());
				generalChatMessage.setText("");
			}
		});
		generalChatSend.setBounds(328, 447, 130, 35);
		frame.getContentPane().add(generalChatSend);
		
		DefaultListModel<String> usernameListModel = new DefaultListModel<String>();
		usernameListModel.addElement(clientName);
				
		JList<String> usernameList = new JList<String>(usernameListModel);
		usernameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		usernameList.setBounds(468, 49, 160, 368);
		frame.getContentPane().add(usernameList);
		
		JLabel label = new JLabel("Logged in as " + clientName);
		label.setBounds(10, 505, 618, 14);
		frame.getContentPane().add(label);
		
		JButton btnNewButton = new JButton("Start Private Chat");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(usernameList.getSelectedValue() != null){
					Privatechat privatechat = new Privatechat(usernameList.getSelectedValue());
				}
			}
		});
		btnNewButton.setBounds(468, 447, 160, 35);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
		
	}
	
	private void sendMessage(String message){
		System.out.println(message);
		comServer.getClientMessage(message);
	}
}
