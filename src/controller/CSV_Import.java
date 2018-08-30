package controller;

import java.sql.*;

public class CSV_Import {
	
	public static int importFilamentData(Connection conn, String sat, String file) {
		long start_time;
		double tot_time;
		Statement st;
		PreparedStatement update;
		
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			update = conn.prepareStatement("UPDATE \"FILAMENT\" " +
											 "SET \"Total_Flux\" = ?, \"Mean_Dens\" = ?, " +
											     "\"Mean_Temp\" = ?, \"Ellipticity\" = ?, \"Contrast\" = ? " +
											 "WHERE \"Id\" = ? and \"Name\" = ?");
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return -1;
		}	
		
		System.out.println("Update started...");
		start_time = System.currentTimeMillis();
		
		/* Importo i dati dal file in una tabella temporanea */
		try {
			String query_create = "CREATE TABLE \"FILAMENT_TEMP\"( " +
								    "\"Id\" int, " +
								    "\"Name\" varchar(30), " +
								    "\"Total_Flux\" float(25), " +
								    "\"Mean_Dens\" float(25), " +
								    "\"Mean_Temp\" float(25), " +
								    "\"Ellipticity\" float(25), " +
								    "\"Contrast\" float(25), " +
								    "\"Satellite\" varchar(15), " +
								    "\"Instrument\" varchar(20), " +
								    "PRIMARY KEY(\"id\", \"name\"))";
			
			String query_copy = "COPY \"FILAMENT_TEMP\" " + 
					       		"FROM '" + file + "' " +
					       		"DELIMITER ',' CSV HEADER";
			
			st.executeUpdate(query_create);
			st.executeUpdate(query_copy);
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return 1;
		}	
		
		try {
			int Filament_Id;
			String Filament_Name;
			double Total_Flux, Mean_Dens,Mean_Temp,Ellipticity,Contrast;
			
			/* aggiorno i filamenti esistenti  presupponendo che non cambino
			 * satellite e strumento associati*/
			ResultSet rs = st.executeQuery("SELECT * " +
										     "FROM \"FILAMENT_TEMP\" " +
										     "WHERE (\"Id\",\"Name\") IN (SELECT \"Id\", \"Name\" " +
																 "FROM \"FILAMENT\")");
			
			while(rs.next()) {
				Filament_Id = rs.getInt("Id");
				Filament_Name = rs.getString("Name");
				Total_Flux = rs.getDouble("Total_Flux");
				Mean_Dens = rs.getDouble("Mean_Dens");
				Mean_Temp = rs.getDouble("Mean_Temp");
				Ellipticity = rs.getDouble("Ellipticity");
				Contrast = rs.getDouble("Contrast");
				
				update.setDouble(1, Total_Flux);
				update.setDouble(2, Mean_Dens);
				update.setDouble(3, Mean_Temp);
				update.setDouble(4, Ellipticity);
				update.setDouble(5, Contrast);
				update.setInt(6, Filament_Id);
				update.setString(7, Filament_Name);
				
				update.executeUpdate();
				
				System.out.println("Updated filament (" + Filament_Id + ", " + Filament_Name + ")");
			}
			
			/* Rimuovo i filamenti gia' presenti nel db dalla tabella temporanea
			 * e lavoro sui nuovi  */
			st.executeUpdate("DELETE FROM \"FILAMENT_TEMP\" " +
							  "WHERE (\"Id\", \"Name\") IN (SELECT \"Id\", \"Name\" " +
												   "FROM \"FILAMENT\")");
			
			/* Inserisco i nuovi filamenti */
			st.executeUpdate("INSERT INTO \"FILAMENT\" " +
							 "SELECT \"Id\", \"Name\", \"Total_Flux\", \"Mean_Dens\", \"Mean_Temp\", \"Ellipticity\", \"Contrast\" " +
							 "FROM \"FILAMENT_TEMP\"");
			
			/* Inserisco filamento-satellite
			 * con il satellite già presente nel db*/
			st.executeUpdate("INSERT INTO \"FIL_SAT\" " +
			                  "SELECT \"FT\".\"Id\", \"FT\".\"Name\", \"S\".\"Id\" " +
			                  "FROM \"FILAMENT_TEMP\" AS \"FT\" join \"SATELLITE\" AS \"S\" ON \"FT\".\"Satellite\" = \"S\".\"Name\"");
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}
		
		/* Elimino la tabella temporanea*/
		try {
			st.executeUpdate("DROP TABLE \"FILAMENT_TEMP\"");
			conn.commit();
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}	
		
		tot_time = (double)(System.currentTimeMillis() - start_time) / 1000;
		
