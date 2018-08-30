package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;

public class AdminInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminInterface window = new AdminInterface(con, user_id, type);
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
	public AdminInterface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Area Admin");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTitle.setBounds(249, 11, 170, 34);
		frame.getContentPane().add(lblTitle);
		
		JButton btnImport = new JButton("Importa nuovi dati");
		btnImport.setToolTipText("Area per importare nuovi dati da file \".csv\"");
		btnImport.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnImport.setBounds(137, 92, 164, 83);
		frame.getContentPane().add(btnImport);
		
		JButton btnRegister = new JButton("Registra nuovi utenti");
		btnRegister.setToolTipText("Area per registrare nuovi utenti.");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRegister.setBounds(137, 186, 164, 83);
		frame.getContentPane().add(btnRegister);
		
		JButton btnSat = new JButton("Inserisci nuovo satellite");
		btnSat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSat.setToolTipText("Area per inserire nuovi satelliti.");
		btnSat.setBounds(331, 92, 164, 83);
		frame.getContentPane().add(btnSat);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 347, 100, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(529, 347, 100, 23);
		frame.getContentPane().add(btnLogout);
		
		JButton btnIns = new JButton("Inserisci nuovo strumento");
		btnIns.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnIns.setToolTipText("Area per inserire nuovi strumenti con le relative bande.");
		btnIns.setBounds(330, 186, 165, 83);
		frame.getContentPane().add(btnIns);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(AdminInterface.class.getResource("/gui/Admin.jpg")));
		Image.setBounds(0, 0, 650, 391);
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
		
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
					ImportInterface ImIn;
					try {
						ImIn = new ImportInterface(con, user_id, type);
						ImIn.setVisible(true);
						frame.dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
					RegisterInterface RegIn = new RegisterInterface(con, user_id,type);
					RegIn.setVisible(true);
					frame.dispose();
			}
		});
		
		btnSat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
					SatelliteInterface SatIn = new SatelliteInterface(con, user_id, type);
					SatIn.setVisible(true);
					frame.dispose();
			}
		});
		
		btnIns.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
					InstrumentInterface InIn = new InstrumentInterface(con, user_id, type);
					InIn.setVisible(true);
					frame.dispose();
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
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
}
