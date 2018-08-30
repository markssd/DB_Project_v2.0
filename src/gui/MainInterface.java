package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingConstants;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;

public class MainInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface window = new MainInterface(con, user_id, type);
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
	public MainInterface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSeiConnessoAl = new JLabel("Sei connesso al database!");
		lblSeiConnessoAl.setForeground(Color.WHITE);
		lblSeiConnessoAl.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSeiConnessoAl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSeiConnessoAl.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSeiConnessoAl.setBounds(198, 58, 264, 44);
		frame.getContentPane().add(lblSeiConnessoAl);
		
		JButton btnNormal = new JButton("Area Utenti");
		btnNormal.setBounds(250, 115, 123, 59);
		frame.getContentPane().add(btnNormal);
		
		JButton btnAdmin = new JButton("Area Admin");
		btnAdmin.setBounds(250, 185, 123, 59);
		frame.getContentPane().add(btnAdmin);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(527, 347, 102, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel image = new JLabel("");
		image.setIcon(new ImageIcon(MainInterface.class.getResource("/gui/Main.jpg")));
		image.setBounds(0, 0, 651, 393);
		frame.getContentPane().add(image);
		
		btnNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
					NormalInterface NorIn = new NormalInterface(con, user_id, type);
					NorIn.setVisible(true);
					frame.dispose();

			}
		});
		
		btnAdmin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
				char typeC = type.charAt(0);
				if (typeC == 'A') {
						AdminInterface AdIn = new AdminInterface(con, user_id, type);
						AdIn.setVisible(true);
						frame.dispose();
			    } else {
			    	JOptionPane.showMessageDialog(null, "Non hai i permessi per accedere a quest'area");
			    }
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
	
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
}
