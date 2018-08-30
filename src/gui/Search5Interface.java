package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import model.StarInfo;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class Search5Interface {

	private JFrame frame;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search5Interface window = new Search5Interface(con, user_id, type);
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
	public Search5Interface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 347, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(532, 347, 97, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel Title = new JLabel("Ricerca stelle in un filamento");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 22));
		Title.setBounds(173, 11, 291, 50);
		frame.getContentPane().add(Title);
		
		nameField = new JTextField();
		nameField.setBounds(205, 84, 227, 36);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblName = new JLabel("Nome filamento:");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(100, 88, 129, 14);
		frame.getContentPane().add(lblName);
		
		JButton btnSearch = new JButton("Cerca");
		btnSearch.setBounds(275, 131, 89, 32);
		frame.getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(205, 208, 227, 91);
		frame.getContentPane().add(scrollPane);
		
		JTextArea txtResult = new JTextArea();
		txtResult.setEditable(false);
		scrollPane.setViewportView(txtResult);
		
		JLabel lblStelleTrovate = new JLabel("Informazioni trovate:");
		lblStelleTrovate.setForeground(Color.WHITE);
		lblStelleTrovate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStelleTrovate.setBounds(253, 174, 135, 23);
		frame.getContentPane().add(lblStelleTrovate);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search5Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 649, 394);
		frame.getContentPane().add(Image);
		
		btnSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				if (name != "") {
					StarInfo s=new StarInfo();
					try {
						s = Query.infoStarsInFilament(con,name);
						int num =s.getOccurrence_star();
						if (num == 0) {
							txtResult.setText("Nessuna stella trovata!");
						} else {
								txtResult.setText("Trovate " + s.getOccurrence_star() + " stelle dove:\n" + s.getPercentual_prestellar() + "% prestelle,\n"
										+ s.getPercentual_protostellar() + "% protostelle,\n" + s.getPercentual_unbound() + "% stelle formate");
						}
					} catch (SQLException e1) {
						txtResult.setText("Filamento non trovato!");
					}
				} else {
					txtResult.setText("Choose a filament!");
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

