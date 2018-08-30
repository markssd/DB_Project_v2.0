package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

import controller.Query;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class Search7Interface {

	private JFrame frame;
	private JTextField idField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search7Interface window = new Search7Interface(con, user_id, type);
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
	public Search7Interface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 347, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(524, 347, 105, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel Title = new JLabel("Distanza vertici di un ramo dal contorno");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Title.setBounds(147, 11, 357, 37);
		frame.getContentPane().add(Title);
		
		idField = new JTextField();
		idField.setBounds(238, 94, 150, 37);
		frame.getContentPane().add(idField);
		idField.setColumns(10);
		
		JLabel lblId = new JLabel("Id del ramo:");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(130, 104, 98, 14);
		frame.getContentPane().add(lblId);
		
		JButton btnSearch = new JButton("Cerca");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(267, 142, 89, 48);
		frame.getContentPane().add(btnSearch);
		
		JLabel feed = new JLabel("");
		feed.setForeground(Color.RED);
		feed.setBounds(237, 79, 211, 14);
		frame.getContentPane().add(feed);
		
		JTextArea resultField = new JTextArea();
		resultField.setEditable(false);
		resultField.setRows(1);
		resultField.setBounds(164, 249, 284, 85);
		frame.getContentPane().add(resultField);
		
		JLabel lblResult = new JLabel("Risultato:");
		lblResult.setForeground(Color.WHITE);
		lblResult.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblResult.setBounds(282, 213, 54, 23);
		frame.getContentPane().add(lblResult);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search7Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 650, 391);
		frame.getContentPane().add(Image);
		
		idField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String id = idField.getText();
				try {
					Integer.parseInt(id);
					feed.setText("");
				} catch (NumberFormatException e1) {
					feed.setText("L'id deve essere un intero!");
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String idS = idField.getText();
				int id;
				try {
					id = Integer.parseInt(idS);
				} catch (NumberFormatException e1) {
					return;
				}
				try {
					ArrayList<Double> result =Query.distanceVertix(con,id);
					if (result.isEmpty()) {
						resultField.setText("Il ramo cercato non esiste");
					} else {
						resultField.setLineWrap(true);
						resultField.setWrapStyleWord(true);
						resultField.setRows(10);
						resultField.setText("La distanza del ramo " + id + " dal contorno per il vertice minimo è "
								+ result.get(0) + " e per il massimo è " + result.get(result.size()- 1));
					}
				} catch (SQLException e1) {
					resultField.setText("Errore!");
				}
				
			}
		});

		btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NormalInterface NoIn = new NormalInterface(con,user_id,type);
				NoIn.setVisible(true);
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
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
}

