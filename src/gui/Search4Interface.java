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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;


import controller.Query;
import model.Filament;
import model.Position;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class Search4Interface {

	private JFrame frame;
	private JTextField radField;
	private JTextField sideField;
	private JTextField longField;
	private JTextField latField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search4Interface window = new Search4Interface(con, user_id, type);
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
	public Search4Interface(Connection con, String user_id, String type) {
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
		
		JLabel Title = new JLabel("Ricerca filamenti in una regione");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Title.setBounds(179, 11, 282, 44);
		frame.getContentPane().add(Title);
		
		JComboBox<String> cbPer = new JComboBox<String>();
		cbPer.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Cerchio", "Quadrato"}));
		cbPer.setBounds(290, 66, 89, 20);
		frame.getContentPane().add(cbPer);
		
		JLabel lblScegliereIlPerimetro = new JLabel("Scegliere il perimetro della regione:");
		lblScegliereIlPerimetro.setForeground(Color.WHITE);
		lblScegliereIlPerimetro.setBounds(91, 69, 189, 14);
		frame.getContentPane().add(lblScegliereIlPerimetro);
		
		radField = new JTextField();
		radField.setEnabled(false);
		radField.setBounds(290, 94, 89, 31);
		frame.getContentPane().add(radField);
		radField.setColumns(10);
		
		JLabel lblRad = new JLabel("Raggio:");
		lblRad.setForeground(Color.WHITE);
		lblRad.setBounds(213, 100, 67, 14);
		frame.getContentPane().add(lblRad);
		
		sideField = new JTextField();
		sideField.setEnabled(false);
		sideField.setBounds(290, 128, 89, 31);
		frame.getContentPane().add(sideField);
		sideField.setColumns(10);
		
		JLabel lblSide = new JLabel("Lato:");
		lblSide.setForeground(Color.WHITE);
		lblSide.setBounds(213, 131, 67, 14);
		frame.getContentPane().add(lblSide);
		
		longField = new JTextField();
		longField.setBounds(235, 159, 89, 31);
		frame.getContentPane().add(longField);
		longField.setColumns(10);
		
		latField = new JTextField();
		latField.setBounds(335, 159, 89, 31);
		frame.getContentPane().add(latField);
		latField.setColumns(10);
		
		JLabel lblCentroid = new JLabel("Longitudine e latitudine centroide:");
		lblCentroid.setForeground(Color.WHITE);
		lblCentroid.setBounds(27, 162, 198, 14);
		frame.getContentPane().add(lblCentroid);
		
		JButton btnSearch = new JButton("Ricerca");
		btnSearch.setBounds(290, 213, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(113, 247, 451, 89);
		frame.getContentPane().add(scrollPane);
		
		JTextArea txtResult = new JTextArea();
		txtResult.setEditable(false);
		scrollPane.setViewportView(txtResult);
		
		JLabel feed1 = new JLabel("");
		feed1.setForeground(Color.RED);
		feed1.setBounds(389, 100, 209, 14);
		frame.getContentPane().add(feed1);
		
		JLabel feed2 = new JLabel("");
		feed2.setForeground(Color.RED);
		feed2.setBounds(389, 131, 209, 14);
		frame.getContentPane().add(feed2);
		
		JLabel feed3 = new JLabel("");
		feed3.setForeground(Color.RED);
		feed3.setBounds(202, 188, 259, 14);
		frame.getContentPane().add(feed3);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search4Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 649, 391);
		frame.getContentPane().add(Image);
		
		cbPer.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String choice = (String) cbPer.getSelectedItem();
				if (choice == "Cerchio")
				{
					radField.setEnabled(true);
					sideField.setEnabled(false);
				} else if(choice == "Quadrato"){
					radField.setEnabled(false);
					sideField.setEnabled(true);
				} else {
					radField.setEnabled(false);
					sideField.setEnabled(false);
				}
			}
		});
		
		radField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String rad = radField.getText();
				try {
					 Integer.parseInt(rad);
					 feed1.setText("");
				} catch (NumberFormatException e1) {
					feed1.setText("Il raggio deve essere un numero!");
				}
			}
		});
		
		sideField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String side = sideField.getText();
				try {
					 Integer.parseInt(side);
					 feed2.setText("");
				} catch (NumberFormatException e1) {
					feed2.setText("Il lato deve essere un numero!");
				}
			}
		});
		
		longField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String lon = longField.getText();
				try {
					 Integer.parseInt(lon);
					 feed3.setText("");
				} catch (NumberFormatException e1) {
					feed3.setText("La longitudine deve essere un numero!");
				}
			}
		});
		
		latField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String lat = latField.getText();
				try {
					 Integer.parseInt(lat);
					 feed3.setText("");
				} catch (NumberFormatException e1) {
					feed3.setText("La latitudine deve essere un numero!");
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String choice = (String) cbPer.getSelectedItem();
				String lonS = longField.getText();
				double lon;
				try {
					lon = Double.parseDouble(lonS);
				} catch(NumberFormatException e1) {
					return;
				}
				String latS = latField.getText();
				double lat;
				try {
					lat = Double.parseDouble(latS);
				} catch(NumberFormatException e1) {
					return;
				}
				Position centroid = new Position(lon,lat);
				if (choice == "Cerchio"){
					String radS = radField.getText();
					double rad;
					try {
						rad = Double.parseDouble(radS);
					} catch(NumberFormatException e1) {
						return;
					}
					ArrayList<Filament> listFilament=null;
					try {
						listFilament = Query.findFilamentCircle(con, rad, centroid);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					int max = listFilament.size();
					if ( max == 0) {
						txtResult.setRows(1);
						txtResult.setText("Nessun risultato!");
					} else {
						txtResult.setRows(max);
						for(int i=0; i<max; i++) {
							txtResult.append(listFilament.get(i).toString()+"\n");
						}
					}
				} else if(choice == "Quadrato"){
					String sideS = sideField.getText();
					double side;
					try {
						side = Double.parseDouble(sideS);
					} catch(NumberFormatException e1) {
						return;
					}
					ArrayList<Filament> listFilament=null;
					try {
						listFilament = Query.findFilamentSquare(con,side, centroid);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					int max = listFilament.size();
					if ( max == 0) {
						txtResult.setRows(1);
						txtResult.setText("Nessun risultato!");
					} else {
						txtResult.setRows(max);
						for(int i=0; i<max; i++) {
							txtResult.append(listFilament.get(i).toString()+"\n");
						}
					}
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

