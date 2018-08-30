package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import gui.LoginInterface;
import gui.MainInterface;

public class UserHandler {
	public static boolean login(String user_id, String pass,Connection con) throws SQLException, ClassNotFoundException {
		PreparedStatement statement;
		String query = "select * from \"USERS\" where \"User_Id\"=? ";
		statement = con.prepareStatement(query);
		statement.setString(1, user_id);
		ResultSet result = null;
		try {
			result = statement.executeQuery();
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore di connessione!");
		}
		if (result.next() == false) {
			JOptionPane.showMessageDialog(null, "L'utente non esiste");
			LoginInterface LoginIn=new LoginInterface();
		    LoginIn.setVisible(true);
		    return false;
		}
		PreparedStatement statement2;
		String query2 = "SELECT * FROM \"USERS\" WHERE (\"User_Id\"=? AND \"Password\"=?)";
		statement2 = con.prepareStatement(query2);
		statement2.setString(1, user_id);
		statement2.setString(2, pass);
		ResultSet result2 = null;
		try {
			result2 = statement2.executeQuery();
		} catch(SQLException e1) {
			JOptionPane.showMessageDialog(null, "Errore di connessione");
		}
		if (result2.next() == true) {
			String typeS = result.getString("Type");
			char type = typeS.charAt(0);
			String privilege;
			if (type == 'A') {
				privilege = "Admin";
			} else {
				privilege = "Utente registrato";
			}
			JOptionPane.showMessageDialog(null, "Sei connesso al database come "+ privilege+"!");
			MainInterface MainIn=new MainInterface(con,user_id,typeS);
		    MainIn.setVisible(true);
		    return true;
		} else {
			JOptionPane.showMessageDialog(null, "Password errata!");
			LoginInterface LoginIn=new LoginInterface();
		    LoginIn.setVisible(true);
		    return false;
		}
	}
	
	public static boolean register(String name, String surname, String user_id,
			String password,String e_mail, String type, Connection con) throws SQLException {
		//user id e mail devono essere diversi
		Statement query= con.createStatement();
		ResultSet result = query.executeQuery("SELECT \"User_Id\", \"E_mail\" FROM \"USERS\" ");
		while (result.next()) {
			String user_idC = result.getString("User_Id");
			String e_mailC = result.getString("E_mail");
			if (user_id.equals(user_idC)) {
				JOptionPane.showMessageDialog(null, "Username già utilizzato");
				return false;
			} else {
				// DO NOTHING
			}
			if (e_mail.equals(e_mailC)) {
				JOptionPane.showMessageDialog(null, "E-mail già utilizzata");
				return false;
			} else {
				// DO NOTHING
			}
		}
		PreparedStatement statement;
		String insert = "INSERT INTO \"USERS\" (\"User_Id\", \"Password\", \"E_mail\", \"Name\", \"Surname\", \"Type\")"
				+ " values (?,?,?,?,?,?)";
		statement = con.prepareStatement(insert);
		statement.setString(1, user_id);
		statement.setString(2, password);
		statement.setString(3, e_mail);
		statement.setString(4, name);
		statement.setString(5, surname);
		statement.setString(6, type);
		statement.executeUpdate();
		JOptionPane.showMessageDialog(null, "User successfully registered!");
		return true;
	}
}

