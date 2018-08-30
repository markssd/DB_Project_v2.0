package gui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import controller.Query;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class InstrumentInterface {

	private JFrame frame;
	private JTextField nameField;
	private JTextField bandField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstrumentInterface window = new InstrumentInterface(con, user_id, type);
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
	public InstrumentInterface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 347, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(516, 347, 99, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel lblTitle = new JLabel("Inserimento nuovo strumento");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitle.setBounds(152, 11, 338, 38);
		frame.getContentPane().add(lblTitle);
		
		JComboBox<String> cbSat = new JComboBox<String>();
		cbSat.setBounds(263, 111, 118, 33);
		frame.getContentPane().add(cbSat);
		
		JLabel lblSat = new JLabel("Satellite per cui inserire lo strumento:");
		lblSat.setForeground(Color.WHITE);
		lblSat.setBounds(34, 120, 224, 14);
		frame.getContentPane().add(lblSat);
		
		nameField = new JTextField();
		nameField.setBounds(263, 155, 118, 33);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblName = new JLabel("Nome dello strumento:");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(116, 164, 137, 14);
		frame.getContentPane().add(lblName);
		
		JLabel feed1 = new JLabel("");
		feed1.setForeground(Color.RED);
		feed1.setBounds(391, 164, 224, 14);
		frame.getContentPane().add(feed1);
		
		bandField = new JTextField();
		bandField.setBounds(263, 199, 118, 33);
		frame.getContentPane().add(bandField);
		bandField.setColumns(10);
		
		JLabel lblBand = new JLabel("Bande  relative separata da \",\" :");
		lblBand.setForeground(Color.WHITE);
		lblBand.setBounds(66, 208, 192, 14);
		frame.getContentPane().add(lblBand);
		
		JLabel feed2 = new JLabel("");
		feed2.setForeground(Color.RED);
		feed2.setBounds(391, 208, 224, 14);
		frame.getContentPane().add(feed2);
		
		JButton btnInsert = new JButton("Inserisci");
		btnInsert.setBounds(263, 243, 118, 40);
		frame.getContentPane().add(btnInsert);
		
		JLabel feed3 = new JLabel("");
		feed3.setForeground(Color.RED);
		feed3.setBounds(391, 120, 224, 14);
		frame.getContentPane().add(feed3);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(InstrumentInterface.class.getResource("/gui/Admin.jpg")));
		label.setBounds(0, 0, 654, 395);
		frame.getContentPane().add(label);
		
		
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
				AdminInterface AdIn = new AdminInterface(con,user_id,type);
				AdIn.setVisible(true);
				frame.dispose();
				
				
			}
		});
		
		cbSat.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String[] satellitesNames = null;
				try {
					satellitesNames = Query.getSatellitesNames(con);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				cbSat.setModel(new DefaultComboBoxModel<String>(satellitesNames));
			}
		});
		
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name = nameField.getText();
				if (name.length() <= 20 ) {
					if (name.length() !=0) {
						feed1.setText("<html><font color='green'>Ok!</font></html>");
					} else {
						feed1.setText("Inserire il nome");
					}
				} else {
					feed1.setText("Il nome deve avere massimo 20 caratteri!");
				}
			}
		});
		
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbSat.getSelectedIndex()!=0 && cbSat.getSelectedIndex()!=-1) {
					String ins = nameField.getText();
					int satId = cbSat.getSelectedIndex();
					if (ins.length() <= 20 && ins.length() !=0) {
						String bandls = bandField.getText();
						if (bandls.equals("")) {
							feed2.setText("Scegli almeno una banda!");
						} else {
							feed2.setText("");
							String bandL = bandField.getText();
							String bandList[] = bandL.split(",");
							LinkedList<Double> bandDList = new LinkedList<Double>();
							int i = 0;
							while(i < bandList.length) {
								try {
								bandDList.add(Double.parseDouble(bandList[i]));
								} catch(NumberFormatException e1){
									feed2.setText("Formato invalido");
									return;
								}
								i++;
							}
							String INS = ins.toUpperCase();
							try {
								Query.insertInstrument(con, INS, bandDList,satId);
								bandField.setText("");
								nameField.setText("");
								cbSat.setSelectedIndex(0);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					} else {
						feed1.setText("Nome strumento non valido!");
					}
				} else {
					feed3.setText("Scegli il satellite!");
				}
			}
		});
		
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				feed1.setText("");
			}
		});
		
		cbSat.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				feed3.setText("");
			}
		});
		
		bandField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				feed2.setText("");
			}
		});
	}
	
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
}
