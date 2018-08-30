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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

import controller.Query;
import javax.swing.ImageIcon;

public class Search1Interface {

	private JFrame frame;
	private JTextField idField;
	private JTextField nameField;
	private JTextField resultField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search1Interface window = new Search1Interface(con, user_id, type);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Search1Interface(Connection con, String user_id, String type) throws SQLException {
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
		
		JLabel Title = new JLabel("Ricerca informazioni filamenti");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 22));
		Title.setBounds(176, 11, 300, 51);
		frame.getContentPane().add(Title);
		
		JComboBox<String> cbFil = new JComboBox<String>();
		cbFil.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Id", "Nome"}));
		cbFil.setBounds(297, 73, 98, 20);
		frame.getContentPane().add(cbFil);
		
		JLabel lblFil = new JLabel("Ricerca filamento tramite:");
		lblFil.setForeground(Color.WHITE);
		lblFil.setBounds(129, 73, 180, 14);
		frame.getContentPane().add(lblFil);
		
		JComboBox<String> cbSat = new JComboBox<String>();
		cbSat.setEnabled(false);
		cbSat.setBounds(297, 104, 98, 20);
		frame.getContentPane().add(cbSat);
		String[] satellitesNames = Query.getSatellitesNames(con);
		cbSat.setModel(new DefaultComboBoxModel<String>(satellitesNames));
		
		JLabel lblSat = new JLabel("Scelta satellite corrispondente all'id:");
		lblSat.setForeground(Color.WHITE);
		lblSat.setBounds(70, 107, 217, 14);
		frame.getContentPane().add(lblSat);
		
		idField = new JTextField();
		idField.setEnabled(false);
		idField.setBounds(297, 135, 98, 23);
		frame.getContentPane().add(idField);
		idField.setColumns(10);
		
		JLabel lblId = new JLabel("Inserire l'id cercato:");
		lblId.setForeground(Color.WHITE);
		lblId.setBounds(162, 138, 136, 14);
		frame.getContentPane().add(lblId);
		
		nameField = new JTextField();
		nameField.setEnabled(false);
		nameField.setBounds(297, 166, 98, 23);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblInserireIlNome = new JLabel("Inserire il nome cercato:");
		lblInserireIlNome.setForeground(Color.WHITE);
		lblInserireIlNome.setBounds(132, 169, 166, 14);
		frame.getContentPane().add(lblInserireIlNome);
		
		JComboBox<String> cbChoose = new JComboBox<String>();
		cbChoose.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Posizione centroide", "Estensione", "Numero segmenti"}));
		cbChoose.setBounds(297, 197, 98, 20);
		frame.getContentPane().add(cbChoose);
		
		JLabel lblInformazioneDaRicercare = new JLabel("Informazione da ricercare:");
		lblInformazioneDaRicercare.setForeground(Color.WHITE);
		lblInformazioneDaRicercare.setBounds(132, 200, 166, 14);
		frame.getContentPane().add(lblInformazioneDaRicercare);
		
		JButton btnSearch = new JButton("Cerca");
		btnSearch.setBounds(297, 228, 98, 23);
		frame.getContentPane().add(btnSearch);
		
		resultField = new JTextField();
		resultField.setEditable(false);
		resultField.setBounds(109, 285, 471, 28);
		frame.getContentPane().add(resultField);
		resultField.setColumns(10);
		
		JLabel lblResult = new JLabel("Risultato:");
		lblResult.setForeground(Color.WHITE);
		lblResult.setBounds(297, 260, 98, 14);
		frame.getContentPane().add(lblResult);
		
		JLabel feedback = new JLabel("");
		feedback.setForeground(Color.RED);
		feedback.setBounds(415, 141, 214, 14);
		frame.getContentPane().add(feedback);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search1Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 650, 392);
		frame.getContentPane().add(Image);
		
		cbFil.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String choice = (String) cbFil.getSelectedItem();
				if (choice == "Id")
				{
					cbSat.setEnabled(true);
					idField.setEnabled(true);
					nameField.setEnabled(false);
				} else if(choice == "Nome"){
					cbSat.setEnabled(false);
					idField.setEnabled(false);
					nameField.setEnabled(true);
				} else {
					cbSat.setEnabled(false);
					idField.setEnabled(false);
					nameField.setEnabled(false);
				}
			}
		});
		
		idField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String idS= idField.getText();
				try {
					Integer.parseInt(idS);
				} catch (NumberFormatException e1) {
					feedback.setText("L'id deve essere un numero");
				}
			}
		});
		
		idField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained (FocusEvent e) {
				feedback.setText("");
			}
		});
		
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String choice = (String) cbFil.getSelectedItem();
				if (choice == "Id") {
					String sat = (String) cbSat.getSelectedItem();
					String idS= idField.getText();
					int id;
					try {
						id = Integer.parseInt(idS);
					} catch (NumberFormatException e1) {
						feedback.setText("L'id deve essere un numero");
						return;
					}
					String search = (String) cbChoose.getSelectedItem();
					if (search == "Posizione centroide") {
						try {
							model.Position centroid = Query.getCentroid(con, id,sat);
							if (centroid.getG_lon() == 0 && centroid.getG_lat() == 0) {
								resultField.setText("Non esiste un filamento con quell' id per quel satelite!");
							}else {
								resultField.setText(centroid.toString());
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else if(search == "Estensione") {
						try {
							double[] extension = Query.getBoundaryExtension(con, id, sat);
							if (extension[0] == 0 && extension[1] == 0) {
								resultField.setText("Non esiste un filamento con quell' id per quel satelite!");
							}else {
								resultField.setText("Long:"+Double.toString(extension[0])+"  Lat:"+Double.toString(extension[1]));
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else if (search == "Numero segmenti") {
						int num;
						try {
							num = Query.countBranch(con, id, sat);
							if (num == 0) {
								resultField.setText("Non esiste un filamento con quell' id per quel satelite!");
							}else {
								resultField.setText(Integer.toString(num));
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
							resultField.setText("Scegli l' informazione da trovare");
					}
				} else if (choice == "Name") {
					String name = nameField.getText();
					String search = (String) cbChoose.getSelectedItem();
					if (search == "Posizione centroide") {
						try {
							model.Position centroid = Query.getCentroid(con, name);
							if (centroid.getG_lon() == 0 && centroid.getG_lat() == 0) {
								resultField.setText("Non esiste un filamento con quel nome!");
							}else {
								resultField.setText(centroid.toString());
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else if(search == "Estensione") {
						try {
							double[] extension = Query.getBoundaryExtension(con, name);
							if (extension[0] == 0 && extension[1] == 0) {
								resultField.setText("Non esiste un filamento con quel nome!");
							}else {
								resultField.setText("Long:"+Double.toString(extension[0])+"  Lat:"+Double.toString(extension[1]));
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else if (search == "Numero segmenti") {
						int num;
						try {
							num = Query.countBranch(con, name);
							if (num == 0) {
								resultField.setText("Non esiste un filamento con quell' id per quel satelite!");
							}else {
								resultField.setText(Integer.toString(num));
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
							resultField.setText("Scegli l' informazione da trovare");
					}
					
				} else {
					resultField.setText("Scegli il criterio di ricerca");
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
