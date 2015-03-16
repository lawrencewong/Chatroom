import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class Connection {

	private JFrame frmTheChatroom;
	private JTextField iptextfield;
	private JLabel lblUsername;
	private JTextField usernametextfield;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection window = new Connection();
					window.frmTheChatroom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Connection() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTheChatroom = new JFrame();
		frmTheChatroom.setResizable(false);
		frmTheChatroom.setTitle("The Chatroom");
		frmTheChatroom.setBounds(100, 100, 436, 313);
		frmTheChatroom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTheChatroom.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to The Chatroom, please enter the server IP:");
		lblNewLabel.setBounds(68, 0, 352, 52);
		frmTheChatroom.getContentPane().add(lblNewLabel);
		
		JButton connectbutton = new JButton("Connect");
		connectbutton.setBounds(68, 224, 288, 26);
		connectbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmTheChatroom.setVisible(false);
				try {
					Client testClient = new Client(iptextfield.getText(), usernametextfield.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		iptextfield = new JTextField();
		iptextfield.setBounds(68, 55, 288, 52);
		lblNewLabel.setLabelFor(iptextfield);
		frmTheChatroom.getContentPane().add(iptextfield);
		iptextfield.setColumns(10);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(68, 107, 280, 42);
		frmTheChatroom.getContentPane().add(lblUsername);
		
		usernametextfield = new JTextField();
		usernametextfield.setBounds(68, 156, 288, 52);
		lblUsername.setLabelFor(usernametextfield);
		frmTheChatroom.getContentPane().add(usernametextfield);
		usernametextfield.setColumns(10);
		frmTheChatroom.getContentPane().add(connectbutton);
	}
}
