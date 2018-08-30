package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import controller.DBHandler;
import controller.UserHandler;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LoginInterface {
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginInterface window = new LoginInterface();
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
	public LoginInterface() throws SQLException, ClassNotFoundException{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(255, 118, 122, 29);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(255, 152, 122, 29);
		frame.getContentPane().add(passwordField);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(189, 125, 66, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(189, 155, 66, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Accedi");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBounds(255, 183, 122, 45);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblFeedbackU = new JLabel("");
		lblFeedbackU.setForeground(Color.RED);
		lblFeedbackU.setBounds(390, 125, 225, 14);
		frame.getContentPane().add(lblFeedbackU);
		
		JLabel lblFeedbackP = new JLabel("");
		lblFeedbackP.setForeground(Color.RED);
		lblFeedbackP.setBounds(390, 155, 225, 14);
		frame.getContentPane().add(lblFeedbackP);
		
		JLabel lblFeedbackT = new JLabel("");
		lblFeedbackT.setForeground(Color.RED);
		lblFeedbackT.setBounds(255, 97, 122, 14);
		frame.getContentPane().add(lblFeedbackT);
		
		JLabel lblNewLabel = new JLabel("Creato da Marco Swid per il corso Basi di Dati anno accademico 2017/2018");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(139, 339, 413, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(LoginInterface.class.getResource("/gui/Log.jpg")));
		Image.setBounds(0, 0, 650, 393);
		frame.getContentPane().add(Image);
		
		
		
		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String user = usernameField.getText();
				if (checkUsername(user))
				{
					lblFeedbackU.setText("<html><font color = 'white'>Ok</font></html>");
				} else {
					lblFeedbackU.setText("Deve avere più di 6 caratteri");
				}
			}
		});
		
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				char[] password = passwordField.getPassword();
				if (checkPassword(password))
				{
					lblFeedbackP.setText("<html><font color = 'white'>Ok</font></html>");
				} else {
					lblFeedbackP.setText("Deve avere più di 6 caratteri");
				}
			}
		}); 
		
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
				String user = usernameField.getText();
				char[] password = passwordField.getPassword();
				if (checkAll(user,password)) {
					try {
						String pass = new String(password);
						//establish database connection
						DBHandler dbcon = new DBHandler();
						Connection con = dbcon.getConnection();
						UserHandler.login(user, pass, con);
						frame.dispose();
					} catch (SQLException e1) {} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} 
				} else {
					lblFeedbackT.setText("Ci sono errori");
				}
			}
		});
		
		
}
	
	//CHECK
	private boolean checkUsername(String username) {
		if (username.length() >= 6) {
			return true ;
		} else {
			return false; 
		}
	}
		
	private boolean checkPassword(char[] password) {
		String pass = new String(password);
		if (pass.length() >= 6) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkAll(String username, char[] password)
	{
		return checkUsername(username) && checkPassword(password);
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
	}