		System.out.println("Update successfully completed! (" + tot_time +"s)");
		
		return 0;
	}

	public static int importStarData(Connection conn, String file) {
		long start_time;
		double tot_time;
		Statement st;
		PreparedStatement update_s;
		
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			update_s = conn.prepareStatement("UPDATE \"STAR\" " +
											   "SET \"Name\" = ?, \"Type\" = ?,\"Flux\" = ?, " +
											         " \"Galactic_Pos\" = (SELECT \"Id\" FROM \"GALACTIC_POSITION\" WHERE \"Galactic_Long\" = ? \"and \"Galactic_Lat\" = ?) " +
											   "WHERE \"Id\" = ?");
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return -1;
		}	
		
		System.out.println("Update started...");
		start_time = System.currentTimeMillis();
		
		/* Importo i dati dal file in una tabella temporanea*/
		try {
			String query_create = "CREATE TABLE \"STAR_TEMP\"( " +
								    "\"Id\" int PRIMARY KEY, " +
								    "\"Name\" varchar(30), " +
								    "\"Galactic_Long\" float(25), " +
								    "\"Galactic_Lat\" float(25), " +
								    "\"Flux\" float(25), " +
								    "\"Type\" varchar(15))";
			
			String query_copy = "COPY \"STAR_TEMP\" " + 
					       		"FROM '" + file + "' " +
					       		"DELIMITER ',' CSV HEADER";
			
			st.executeUpdate(query_create);
			st.executeUpdate(query_copy);
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return 1;
		}	
		try {
			int Star_Id;
			String Star_Name, Type;
			double Flux, Galactic_Long, Galactic_Lat;
			
			/* Inserisco le nuove posizioni galattiche */
			st.executeUpdate("INSERT INTO \"GALACTIC_POSITION\"(\"Galactic_Long\", \"Galactic_Lat\") " +
							 "(SELECT DISTINCT \"Galactic_Long\", \"Galactic_Lat\" " +
							 "FROM \"STAR_TEMP\"" +
							 "except " +
							 "SELECT DISTINCT \"GALACTIC_Long\", \"Galactic_Lat\" " + 
							 "FROM \"STAR_TEMP\" " +
							 "WHERE (\"Galactic_Long\", \"Galactic_Lat\") IN (SELECT \"Galactic_Long\", \"Galactic_Lat\" FROM \"GALACTIC_POSITION\"))");
			
			/* Aggiorno le stelle gia' esistenti */
			ResultSet rs = st.executeQuery("SELECT * " + 
										   "FROM \"STAR_TEMP\" " +
										   "WHERE \"Id\" IN (SELECT \"Id\" " +
												   		"FROM \"STAR\")");
			
			while(rs.next()) {
				Star_Id = rs.getInt("Id");
				Star_Name = rs.getString("Name");
				Flux = rs.getDouble("Flux");
				Galactic_Long = rs.getDouble("Galactic_Long");
				Galactic_Lat = rs.getDouble("Galactic_Lat");
				Type = rs.getString("Type");
				
				update_s.setString(1, Star_Name);
				update_s.setString(2, Type);
				update_s.setDouble(3, Flux);
				update_s.setDouble(4, Galactic_Long);
				update_s.setDouble(5, Galactic_Lat);
				update_s.setInt(6, Star_Id);

				update_s.executeUpdate();
				
				System.out.println("Updated star " + Star_Id);
			}
			
			/* Rimuovo le stelle gia' presenti nel db dalla tabella temporanea
			   per lavorare solo sulle nuove*/
			st.executeUpdate("DELETE FROM \"STAR_TEMP\" " +
							  "WHERE \"Id\" IN (SELECT \"Id\" " +
										   "FROM \"STAR\")");
			
			/* Inserisco nuove stelle */
			st.executeUpdate("INSERT INTO \"STAR\" " +
							  "SELECT \"ST\".\"Id\", \"ST\".\"Name\", \"ST\".\"Type\", \"G\".\"Id\", \"ST\".\"Flux\" " +
							  "FROM \"STAR_TEMP\" AS \"ST\" JOIN \"GALACTIC_POSITION\" AS \"G\" ON (\"ST\".\"Galactic_Long\" = \"G\".\"Galactic_Long\" AND \"ST\".\"Galactic_Lat\" = \"G\".\"Galactic_Lat\")");
			
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}
		
		/* Elimino la tabella temporanea */
		try {
			st.executeUpdate("DROP TABLE \"STAR_TEMP\"");
			conn.commit();
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}	
		
		tot_time = (double)(System.currentTimeMillis() - start_time) / 1000;
		
		System.out.println("Update successfully completed! (" + tot_time +"s)");
		
		return 0;

	}

	public static int importBoundaryData(Connection conn, String sat, String file){
		long start_time;
		double tot_time;
		Statement st;
		
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return -1;
		}
		
		System.out.println("Update started...");
		start_time = System.currentTimeMillis();
		
		/* Importo i dati dal file in una tabella temporanea */
		try {
			String query_create = "CREATE TABLE \"BOUNDARY_TEMP\"( " +
								    "\"Id\" SERIAL PRIMARY KEY, " +
								    "\"Filament_Id\" int, " +
								    "\"Galactic_Long\" float(25), " +
								    "\"Galactic_Lat\" float(25))";
			
			String query_copy = "COPY \"BOUNDARY_TEMP\"(\"Filament_Id\", \"Galactic_Long\", \"Galactic_Lat\") " + 
					       		"FROM '" + file + "' " +
					       		"DELIMITER ',' CSV HEADER";
			
			st.executeUpdate(query_create);
			st.executeUpdate(query_copy);
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return 1;
		}	
		
		try {
			
			/* Inserisco le nuove posizioni galattiche */
			st.executeUpdate("INSERT INTO \"GALACTIC_POSITION\"(\"Galactic_Long\", \"Galactic_Lat\") " +
							 "(SELECT DISTINCT \"Galactic_Long\", \"Galactic_Lat\" " +
							 "FROM \"BOUNDARY_TEMP\" " +
							 "except " +
							 "SELECT DISTINCT \"Galactic_Long\", \"Galactic_Lat\" " + 
							 "FROM \"BOUNDARY_TEMP\" " +
							 "WHERE (\"Galactic_Long\", \"Galactic_Lat\") IN (SELECT \"Galactic_Long\", \"Galactic_Lat\" FROM \"GALACTIC_POSITION\"))");
			
			/* Rimuovo i punti gia' presenti nel db e gli id dei filamenti non esistenti
			in modo da avere a che fare con solo nuovi punti e filamenti esistenti */
			st.executeUpdate("DELETE FROM \"BOUNDARY_TEMP\" " +
							  "WHERE \"Filament_Id\" NOT IN (SELECT \"Id\" " +
									  					"FROM \"FILAMENT\") " +
							  		"OR (\"Filament_Id\", \"Galactic_Long\", \"Galactic_Lat\") IN (SELECT \"B\".\"Filament_Id\", \"G\".\"Galactic_Long\", \"G\".\"Galactic_Lat\" " +
							  											              "FROM \"BOUNDARY\" AS \"B\" JOIN \"GALACTIC_POSITION\" AS \"G\" ON \"B\".\"Galactic_Pos\" = \"G\".\"Id\" " +
							  												                  "JOIN \"FIL_SAT\" AS \"FS\" ON (\"B\".\"Filament_Id\" = \"FS\".\"Filament_Id\" AND \"B\".\"Filament_Name\" = \"FS\".\"Filament_Name\") " + 
							  												                  "JOIN \"SATELLITE\" AS \"S\" ON \"S\".\"Id\" = \"FS\".\"Satellite_Id\" " +
							  											              "WHERE \"S\".\"Name\" = '" + sat + "')");
			
			/* Inserisco i nuovi punti per i filamenti esistenti
			   (non possono esistere filamenti con stesso id e stesso satellite) */
			st.executeUpdate("INSERT INTO \"BOUNDARY\" " +
							  "(SELECT DISTINCT \"BT\".\"Filament_Id\", \"FS\".\"Filament_Name\", \"G\".\"Id\" " +
							  "FROM \"BOUNDARY_TEMP\" AS \"BT\" JOIN \"GALACTIC_POSITION\" AS \"G\" ON (\"BT\".\"Galactic_Long\" = \"G\".\"Galactic_Long\" AND \"BT\".\"Galactic_Lat\" = \"G\".\"Galactic_Lat\") " +
							  		"JOIN \"FIL_SAT\" AS \"FS\" ON \"FS\".\"Filament_Id\" = \"BT\".\"Filament_Id\"  JOIN \"SATELLITE\" AS \"S\" ON \"S\".\"Id\" = \"FS\".\"Satellite_Id\" " +
							  "WHERE \"S\".\"Name\" = '" + sat + "')");
				
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}
		
		/* Elimino la tabella temporanea */
		try {
			st.executeUpdate("DROP TABLE \"BOUNDARY_TEMP\"");
			conn.commit();
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}
		
		tot_time = (double)(System.currentTimeMillis() - start_time) / 1000;
		
		System.out.println("Update successfully completed! (" + tot_time +"s)");
		
		return 0;
	}

	public static int importBranchData(Connection conn, String sat, String file) {
		long start_time;
		double tot_time;
		Statement st;
		PreparedStatement find_position, insert_position, update_branch, update_branch_position;
		
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			find_position = conn.prepareStatement("SELECT * " +
							 				  "FROM \"BRANCH_POSITION\" " + 
							 				  "WHERE \"Branch_Id\" = ? AND \"Galactic_Pos\" = (SELECT \"Id\" " +
							 											    "FROM \"GALACTIC_POSITION\" " +
							 											    "WHERE \"Galactic_Long\" = ? AND \"Galactic_Lat\" = ?)");

			insert_position = conn.prepareStatement("INSERT INTO \"BRANCH_POSITION\" VALUES (?,(SELECT \"Id\" " +
			 									                                         "FROM \"GALACTIC_POSITION\" " +
			 									                                         "WHERE \"Galactic_Long\" = ? AND \"Galactic_Lat\" = ?),?,?)");                         

			update_branch = conn.prepareStatement("UPDATE \"BRANCH\" " +
												 "SET \"Type\" = ?, \"Filament_Id\" = ?, \"Filament_Name\" = ? " +
												 "WHERE \"Id\" = ?");

			update_branch_position = conn.prepareStatement("UPDATE \"BRANCH_POSITION\" " +
													       "SET \"Progressive_Num\" = ?, \"Flux\" = ? " +
													       "WHERE \"Branch_Id\" = ? AND \"Galactic_Pos\" = (SELECT \"Id\" " +
															   						               "FROM \"GALACTIC_POSITION\" " +
															   						               "WHERE \"Galactic_Long\" = ? AND \"Galactic_Lat\" = ?)");

		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return -1;
		}	
		
		System.out.println("Update started...");
		start_time = System.currentTimeMillis();
		
		/* Importo i dati dal file in una tabella temporanea */
		try {
			String query_create = "CREATE TABLE \"BRANCH_TEMP\"( " +
								    "\"Id\" SERIAL PRIMARY KEY, " +
								    "\"Filament_Id\" int, " +
								    "\"Branch_Id\" int, " +
								    "\"Type\" char, " +
								    "\"Galactic_Long\" float(25), " +
								    "\"Galactic_Lat\" float(25), " +
								    "\"Progressive_Num\" int, " +
								    "\"Flux\" float(25))";
			
			String query_copy = "COPY \"BRANCH_TEMP\"(\"Filament_Id\", \"Branch_Id\", \"Type\", \"Galactic_Long\", \"Galactic_Lat\", \"Progressive_Num\", \"Flux\") " + 
					       		"FROM '" + file + "' " +
					       		"DELIMITER ',' CSV HEADER";

			st.executeUpdate(query_create);
			st.executeUpdate(query_copy);
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return 1;
		}	
		
		try {
			int Filament_Id, Branch_Id, Progressive_Num;
			String Type, Filament_Name;
			double Galactic_Long, Galactic_Lat, Flux;
			
			/* Inserisco le nuove posizioni galattiche */
			st.executeUpdate("INSERT INTO \"GALACTIC_POSITION\"(\"Galactic_Long\", \"Galactic_Lat\") " +
							 "(SELECT DISTINCT \"Galactic_Long\", \"Galactic_Lat\" " +
							 "FROM \"BRANCH_TEMP\" " +
							 "except " +
							 "SELECT DISTINCT \"Galactic_Long\", \"Galactic_Lat\" " + 
							 "FROM \"BRANCH_TEMP\" " +
							 "WHERE (\"Galactic_Long\", \"Galactic_Lat\") IN (SELECT \"Galactic_Long\", \"Galactic_Lat\" FROM \"GALACTIC_POSITION\"))");
			
			/* Aggiorno i rami esistenti 
			 * (esistono gia' l'id del ramo e l'id del filamento) */
			ResultSet rs = st.executeQuery("SELECT \"BT\".*, \"F\".\"Name\" " +
										    "FROM \"BRANCH_TEMP\" AS \"BT\" JOIN \"FILAMENT\" AS \"F\" ON \"BT\".\"Filament_Id\" = \"F\".\"Id\" " +
										    	"JOIN \"FIL_SAT\" AS \"FS\" ON (\"F\".\"Id\" = \"FS\".\"Filament_Id\" AND \"F\".\"Name\" = \"FS\".\"Filament_Name\") " + 
										    	"JOIN \"SATELLITE\" AS \"S\" ON \"S\".\"Id\" = \"FS\".\"Satellite_Id\" " +
										    "WHERE \"S\".\"Name\" = '" + sat + "' AND \"BT\".\"Branch_Id\" IN (SELECT \"Id\" " +
												   				                              "FROM \"BRANCH\") " +
												  "AND (\"BT\".\"Filament_Id\", \"F\".\"Name\") IN (SELECT \"Id\", \"Name\" " +
														  						   "FROM \"FILAMENT\")");
			
			while(rs.next()) {
				Filament_Name = rs.getString("Name");
				Filament_Id = rs.getInt("Filament_Id");
				Branch_Id = rs.getInt("Branch_Id");
				Type = rs.getString("Type");
				Progressive_Num = rs.getInt("Progressive_Num");
				Flux = rs.getDouble("Flux");
				Galactic_Long = rs.getDouble("Galactic_Long");
				Galactic_Lat = rs.getDouble("Galactic_Lat");
				 
				update_branch.setString(1, Type);
				update_branch.setInt(2, Filament_Id);
				update_branch.setString(3, Filament_Name);
				update_branch.setInt(4, Branch_Id);
				 
				update_branch.executeUpdate(); 
				
				System.out.println("Updated branch (" + Branch_Id + ")");
				
				find_position.setInt(1, Branch_Id);
				find_position.setDouble(2, Galactic_Long);
				find_position.setDouble(3, Galactic_Lat);
				
				ResultSet rs2 = find_position.executeQuery();
				
				/* Se rs2 è ha qualche elemento, il branch esiste già, quindi devo aggiornarlo
				 * altrimenti devo inserirlo */
				if (rs2.next()) {
					update_branch_position.setInt(1, Progressive_Num);
					update_branch_position.setDouble(2, Flux);
					update_branch_position.setInt(3, Branch_Id);
					update_branch_position.setDouble(4, Galactic_Long);
					update_branch_position.setDouble(5, Galactic_Lat);
					
					update_branch_position.executeUpdate();
					
					System.out.println("Updated branch position (" + Branch_Id + ", " + Galactic_Long + ", " + Galactic_Lat + ")");
				}
				else {
					insert_position.setInt(1, Branch_Id);
					insert_position.setDouble(2, Galactic_Long);
					insert_position.setDouble(3, Galactic_Lat);
					insert_position.setInt(4, Progressive_Num);
					insert_position.setDouble(5, Flux);
					
					insert_position.executeUpdate();
					
					System.out.println("Inserted branch position (" + Branch_Id + ", " + Galactic_Long + ", " + Galactic_Lat + ")");
				}
			}
			
			/* Rimuovo i rami e i punti gia' presenti nel db
			 * e lavoro solo con i nuovi*/
			st.executeUpdate("DELETE FROM \"BRANCH_TEMP\" " +
							  "WHERE \"Branch_Id\" IN (SELECT \"Id\" " +
										   		  "FROM \"BRANCH\")");
			
			/* Inserisco nuovi rami */
			st.executeUpdate("INSERT INTO \"BRANCH\" " +
							  "SELECT DISTINCT \"BT\".\"Branch_Id\", \"BT\".\"Type\", \"BT\".\"Filament_Id\", \"FS\".\"Filament_Name\" " +
							  "FROM \"BRANCH_TEMP\" AS \"BT\" JOIN \"FIL_SAT\" AS \"FS\" ON \"FS\".\"Filament_Id\" = \"BT\".\"Filament_Id\" " +
							  		"JOIN \"SATELLITE\" AS \"S\" ON \"S\".\"Id\" = \"FS\".\"Satellite_Id\" " +
							  "WHERE \"S\".\"Name\" = '" + sat + "'");
			
			/* Inserisco nuovi punti */
			st.executeUpdate("INSERT INTO \"BRANCH_POSITION\" " +
							  "SELECT \"BT\".\"Branch_Id\", \"G\".\"Id\", \"BT\".\"Progressive_Num\", \"BT\".\"Flux\" " +
							  "FROM \"BRANCH_TEMP\" AS \"BT\" JOIN \"GALACTIC_POSITION\" AS \"G\" ON (\"BT\".\"Galactic_Long\" = \"G\".\"Galactic_Long\" AND \"BT\".\"Galactic_Lat\" = \"G\".\"Galactic_Lat\")");		
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}
		
		/*Elimino la tabella temporanea */
		try {
			st.executeUpdate("DROP TABLE \"BRANCH_TEMP\"");
			conn.commit();
			
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
			return -1;
		}
		
		tot_time = (double)(System.currentTimeMillis() - start_time) / 1000;
		
		System.out.println("Update successfully completed! (" + tot_time +"s)");
		
		return 0;
	}
}