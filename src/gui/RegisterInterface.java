package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTextField;

import controller.UserHandler;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;


public class RegisterInterface {
	

	private JFrame frame;
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField usernameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField password2Field;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterInterface window = new RegisterInterface(con, user_id, type);
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
	public RegisterInterface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		nameField = new JTextField();
		nameField.setBounds(275, 29, 103, 32);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		surnameField = new JTextField();
		surnameField.setBounds(275, 76, 103, 32);
		frame.getContentPane().add(surnameField);
		surnameField.setColumns(10);
		
		usernameField = new JTextField();
		usernameField.setBounds(275, 119, 103, 32);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setBounds(275, 245, 103, 32);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JButton btnRegister = new JButton("Registra");
		btnRegister.setBounds(286, 313, 81, 32);
		frame.getContentPane().add(btnRegister);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(208, 38, 57, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setForeground(Color.WHITE);
		lblSurname.setBounds(196, 85, 69, 14);
		frame.getContentPane().add(lblSurname);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(196, 128, 81, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(196, 162, 81, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblPassword2 = new JLabel("Conferma pass:");
		lblPassword2.setForeground(Color.WHITE);
		lblPassword2.setBounds(184, 211, 103, 14);
		frame.getContentPane().add(lblPassword2);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(208, 254, 57, 14);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblFeedbackU = new JLabel("");
		lblFeedbackU.setForeground(Color.RED);
		lblFeedbackU.setBounds(388, 128, 191, 14);
		frame.getContentPane().add(lblFeedbackU);
		
		JLabel lblFeedbackP = new JLabel("");
		lblFeedbackP.setForeground(Color.RED);
		lblFeedbackP.setBounds(388, 162, 191, 14);
		frame.getContentPane().add(lblFeedbackP);
		
		JLabel lblFeedbackP2 = new JLabel("");
		lblFeedbackP2.setForeground(Color.RED);
		lblFeedbackP2.setBounds(388, 211, 191, 14);
		frame.getContentPane().add(lblFeedbackP2);
		
		JLabel lblFeedbackE = new JLabel("");
		lblFeedbackE.setForeground(Color.RED);
		lblFeedbackE.setBounds(388, 254, 191, 14);
		frame.getContentPane().add(lblFeedbackE);
		
		JLabel lblFeedbackN = new JLabel("");
		lblFeedbackN.setForeground(Color.RED);
		lblFeedbackN.setBounds(388, 47, 191, 14);
		frame.getContentPane().add(lblFeedbackN);
		
		JLabel lblFeedbackS = new JLabel("");
		lblFeedbackS.setForeground(Color.RED);
		lblFeedbackS.setBounds(388, 91, 191, 14);
		frame.getContentPane().add(lblFeedbackS);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(275, 162, 103, 32);
		frame.getContentPane().add(passwordField);
		
		password2Field = new JPasswordField();
		password2Field.setBounds(275, 202, 103, 32);
		frame.getContentPane().add(password2Field);
		
		JLabel lblFeedback = new JLabel("");
		lblFeedback.setForeground(Color.RED);
		lblFeedback.setBounds(276, 32, 102, 14);
		frame.getContentPane().add(lblFeedback);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 337, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JCheckBox checkBox = new JCheckBox("");
		checkBox.setBorderPaintedFlat(true);
		checkBox.setBorderPainted(true);
		checkBox.setBackground(Color.BLACK);
		checkBox.setBounds(357, 282, 21, 20);
		frame.getContentPane().add(checkBox);
		
		JLabel lblVuoiCheSia = new JLabel("Vuoi che sia un admin?");
		lblVuoiCheSia.setForeground(Color.WHITE);
		lblVuoiCheSia.setBounds(184, 288, 144, 14);
		frame.getContentPane().add(lblVuoiCheSia);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(526, 337, 103, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(RegisterInterface.class.getResource("/gui/Admin.jpg")));
		Image.setBounds(0, 0, 651, 393);
		frame.getContentPane().add(Image);
		
		
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name = nameField.getText();
				if (checkName(name))
				{
					lblFeedbackN.setText("<html><font color='green'>Ok!</font></html>");
				} else {
					lblFeedbackN.setText("Deve contenere solo lettere");
				}
			}
		});
		
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblFeedbackN.setText("");
			}
		});

		
		
		surnameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String surname = surnameField.getText();
				if (checkSurname(surname))
				{
					lblFeedbackS.setText("<html><font color='green'>Ok!</font></html>");
				} else {
					lblFeedbackS.setText("Deve contenere solo lettere");
				}
			}
		});
		
		surnameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblFeedbackS.setText("");
			}
		});

		
		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String username = usernameField.getText();
				if (checkUsername(username))
				{
					lblFeedbackU.setText("<html><font color='green'>Ok!</font></html>");
				} else {
					lblFeedbackU.setText("Deve avere più di 6 caratteri ");
				}
			}
		});
		
		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblFeedbackU.setText("");
			}
		});

		
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				char[] password = passwordField.getPassword();
				if (checkPassword(password))
				{
					lblFeedbackP.setText("<html><font color='green'>Ok!</font></html>");
				} else {
					lblFeedbackP.setText("Deve avere più di 6 caratteri");
				}
			}
		});
		
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblFeedbackP.setText("");
			}
		});

		
		password2Field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				char[] password = passwordField.getPassword();
				char[] password2 = password2Field.getPassword();
				String pass = new String(password);
				String pass2 = new String(password2);
				if (pass.equals(pass2)){
					lblFeedbackP2.setText("<html><font color='green'>Ok!</font></html>");
				} else {
					lblFeedbackP2.setText("Le password devono coincidere");
				}
			}
		});
		
		password2Field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblFeedbackP2.setText("");
			}
		});

		
		emailField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = emailField.getText();
				if (checkEmail(email))
				{
					lblFeedbackE.setText("<html><font color='green'>Ok!</font></html>");
				} else {
					lblFeedbackE.setText("E-Mail non valida");
				}
			}
		});
		
		emailField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblFeedbackE.setText("");
			}
		});

		
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
				String name = nameField.getText();
				String surname = surnameField.getText();
				String user = usernameField.getText();
				String e_mail = emailField.getText();
				char[] password = passwordField.getPassword();
				String pass = new String(password);
				String type = "R";
				if (checkBox.isSelected() == true) {
					type = "A";
				}
				if (checkAll(name,surname,user,password,e_mail)) {
					try {
						UserHandler.register(name.toLowerCase(),surname.toLowerCase(),user,pass,e_mail,type,con);
						lblFeedbackN.setText("");
						lblFeedbackS.setText("");
						lblFeedbackU.setText("");
						lblFeedbackP.setText("");
						lblFeedbackP2.setText("");
						lblFeedbackE.setText("");
					} catch (SQLException e1) {} 
				} else {
					lblFeedback.setText("Ci sono errori");
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					AdminInterface AP = new AdminInterface(con,user_id,type);
					AP.setVisible(true);
					frame.dispose();
				
			}
			
		});
		
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
					try {
						JOptionPane.showMessageDialog(null, "Arrivederci "+user_id);
						LoginInterface LoIn = new LoginInterface();
						LoIn.setVisible(true);
						frame.dispose();
						con.close();
					} catch (SQLException e1) {} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} 
			    } 
		});
	}
	
	
	//CHECK
		private boolean checkName(String name) {
			if (Pattern.matches("[a-zA-Z]+", name)) {
				return true;
			} else {
				return false;
			}
		}
		
		private boolean checkSurname(String surname) {
			if (Pattern.matches("[a-zA-Z]+", surname)) {
				return true;
			} else {
				return false;
			}
		}
		
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
		
		private boolean checkEmail(String e_mail) {
			return e_mail.contains("@");
		}
		
		private boolean checkAll(String name, String surname, String username, char[] password, String e_mail)
		{
			return checkName(name) && checkSurname(surname) && checkUsername(username) && checkPassword(password) && checkEmail(e_mail);
		}
		
		public void setVisible(boolean b) {
			frame.setVisible(b);
			
		}
}


