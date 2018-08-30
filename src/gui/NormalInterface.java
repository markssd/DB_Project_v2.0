package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.Color;

public class NormalInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NormalInterface window = new NormalInterface(con, user_id, type);
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
	public NormalInterface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Area Utenti");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTitle.setBounds(243, 11, 169, 75);
		frame.getContentPane().add(lblTitle);
		
		JButton btnSearch1 = new JButton("Ricerca informazioni filamenti");
		btnSearch1.setBounds(30, 82, 257, 42);
		frame.getContentPane().add(btnSearch1);
		
		JButton btnSearch2 = new JButton("Ricerca filamenti per brillanza o ellitticit\u00E0");
		btnSearch2.setBounds(30, 135, 257, 42);
		frame.getContentPane().add(btnSearch2);
		
		JButton btnSearch3 = new JButton("Ricerca filamenti per numero di segmenti");
		btnSearch3.setBounds(30, 188, 257, 42);
		frame.getContentPane().add(btnSearch3);
		
		JButton btnSearch5 = new JButton("Ricerca stelle in un filamento");
		btnSearch5.setBounds(344, 82, 257, 42);
		frame.getContentPane().add(btnSearch5);
		
		JButton btnSearch4 = new JButton("Ricerca filamenti in una regione");
		btnSearch4.setBounds(30, 241, 257, 42);
		frame.getContentPane().add(btnSearch4);
		
		JButton btnSearch6 = new JButton("Stelle in formazione nei filamenti di una regione");
		btnSearch6.setBounds(344, 135, 257, 42);
		frame.getContentPane().add(btnSearch6);
		
		JButton btnSearch7 = new JButton("Distanza vertici di un ramo dal contorno");
		btnSearch7.setBounds(344, 188, 257, 42);
		frame.getContentPane().add(btnSearch7);
		
		JButton btnSearch8 = new JButton("Posizione delle stelle in un filamento");
		btnSearch8.setBounds(344, 241, 257, 42);
		frame.getContentPane().add(btnSearch8);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 337, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(521, 337, 98, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(NormalInterface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 642, 387);
		frame.getContentPane().add(Image);
		
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
		
		btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					MainInterface MaIn = new MainInterface(con,user_id,type);
					MaIn.setVisible(true);
					frame.dispose();
				
			}
		});
	
		btnSearch1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Search1Interface Se1 = new Search1Interface(con,user_id,type);
					Se1.setVisible(true);
					frame.dispose();
				} catch( SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnSearch2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Search2Interface Se2 = new Search2Interface(con,user_id,type);
					Se2.setVisible(true);
					frame.dispose();
				
			}
		});
		
		btnSearch3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Search3Interface Se3 = new Search3Interface(con,user_id,type);
					Se3.setVisible(true);
					frame.dispose();

			}
		});
		
		btnSearch4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Search4Interface Se4 = new Search4Interface(con,user_id,type);
					Se4.setVisible(true);
					frame.dispose();
				
			}
		});
		
		btnSearch5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Search5Interface Se5 = new Search5Interface(con,user_id,type);
					Se5.setVisible(true);
					frame.dispose();
			}
		});
		
		btnSearch6.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Search6Interface Se6 = new Search6Interface(con,user_id,type);
					Se6.setVisible(true);
					frame.dispose();
				
			}
		});
		
		btnSearch7.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Search7Interface Se7 = new Search7Interface(con,user_id,type);
					Se7.setVisible(true);
					frame.dispose();
				
			}
		});
		
		btnSearch8.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					Search8Interface Se8 = new Search8Interface(con,user_id,type);
					Se8.setVisible(true);
					frame.dispose();
				
			}
		});

	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
}
