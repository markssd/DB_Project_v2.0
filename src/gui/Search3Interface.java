package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import model.Filament;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.ImageIcon;

public class Search3Interface {

	private JFrame frame;
	private JTextField minField;
	private JTextField maxField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search3Interface window = new Search3Interface(con, user_id, type);
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
	public Search3Interface(Connection con, String user_id, String type) {
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
		
		JLabel Title = new JLabel("Ricerca filamenti per numero di segmenti");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Title.setBounds(158, 11, 342, 38);
		frame.getContentPane().add(Title);
		
		minField = new JTextField();
		minField.setBounds(189, 70, 86, 31);
		frame.getContentPane().add(minField);
		minField.setColumns(10);
		
		JLabel lblMin = new JLabel("Numero minimo di segmenti:");
		lblMin.setForeground(Color.WHITE);
		lblMin.setBounds(22, 73, 169, 14);
		frame.getContentPane().add(lblMin);
		
		JLabel lblMax = new JLabel("Numero massimo di segmenti:");
		lblMax.setForeground(Color.WHITE);
		lblMax.setBounds(285, 73, 180, 14);
		frame.getContentPane().add(lblMax);
		
		maxField = new JTextField();
		maxField.setBounds(466, 70, 86, 31);
		frame.getContentPane().add(maxField);
		maxField.setColumns(10);
		
		JLabel feed1 = new JLabel("");
		feed1.setForeground(Color.RED);
		feed1.setBounds(113, 102, 242, 14);
		frame.getContentPane().add(feed1);
		
		JLabel feed2 = new JLabel("");
		feed2.setForeground(Color.RED);
		feed2.setBounds(412, 102, 166, 14);
		frame.getContentPane().add(feed2);
		
		JButton btnSearch = new JButton("Cerca");
		btnSearch.setBounds(266, 127, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 186, 560, 133);
		frame.getContentPane().add(scrollPane);
		
		JTextArea txtResult = new JTextArea();
		txtResult.setEditable(false);
		txtResult.setRows(20);
		txtResult.setLineWrap(true);
		txtResult.setWrapStyleWord(true);
		scrollPane.setViewportView(txtResult);
		
		JSlider slider = new JSlider();
		slider.setEnabled(false);
		slider.setSnapToTicks(true);
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setBounds(215, 330, 200, 26);
		frame.getContentPane().add(slider);
		
		JLabel feedbackRes = new JLabel("");
		feedbackRes.setForeground(Color.WHITE);
		feedbackRes.setBounds(40, 161, 560, 14);
		frame.getContentPane().add(feedbackRes);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search3Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 649, 391);
		frame.getContentPane().add(Image);
		
		minField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String minS = minField.getText();
				int min;
				try {
					min = Integer.parseInt(minS);
					if(min>2) {
						feed1.setText("");
					} else {
						feed1.setText("Deve essere un intero maggiore di 2!");
					}
				} catch (NumberFormatException e1) {
					feed1.setText("Deve essere un intero maggiore di 2!");
				}
			}
		});
		
		maxField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String max = maxField.getText();
				try {
					Integer.parseInt(max);
					feed2.setText("");
				} catch (NumberFormatException e1) {
					feed2.setText("Deve essere un intero!");
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setValue(0);
				String minS = minField.getText();
				int min;
				try {
					min = Integer.parseInt(minS);
					if(min<=2) {
						return;
					}
				} catch (NumberFormatException e1) {
					return;
				}
				String maxS = maxField.getText();
				int max;
				try {
					max = Integer.parseInt(maxS);
				} catch (NumberFormatException e1) {
					return;
				}
				if (min < max) {
						ArrayList<Filament> resultList = null;
						ArrayList<Filament> resultListP = null;
						int resultCount = 0;
						try {
							resultCount = Query.countFilament(con);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						try {
							resultListP = Query.searchFilamentByRange(con,min,max);
							resultList = Query.searchFilamentByRange(con,min,max,20,0);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						int i=0;
						feedbackRes.setText("Trovati " + resultListP.size() + " filamenti su un totale di:" +resultCount);
						String result2 = "";
						int pages = resultListP.size()/20;
						slider.setMaximum(pages);
						slider.setMajorTickSpacing(1);
						slider.setEnabled(true);
						while (i!=(resultList.size())) {
							Filament f = (Filament) resultList.get(i);
							result2 = result2 + f.getName() + "\n";
							i=i+1;
						}
						if (i==0) {
							result2 = result2 + "Nessun risultato";
						}
						txtResult.setText(result2);
						slider.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								int x=slider.getValue();
								ArrayList<Filament> resultList2= null;
								try {
									resultList2 = Query.searchFilamentByRange(con, min, max, 20, x*20);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								int i=0;
								String result2 = "";
								while (i!=(resultList2.size())) {
									Filament f = resultList2.get(i);
									result2 = result2 + f.getName() + "\n";
									i=i+1;
								}
								if (i==0) {
									result2 = result2 + "Nessun risultato";
								}
								txtResult.setText(result2);
							}
						}); 
					} else {
						feedbackRes.setText("<html><font color ='red'>Il minimo deve essere minore del massimo</font></html>");
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
