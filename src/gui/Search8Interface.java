package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import model.Star;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class Search8Interface {

	private JFrame frame;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search8Interface window = new Search8Interface(con, user_id, type);
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
	public Search8Interface(Connection con, String user_id, String type) {
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
		
		JLabel Title = new JLabel("Posizione delle stelle in un filamento");
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Title.setBounds(167, 11, 291, 38);
		frame.getContentPane().add(Title);
		
		nameField = new JTextField();
		nameField.setBounds(137, 60, 182, 35);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblName = new JLabel("Nome del filamento:");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(21, 70, 121, 14);
		frame.getContentPane().add(lblName);
		
		JButton btnSearch = new JButton("Cerca");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSearch.setBounds(278, 109, 89, 38);
		frame.getContentPane().add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 183, 619, 126);
		frame.getContentPane().add(scrollPane);
		
		JTextArea txtResult = new JTextArea();
		txtResult.setEditable(false);
		txtResult.setRows(20);
		txtResult.setLineWrap(true);
		txtResult.setWrapStyleWord(true);
		scrollPane.setViewportView(txtResult);
		
		JLabel feedbackRes = new JLabel("");
		feedbackRes.setFont(new Font("Tahoma", Font.PLAIN, 9));
		feedbackRes.setForeground(Color.WHITE);
		feedbackRes.setBounds(10, 158, 619, 14);
		frame.getContentPane().add(feedbackRes);
		
		JSlider slider = new JSlider();
		slider.setEnabled(false);
		slider.setSnapToTicks(true);
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setBounds(234, 320, 200, 26);
		frame.getContentPane().add(slider);
		
		JComboBox<String> cbOrder = new JComboBox<String>();
		cbOrder.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Distanza", "Flusso"}));
		cbOrder.setBounds(491, 60, 106, 35);
		frame.getContentPane().add(cbOrder);
		
		JLabel lblOrder = new JLabel("Ordinamento rispetto:");
		lblOrder.setForeground(Color.WHITE);
		lblOrder.setBounds(352, 70, 141, 14);
		frame.getContentPane().add(lblOrder);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(Search8Interface.class.getResource("/gui/Normal.jpeg")));
		Image.setBounds(0, 0, 651, 391);
		frame.getContentPane().add(Image);

		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slider.setValue(0);
				if (cbOrder.getSelectedIndex()!=0 && cbOrder.getSelectedIndex()!=-1) {
						String name = (String) nameField.getText();
						String order= (String) cbOrder.getSelectedItem();
						ArrayList<Star> resultList = null;
						ArrayList<Star> resultList1 = null;
						try {
							resultList = Query.distanceStarsToMainBranch(con,name);
						} catch (SQLException e2) {
							feedbackRes.setText("<html><font color ='red'>Errore!</font></html>");
						}
						int fullsize = resultList.size();
						ArrayList<Star> resultListOrd = Query.orderStars(resultList, order);
						resultList1 = Query.viewStarsLimit(resultListOrd, 20, 0);
						int i=0;
						feedbackRes.setText("La distanza minima dallo scheletro per tutte le stelle in " + name + "ordinate in base alla/al "
								+order + "in ordine crescente è:");
						String result2 = "";
						int pages = fullsize/20;
						slider.setMaximum(pages);
						slider.setMajorTickSpacing(1);
						slider.setEnabled(true);
						while (i!=(resultList1.size())) {
							Star s = (Star) resultList1.get(i);
							result2 = result2 + s.getName() +" distanza: "+s.getDistanceBranch()+" flusso"+s.getFlux()+"\n";
							i=i+1;
						}
						if (i==0) {
							result2 = "Nessun risultato!";
						}
						txtResult.setText(result2);
						slider.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								int x=slider.getValue();
								ArrayList<Star> resultList2= null;
								resultList2 = Query.viewStarsLimit(resultListOrd, 20, x*20);
								int i=0;
								String result2 = "";
								while (i!=(resultList2.size())) {
									Star s = (Star) resultList2.get(i);
									result2 = result2 + s.getName() +" distanza: "+s.getDistanceBranch()+" flusso"+s.getFlux()+"\n";
									i=i+1;
								}
								if (i==0) {
									result2 = "Nessun risultato!";
								}
								txtResult.setText(result2);
							}
						}); 
					} else {
						feedbackRes.setText("<html><font color ='red'>Scegli un ordinamento!</font></html>");
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
