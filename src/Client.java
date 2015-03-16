import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.SwingConstants;


public class Client {

	private JFrame frame;
	private JTextField txtWrtieMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client(args[0],args[1]);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client(String ipAddress, String clientName) {
		initialize(ipAddress, clientName);
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
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 49, 448, 368);
		frame.getContentPane().add(textArea);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setBounds(468, 13, 46, 14);
		frame.getContentPane().add(lblUsers);
		
		txtWrtieMessage = new JTextField();
		txtWrtieMessage.setHorizontalAlignment(SwingConstants.LEFT);
		txtWrtieMessage.setBounds(10, 428, 448, 72);
		frame.getContentPane().add(txtWrtieMessage);
		txtWrtieMessage.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBounds(468, 447, 160, 35);
		frame.getContentPane().add(btnNewButton);
		
		JList list = new JList();
		list.setBounds(468, 49, 160, 368);
		frame.getContentPane().add(list);
		
		JLabel label = new JLabel("Logged in as " + clientName);
		label.setBounds(10, 505, 618, 14);
		frame.getContentPane().add(label);
		frame.setVisible(true);
	}
}
