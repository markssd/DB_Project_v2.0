package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.CSV_Import;
import controller.Query;

import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;

public class ImportInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(Connection con, String user_id, String type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportInterface window = new ImportInterface(con, user_id, type);
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
	public ImportInterface(Connection con, String user_id, String type) throws SQLException {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 645, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Indietro");
		btnBack.setBounds(10, 337, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JButton btnLogout = new JButton("Disconnettiti");
		btnLogout.setBounds(519, 337, 100, 23);
		frame.getContentPane().add(btnLogout);
		
		JButton btnImport = new JButton("Importa File");
		btnImport.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnImport.setBounds(245, 269, 117, 45);
		frame.getContentPane().add(btnImport);
		
		JComboBox<String> cbType = new JComboBox<String>();
		cbType.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Filamento", "Stella", "Contorni", "Rami"}));
		cbType.setBounds(259, 79, 89, 35);
		frame.getContentPane().add(cbType);
		
		JLabel lblType = new JLabel("Scegli il tipo di dati da importare");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblType.setForeground(Color.WHITE);
		lblType.setBounds(68, 89, 181, 14);
		frame.getContentPane().add(lblType);
		
		String[] satellitesNames = Query.getSatellitesNames(con);
		JComboBox<String> cbSat = new JComboBox<String>();
		cbSat.setEnabled(false);
		cbSat.setModel(new DefaultComboBoxModel<String>(satellitesNames));
		cbSat.setBounds(259, 134, 89, 35);
		frame.getContentPane().add(cbSat);

		
		JLabel lblSat = new JLabel("Scegli il satellite di riferimento");
		lblSat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSat.setForeground(Color.WHITE);
		lblSat.setBounds(68, 144, 181, 14);
		frame.getContentPane().add(lblSat);
		
		JLabel lblImportaNuoviDati = new JLabel("Importa nuovi dati");
		lblImportaNuoviDati.setForeground(Color.WHITE);
		lblImportaNuoviDati.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblImportaNuoviDati.setBounds(193, 22, 223, 27);
		frame.getContentPane().add(lblImportaNuoviDati);
		
		JButton btnChoose = new JButton("Scegli il file");
		btnChoose.setBounds(259, 188, 89, 35);
		frame.getContentPane().add(btnChoose);
		
		JLabel lblChoose = new JLabel("Scegli il file csv da importare");
		lblChoose.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblChoose.setForeground(Color.WHITE);
		lblChoose.setBounds(68, 198, 181, 14);
		frame.getContentPane().add(lblChoose);
		
		JLabel feedback = new JLabel("");
		feedback.setForeground(Color.RED);
		feedback.setBounds(203, 60, 300, 14);
		frame.getContentPane().add(feedback);
		
		JLabel absPath = new JLabel("");
		absPath.setBounds(164, 244, 325, 14);
		frame.getContentPane().add(absPath);
		
		JLabel feedback2 = new JLabel("");
		feedback2.setForeground(Color.RED);
		feedback2.setBounds(358, 144, 223, 14);
		frame.getContentPane().add(feedback2);
		
		JLabel Image = new JLabel("");
		Image.setIcon(new ImageIcon(ImportInterface.class.getResource("/gui/Admin.jpg")));
		Image.setBounds(0, 0, 650, 394);
		frame.getContentPane().add(Image);
		
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				feedback2.setText("");
				final JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        ".csv files", "csv");
				chooser.setFileFilter(filter);
	            int returnVal = chooser.showOpenDialog(frame);
	            if(returnVal == JFileChooser.APPROVE_OPTION){
	                File file = chooser.getSelectedFile();
	                feedback2.setText("<html><font color = 'green'>Hai selezionato il file: " + file.getName() + "</font></html>");
	                absPath.setText(file.getAbsolutePath());
	            }else if(returnVal == JFileChooser.CANCEL_OPTION){
	                feedback2.setText("Operazione cancellata!");
	            }else if(returnVal == JFileChooser.ERROR_OPTION){
	                feedback2.setText("Errore!");
	            }else{
	                feedback2.setText("Errore sconosciuto!");
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
		
		cbType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String typeC = (String) cbType.getSelectedItem();
				if (typeC != "Stella" && typeC != "")
				{
					cbSat.setEnabled(true);
				}
			}
		});
		
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
				int feed = 0;
				String typeC =(String) cbType.getSelectedItem();
				String sat = "";
				if (typeC != "Stella" && typeC != "") {
					sat = (String) cbSat.getSelectedItem();
				}
				String myPath = absPath.getText();
				switch(typeC) {
				case "":
					feedback.setText("Selezionare un tipo di file da importare!");
				case "Filamento":
					feed=CSV_Import.importFilamentData(con, sat, myPath);
				case "Stella":
					feed=CSV_Import.importStarData(con, myPath);
				case "Contorni":
					feed=CSV_Import.importBoundaryData(con, sat, myPath);
				case "Rami":
					feed=CSV_Import.importBranchData(con, sat, myPath);
				}
				if (feed == 1) {
					feedback.setText("I campi non corrispondono, cambia file!");
					cbType.setSelectedItem(0);
					cbSat.setEnabled(false);
				} else if(feed == -1){
					feedback.setText("Query errata, contatta i gestori del database!");
				} else if (feed == 0) {
					feedback.setText("<html><font color ='green'>Import avvenuto con successo!</font></html>");
					cbType.setSelectedItem(0);
					cbSat.setEnabled(false);
				}
				}
		});
		
		
		cbSat.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				feedback.setText("");
			}
		});
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
}
