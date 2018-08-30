package gui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
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

public class Search2Interface {

	private JFrame frame;
	private JTextField ellAField;
	private JTextField brillField;
	private JTextField ellBField;
	private JLabel lblBrill;
	private JLabel lblEllA;
	private JLabel lblEllB;
	private JLabel feed1;
	private JLabel feed2;
	private JLabel feed3;
	private JLabel feedbackRes;
	private JLabel Image;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search2Interface window = new Search2Interface(con, user_id, type);
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
	public Search2Interface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 347, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLogout.setBounds(529, 347, 100, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel Title = new JLabel("Ricerca filamenti per brillanza e ellitticit\u00E0");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Title.setBounds(147, 11, 379, 31);
		frame.getContentPane().add(Title);
		
		ellAField = new JTextField();
		ellAField.setBounds(280, 85, 86, 31);
		frame.getContentPane().add(ellAField);
		ellAField.setColumns(10);
		
		brillField = new JTextField();
		brillField.setBounds(84, 85, 86, 31);
		frame.getContentPane().add(brillField);
		brillField.setColumns(10);
		
		ellBField = new JTextField();
		ellBField.setBounds(493, 85, 86, 31);
		frame.getContentPane().add(ellBField);
		ellBField.setColumns(10);
		
		lblBrill = new JLabel("Brillanza:");
		lblBrill.setForeground(Color.WHITE);
		lblBrill.setBounds(10, 88, 64, 14);
		frame.getContentPane().add(lblBrill);
		
		lblEllA = new JLabel("Ellitticit\u00E0 minima:");
		lblEllA.setForeground(Color.WHITE);
		lblEllA.setBounds(192, 88, 100, 14);
		frame.getContentPane().add(lblEllA);
		
		lblEllB = new JLabel("Ellitticit\u00E0 massima:");
		lblEllB.setForeground(Color.WHITE);
		lblEllB.setBounds(394, 88, 114, 14);
		frame.getContentPane().add(lblEllB);
		
		JButton btnSearch = new JButton("Cerca");
		btnSearch.setBounds(280, 137, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 196, 495, 131);
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
		slider.setBounds(223, 338, 200, 20);
		frame.getContentPane().add(slider);
		
		feed1 = new JLabel("");
		feed1.setForeground(Color.RED);
		feed1.setBounds(10, 60, 183, 14);
		frame.getContentPane().add(feed1);
		
		feed2 = new JLabel("");
		feed2.setForeground(Color.RED);
		feed2.setBounds(203, 63, 203, 14);
		frame.getContentPane().add(feed2);
		
		feed3 = new JLabel("");
		feed3.setForeground(Color.RED);
		feed3.setBounds(416, 63, 213, 14);
		frame.getContentPane().add(feed3);
		
		feedbackRes = new JLabel("");
		feedbackRes.setForeground(Color.WHITE);
		feedbackRes.setBounds(84, 171, 495, 14);
		frame.getContentPane().add(feedbackRes);
		
		Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search2Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 650, 394);
		frame.getContentPane().add(Image);
		
		brillField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String brillS = brillField.getText();
				double brill;
				try {
					brill = Double.parseDouble(brillS);
					if (brill <0) {
						feed1.setText("Deve essere positiva");
					} else {
						feed1.setText("");
					}
				} catch (NumberFormatException e1) {
					feed1.setText("Deve essere un numero positivo!");
				}
			}
		});
		
		ellAField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String ellAS = ellAField.getText();
				double ellA;
				try {
					ellA = Double.parseDouble(ellAS);
					if (ellA <1 || ellA>10) {
						feed2.setText("Deve essere tra 1 e 10");
					} else {
						feed2.setText("");
					}
				} catch (NumberFormatException e1) {
					feed2.setText("Deve essere un numero tra 1 e 10!");
				}
			}
		});
		
		ellBField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost (FocusEvent e) {
				String ellBS = ellBField.getText();
				double ellB;
				try {
					ellB = Double.parseDouble(ellBS);
					if (ellB <1 || ellB>10) {
						feed3.setText("Deve essere tra 1 e 10");
					} else {
						feed3.setText("");
					}
				} catch (NumberFormatException e1) {
					feed3.setText("Deve essere un numero tra 1 e 10!");
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setValue(0);
				String brillS=brillField.getText();
				double brill;
				try {
					brill = Double.parseDouble(brillS);
					if (brill <0) {
						return;
					}
				} catch (NumberFormatException e1) {
					return;
				}
				String ellAS= ellAField.getText();
				double ellA;
				try {
					ellA = Double.parseDouble(ellAS);
					if (ellA <1 || ellA>10) {
						return;
					}
				} catch (NumberFormatException e1) {
					return;
				}
				String ellBS=(String) ellBField.getText();
				double ellB;
				try {
					ellB = Double.parseDouble(ellBS);
					if (ellB <1 || ellB>10) {
						return;
					}
				} catch (NumberFormatException e1) {
					return;
				}
				if (ellA<=ellB) {
					ArrayList<Filament> resultList = null;
					ArrayList<Filament> resultListP = null;
					int resultCount = 0;
					try {
						resultCount = Query.countFilament(con);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						resultListP = Query.searchFilamentByContrastEllipticity(con,brill,ellA,ellB);
						resultList = Query.searchFilamentByContrastEllipticity(con,brill,ellA,ellB,20,0);
					} catch (SQLException e1) {
							e1.printStackTrace();
					}
					feedbackRes.setText("Trovati: " + resultListP.size() + " filamenti su un totale di "+resultCount);
					String result2 = "";
					int pages = resultListP.size()/20;
					slider.setMaximum(pages);
					slider.setMajorTickSpacing(1);
					slider.setEnabled(true);
					int i=0;
					while (i!=(resultList.size())) {
							Filament f = (Filament) resultList.get(i);
							result2 = result2 + f.getName() + "\n";
							i=i+1;
							}
					if (i==0) {
						result2 = "Nessun risultato";
					}
					txtResult.setText(result2);
					slider.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							int x=slider.getValue();
							ArrayList<Filament> resultList2= null;
							try {
								resultList2 = Query.searchFilamentByContrastEllipticity(con,brill,ellA,ellB,20,x*20);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							int i=0;
							String result2= "";
							while (i!=(resultList2.size())) {
									Filament f = (Filament) resultList2.get(i);
									result2 = result2 + f.getName() + "\n";
									i=i+1;
							}
							if (i==0) {
							result2 = result2 + "Nessun risultato!";
							}
							txtResult.setText(result2);
						}
					}); 
				} else {
					feedbackRes.setText("Il minimo deve essere minore del massimo!");
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

