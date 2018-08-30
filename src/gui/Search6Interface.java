package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

import controller.Query;
import model.Position;
import model.StarInfo;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class Search6Interface {

	private JFrame frame;
	private JTextField heightField;
	private JTextField baseField;
	private JTextField longField;
	private JTextField latField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search6Interface window = new Search6Interface(con, user_id, type);
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
	public Search6Interface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 347, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(531, 347, 98, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel Title = new JLabel("Stelle in formazione nei filamenti di una regione");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Title.setBounds(125, 11, 377, 51);
		frame.getContentPane().add(Title);
		
		heightField = new JTextField();
		heightField.setBounds(177, 100, 86, 31);
		frame.getContentPane().add(heightField);
		heightField.setColumns(10);
		
		baseField = new JTextField();
		baseField.setBounds(391, 100, 86, 31);
		frame.getContentPane().add(baseField);
		baseField.setColumns(10);
		
		longField = new JTextField();
		longField.setBounds(177, 153, 86, 31);
		frame.getContentPane().add(longField);
		longField.setColumns(10);
		
		latField = new JTextField();
		latField.setBounds(391, 153, 86, 31);
		frame.getContentPane().add(latField);
		latField.setColumns(10);
		
		JLabel lblHeight = new JLabel("Altezza:");
		lblHeight.setForeground(Color.WHITE);
		lblHeight.setBounds(108, 103, 59, 14);
		frame.getContentPane().add(lblHeight);
		
		JLabel lblBase = new JLabel("Base:");
		lblBase.setForeground(Color.WHITE);
		lblBase.setBounds(335, 108, 46, 14);
		frame.getContentPane().add(lblBase);
		
		JLabel lblSub1 = new JLabel("La regione \u00E8 rettangolare, scegliere i dati del rettangolo:");
		lblSub1.setForeground(Color.WHITE);
		lblSub1.setBounds(195, 61, 271, 14);
		frame.getContentPane().add(lblSub1);
		
		JLabel lblSub2 = new JLabel("Centroide:");
		lblSub2.setForeground(Color.WHITE);
		lblSub2.setBounds(10, 161, 75, 14);
		frame.getContentPane().add(lblSub2);
		
		JLabel lblLong = new JLabel("Longitudine:");
		lblLong.setForeground(Color.WHITE);
		lblLong.setBounds(95, 161, 72, 14);
		frame.getContentPane().add(lblLong);
		
		JLabel lblLat = new JLabel("Latitudine:");
		lblLat.setForeground(Color.WHITE);
		lblLat.setBounds(306, 161, 75, 14);
		frame.getContentPane().add(lblLat);
		
		JButton btnSearch = new JButton("Cerca");
		btnSearch.setBounds(276, 193, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(177, 225, 300, 108);
		frame.getContentPane().add(scrollPane);
		
		JTextArea txtResult = new JTextArea();
		txtResult.setEditable(false);
		scrollPane.setViewportView(txtResult);
		
		JLabel feed1 = new JLabel("");
		feed1.setForeground(Color.RED);
		feed1.setBounds(99, 136, 209, 14);
		frame.getContentPane().add(feed1);
		
		JLabel feed2 = new JLabel("");
		feed2.setForeground(Color.RED);
		feed2.setBounds(335, 131, 225, 14);
		frame.getContentPane().add(feed2);
		
		JLabel feed3 = new JLabel("");
		feed3.setForeground(Color.RED);
		feed3.setBounds(95, 184, 168, 14);
		frame.getContentPane().add(feed3);
		
		JLabel feed4 = new JLabel("");
		feed4.setForeground(Color.RED);
		feed4.setBounds(392, 184, 168, 14);
		frame.getContentPane().add(feed4);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search6Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 651, 394);
		frame.getContentPane().add(Image);
		
		heightField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String height = heightField.getText();
				try {
					if (Double.parseDouble(height)>0) {
						feed1.setText("");
					} else {
						feed1.setText("Deve essere un numero positivo!");
					}
				} catch( NumberFormatException e1) {
					feed1.setText("Deve essere un numero positivo!");
				}
			}
		});
		
		baseField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String base = baseField.getText();
				try {
					if (Double.parseDouble(base)>0) {
						feed2.setText("");
					} else {
						feed2.setText("Deve essere un numero positivo!");
					}
				} catch( NumberFormatException e1) {
					feed2.setText("Deve essere un numero positivo!");
				}
			}
		});
		
		longField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String lon = longField.getText();
				try {
					Double.parseDouble(lon);
				} catch( NumberFormatException e1) {
					feed3.setText("Deve essere un numero!");
				}
			}
		});
		
		latField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String lat = latField.getText();
				try {
					Double.parseDouble(lat);
					feed4.setText("");
				} catch( NumberFormatException e1) {
					feed4.setText("Deve essere un numero!");
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				double height,base,lon,lat;
				String heightS = heightField.getText();
				try {
					height=Double.parseDouble(heightS);
					if (height<0){
						return;
					}
				} catch( NumberFormatException e1) {
					return;
				}
				String baseS = baseField.getText();
				try {
					base = Double.parseDouble(baseS);
					if (base<0) {
						return;
					}
				} catch( NumberFormatException e1) {
					return;
				}
				String lonS = longField.getText();
				try {
					lon = Double.parseDouble(lonS);
				} catch( NumberFormatException e1) {
					return;
				}
				String latS = latField.getText();
				try {
					lat = Double.parseDouble(latS);
				} catch( NumberFormatException e1) {
					return;
				}
				Position centroid = new Position(lon,lat);
				try {
					StarInfo si[] = Query.starsInFilament(con, height, base, centroid);
					txtResult.setRows(10);
					txtResult.setLineWrap(true);
					txtResult.setWrapStyleWord(true);
					txtResult.setText("Trovate\n" +
								"Nei filamenti:" + si[0].getOccurrence_star() + " stelle dove: " + si[0].getPercentual_prestellar() + " prestelle, "
								+ si[0].getPercentual_protostellar() + " protostelle, " + si[0].getPercentual_unbound() + " stelle formate\n" +
								"All'esterno: " + si[1].getOccurrence_star() + " stelle dove: " + si[1].getPercentual_prestellar() + " prestelle, "
										+ si[1].getPercentual_protostellar() + " protostelle, " + si[1].getPercentual_unbound() + " stelle formate");
				} catch (SQLException e1) {
					txtResult.setRows(1);
					txtResult.setText("Errore!");
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

