package controller;

/*
 * Classe Java responsabile della creazione e 
 * gestione della connessione con un database
 * tramite il metodo getConnection()
 */

import java.sql.*;

public class DBHandler {
	private final String dbURI = "jdbc:postgresql://localhost:5432/postgres";
	private final String user = "postgres";
	private final String password = "postgres";

	private Connection con;
	
	public DBHandler() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
	}	
	public Connection getConnection() throws SQLException {
		if (this.con == null || this.con.isClosed()){
			this.con = DriverManager.getConnection(dbURI, user, password);
		}
		return this.con;
	}
}