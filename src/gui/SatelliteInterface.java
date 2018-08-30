package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import com.toedter.calendar.JDateChooser;

import controller.Query;

public class SatelliteInterface {

	private JFrame frame;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SatelliteInterface window = new SatelliteInterface(con, user_id, type);
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
	public SatelliteInterface(Connection con, String user_id, String type) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 347, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(528, 347, 101, 23);
		frame.getContentPane().add(btnLogout);
		
		JLabel lblTitle = new JLabel("Inserimento nuovo satellite");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitle.setBounds(163, 11, 300, 43);
		frame.getContentPane().add(lblTitle);
		
		nameField = new JTextField();
		nameField.setBounds(240, 114, 154, 30);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblName = new JLabel("Nome satellite:");
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(142, 122, 110, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblDateF = new JLabel("Data prima osservazione:");
		lblDateF.setForeground(Color.WHITE);
		lblDateF.setBounds(106, 163, 124, 14);
		frame.getContentPane().add(lblDateF);
		
		JLabel lblDateL = new JLabel("Data ultima osservazione:");
		lblDateL.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDateL.setForeground(Color.WHITE);
		lblDateL.setBounds(101, 204, 129, 14);
		frame.getContentPane().add(lblDateL);
		
		JLabel feed1 = new JLabel("");
		feed1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		feed1.setForeground(Color.RED);
		feed1.setBounds(404, 114, 238, 14);
		frame.getContentPane().add(feed1);
		
		JLabel feed2 = new JLabel("");
		feed2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		feed2.setForeground(Color.RED);
		feed2.setBounds(411, 163, 218, 14);
		frame.getContentPane().add(feed2);
		
		JLabel feed3 = new JLabel("");
		feed3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		feed3.setForeground(Color.RED);
		feed3.setBounds(403, 204, 226, 14);
		frame.getContentPane().add(feed3);
		
		JButton btnInsert = new JButton("Inserisci Satellite");
		btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnInsert.setBounds(240, 237, 154, 43);
		frame.getContentPane().add(btnInsert);
		
		JDateChooser dcf = new JDateChooser();
		dcf.setBounds(240, 157, 154, 30);
		frame.getContentPane().add(dcf);
		
		JDateChooser dcl = new JDateChooser();
		dcl.setBounds(240, 196, 154, 30);
		frame.getContentPane().add(dcl);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(SatelliteInterface.class.getResource("/gui/Admin.jpg")));
		Image.setBounds(0, 0, 650, 394);
		frame.getContentPane().add(Image);
		
		
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String user = nameField.getText();
				if (user.length() <= 15){
					if (user.length() == 0) {
						feed1.setText("Inserisci il nome!");
					} else {
						feed1.setText("<html><font color = 'green'>Ok</font></html>");
					}
				} else {
					feed1.setText("Deve avere al massimo 15 caratteri!");
				}
			}
		});
		
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				feed1.setText("");
			}
		});
		
		dcf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				feed2.setText("");
			}
		});
		
		dcl.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				feed3.setText("");
			}
		});

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String satellite = nameField.getText();
				if (satellite.length()<=15 && satellite.length() !=0) {
					try {
						Calendar datef = dcf.getCalendar();
						Calendar datel = dcl.getCalendar();
						Calendar today = Calendar.getInstance();
						if (datel.after(datef)) {
							if (datel.before(today)) {
								int day1 = datef.get(Calendar.DAY_OF_MONTH);
								int month1 =datef.get(Calendar.MONTH) + 1;
								int year1 =datef.get(Calendar.YEAR);
								String dateF=null;
								if (month1<10 && day1<10) {
									dateF = year1 + "-0" + month1 + "-0" + day1;
								} else if(month1<10 && day1>10) {
									dateF = year1 + "-0" + month1 + "-" + day1;
								} else if(day1<10 && month1>10){
									dateF = year1 + "-" + month1 + "-0" + day1;
								} else {
									dateF = year1 + "-" + month1 + "-" + day1;
								}
								int day2 = datel.get(Calendar.DAY_OF_MONTH);
								int month2 =datel.get(Calendar.MONTH) + 1;
								int year2 =datel.get(Calendar.YEAR);
								String dateL=null;
								if (month2<10 && day2<10) {
									dateL = year2 + "-0" + month2 + "-0" + day2;
								} else if(month2<10 && day2>10) {
									dateL = year2 + "-0" + month2 + "-" + day2;
								} else if(day2<10 && month2>10){
									dateL = year2 + "-" + month2 + "-0" + day2;
								} else {
									dateL = year2 + "-" + month2 + "-" + day2;
								}
								String satelliteC = satellite.substring(0, 1).toUpperCase() + satellite.substring(1).toLowerCase();
								try {
									Query.insertSatellite(con, satelliteC, dateF, dateL);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							} else {
								feed3.setText("L'ultima osservazione non può essere successiva ad oggi!");
							}
						} else {
							feed3.setText("L'ultima osservazione deve essere dopo la prima!");
						}
						} catch (java.lang.NullPointerException e1) {
							feed2.setText("Data non valida!");
						}
				} else {
					feed1.setText("Nome satellite non valido!");
				}
				
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
		
		btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminInterface AdIn = new AdminInterface(con,user_id,type);
				AdIn.setVisible(true);
				frame.dispose();
				
				
			}
		});
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
}
