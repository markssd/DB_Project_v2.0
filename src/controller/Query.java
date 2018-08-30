package controller;

import model.Position;
import model.Star;
import model.StarInfo;
import model.DistanceComparator;
import model.Filament;
import model.FluxComparator;
import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;
 
public class Query {
    //Query 5.1
    public static Position getCentroid(Connection con, int id, String satellite) throws SQLException {
        Position centroid = new Position();
        PreparedStatement statement;
        ResultSet result;
        String query_id;
 
        double lat = 0, lon = 0;
         
        query_id = "SELECT avg(\"Galactic_Long\") as \"Mean_Long\", avg(\"Galactic_Lat\") as \"Mean_Lat\""
                + " FROM \"GALACTIC_POSITION\" AS \"G\" JOIN \"BOUNDARY\" AS \"B\" on \"G\".\"Id\"=\"B\".\"Galactic_Pos\" JOIN \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Name\"=\"F\".\"Name\" JOIN"
                + " \"FIL_SAT\" AS \"FS\" on \"FS\".\"Filament_Name\"=\"F\".\"Name\" JOIN \"SATELLITE\" AS \"S\" on \"FS\".\"Satellite_Id\" = \"S\".\"Id\""
                + " WHERE \"FS\".\"Filament_Id\" = ? AND \"S\".\"Name\" = ?;";
         
        statement = con.prepareStatement(query_id);
         
        statement.setInt(1, id);
        statement.setString(2, satellite);
 
        result = statement.executeQuery();
        if(result.next()) {
            lon += result.getDouble("Mean_Long");
            lat += result.getDouble("Mean_Lat");
         
        }
        centroid.setG_lon(lon);
        centroid.setG_lat(lat);
        
        result.close();
        statement.close();
 
        return centroid;
    }
    public static Position getCentroid(Connection con, String name) throws SQLException{
        Position centroid = new Position();
        PreparedStatement statement;
        ResultSet result;
        String query_id;
 
        double lat = 0, lon = 0;
         
        query_id = "SELECT avg(\"Galactic_Long\") as \"Mean_Long\", avg(\"Galactic_Lat\") as \"Mean_Lat\""
                + " FROM \"GALACTIC_POSITION\" AS \"G\" JOIN \"BOUNDARY\" AS \"B\" on \"G\".\"Id\"=\"B\".\"Galactic_Pos\" JOIN \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Id\"=\"F\".\"Id\" AND \"B\".\"Filament_Name\"=\"F\".\"Name\" "
                + " WHERE \"F\".\"Name\"= ?";
        statement = con.prepareStatement(query_id);
         
        statement.setString(1, name);
         
        result = statement.executeQuery();
        if(result.next()) {
            lon = result.getDouble("Mean_Long");
            lat = result.getDouble("Mean_Lat");        
        }
        centroid.setG_lon(lon);
        centroid.setG_lat(lat);

        result.close();
        statement.close();
         
        return centroid;
    }
  
    //Query 5.2
    public static double[] getBoundaryExtension(Connection con, int id, String satellite) throws SQLException {
		/* L'estensione del contorno, calcolata come la distanza tra il minimo e il massimo delle
		 * longitudini e la distanza tra il minimo e massimo delle latitudini
		 */
		double[] extension = new double[2];
		PreparedStatement statement;
		ResultSet result;
		String query;
		double min_lat, min_lon, max_lat, max_lon;
		min_lat = min_lon = max_lat = max_lon = 0;
		
		query = "SELECT min(\"Galactic_Long\"), min(\"Galactic_Lat\"), max(\"Galactic_Long\"), max(\"Galactic_Lat\")"
				+ " FROM \"GALACTIC_POSITION\" AS \"G\" JOIN \"BOUNDARY\" AS \"B\" on \"G\".\"Id\"=\"B\".\"Galactic_Pos\" JOIN"
				+ " \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Name\"=\"F\".\"Name\" JOIN \"FIL_SAT\" AS \"FS\" on \"FS\".\"Filament_Name\"=\"F\".\"Name\" JOIN"
				+ " \"SATELLITE\" \"S\" on \"FS\".\"Satellite_Id\"=\"S\".\"Id\""
				+ " WHERE \"F\".\"Id\"= ? AND \"S\".\"Name\"= ?;";
		
		statement = con.prepareStatement(query);
		
		statement.setInt(1, id);
		statement.setString(2, satellite);

		result = statement.executeQuery();
		
		if(result.next()) {
			min_lon = result.getDouble(1);
			min_lat = result.getDouble(2);
			max_lon = result.getDouble(3);
			max_lat = result.getDouble(4);
			
		}
		extension[0] = Math.abs(max_lon - min_lon);
		extension[1] = Math.abs(max_lat - min_lat);
		
		result.close();
		statement.close();
		
		return extension;
	}
	public static double[] getBoundaryExtension(Connection con, String name) throws SQLException {
		/* L'estensione del contorno, calcolata come la distanza tra il minimo e il massimo delle
		 * longitudini e la distanza tra il minimo e massimo delle latitudini
		 */
		double[] extension = new double[2]; 
		PreparedStatement statement;
		ResultSet result;
		String query;
		double min_lat, min_lon, max_lat, max_lon;
		min_lat = min_lon = max_lat = max_lon = 0;
		
		query = "SELECT min(\"Galactic_Long\"), min(\"Galactic_Lat\"), max(\"Galactic_Long\"), max(\"Galactic_Lat\")"
				+ " FROM \"GALACTIC_POSITION\" AS \"G\" JOIN \"BOUNDARY\" AS \"B\" on \"G\".\"Id\"=\"B\".\"Galactic_Pos\" JOIN"
				+ " \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Name\"=\"F\".\"Name\" JOIN \"FIL_SAT\" AS \"FS\" on \"FS\".\"Filament_Name\"=\"F\".\"Name\" JOIN"
				+ " \"SATELLITE\" AS \"S\" on \"FS\".\"Satellite_Id\"=\"S\".\"Id\""
				+ " WHERE \"F\".\"Name\"= ?;";
		
		statement = con.prepareStatement(query);
		
		statement.setString(1, name);

		result = statement.executeQuery();
		
		if(result.next()) {
			min_lon = result.getDouble(1);
			min_lat = result.getDouble(2);
			max_lon = result.getDouble(3);
			max_lat = result.getDouble(4);
			
		}
		extension[0] = Math.abs(max_lon - min_lon);
		extension[1] = Math.abs(max_lat - min_lat);
		
		result.close();
		statement.close();
		
		return extension;
	}
    
    //Query 5.3
    public static int countBranch(Connection con, int id, String satellite) throws SQLException {
        PreparedStatement statement;
        ResultSet result;
        String query;
        int count_branch = 0;
 
        query = "SELECT count(\"B\".\"Id\")"
                + " FROM \"BRANCH\" AS \"B\" JOIN \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Name\"=\"F\".\"Name\" JOIN \"FIL_SAT\" AS \"FS\" on \"FS\".\"Filament_Name\"=\"F\".\"Name\" "
                + " JOIN \"SATELLITE\" \"S\" on \"FS\".\"Satellite_Id\"=\"S\".\"Id\""
                + " WHERE \"F\".\"Id\" = ? AND \"S\".\"Name\" = ?";
         
        statement = con.prepareStatement(query);
         
        statement.setInt(1, id);
        statement.setString(2, satellite);
 
        result = statement.executeQuery();
 
        if(result.next()) {
            count_branch = result.getInt(1);
        }
         
        return count_branch;
    }
    public static int countBranch(Connection con, String name) throws SQLException {
        PreparedStatement statement;
        ResultSet result;
        String query;
        int count_branch = 0;
 
        query = "SELECT count(\"B\".\"Id\")"
                + " FROM \"BRANCH\" AS \"B\" JOIN \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Name\"=\"F\".\"Name\""
                + " WHERE \"F\".\"Name\" = ?";
         
        statement = con.prepareStatement(query);
     
        statement.setString(1, name);
 
        result = statement.executeQuery();
 
        if(result.next()) {
            count_branch = result.getInt(1);
        }
         
        return count_branch;
    }
     
    //Query 6
    public static int countFilament(Connection con) throws SQLException {
        PreparedStatement statement;
        ResultSet result;
        String query;
        int count_total = 0;
             
        query = "SELECT count(*)"
                + " FROM \"FILAMENT\";";
         
        statement = con.prepareStatement(query);
        result = statement.executeQuery();
         
        if(result.next()) {
            count_total = result.getInt(1);
        }
         
        return count_total;
    }
    public static ArrayList<Filament> searchFilamentByContrastEllipticity(Connection con, double brilliance, double ellptc_A, double ellptc_B) throws SQLException {
        //il controllo sull'intervallo dell'ellitticità e della brillantezza è effettuato
        //nella view a livello di interfaccia
        PreparedStatement statement;
        ResultSet result;
        String query;
        double contrast;
        ArrayList<Filament> linked_filam = new ArrayList<Filament>();
 
        contrast = (brilliance / 100) + 1;
 
        query = "SELECT *"
                + " FROM \"FILAMENT\""
                + " WHERE \"Contrast\" > ? AND \"Ellipticity\" between ? and ?;";
         
        statement = con.prepareStatement(query);
 
        statement.setDouble(1, contrast);
        statement.setDouble(2, ellptc_A);
        statement.setDouble(3, ellptc_B);
 
        result = statement.executeQuery();
 
        while(result.next()) {
            Filament fil = new Filament();
            fil.setId(result.getInt("Id"));
            fil.setName(result.getString("Name"));
            fil.setTotal_flux(result.getDouble("Total_Flux"));
            fil.setMean_dens(result.getDouble("Mean_Dens"));
            fil.setMean_temp(result.getDouble("Mean_Temp"));
            fil.setEllipticity(result.getDouble("Ellipticity"));
            fil.setContrast(result.getDouble("Contrast"));
            linked_filam.add(fil);
        }
        result.close();
        statement.close();
                 
        return linked_filam;
    }
    public static ArrayList<Filament> searchFilamentByContrastEllipticity(Connection con, double brilliance, double ellptc_A, double ellptc_B, int limit, int offset) throws SQLException {
        // offset per visualizzazione grafica
        PreparedStatement statement;
        ResultSet result;
        String query;
        double contrast;
        ArrayList<Filament> linked_filam = new ArrayList<Filament>();
 
        contrast = (brilliance / 100) + 1;
 
        query = "SELECT *"
                + " FROM \"FILAMENT\""
                + " WHERE \"Contrast\" > ? AND \"Ellipticity\" between ? and ?"
                + " LIMIT ? OFFSET ?";
         
        statement = con.prepareStatement(query);
 
        statement.setDouble(1, contrast);
        statement.setDouble(2, ellptc_A);
        statement.setDouble(3, ellptc_B);
        statement.setInt(4, limit);
        statement.setInt(5, offset);
 
        result = statement.executeQuery();
 
        while(result.next()) {
            Filament fil = new Filament();
            fil.setId(result.getInt("id"));
            fil.setName(result.getString("name"));
            fil.setTotal_flux(result.getDouble("total_flux"));
            fil.setMean_dens(result.getDouble("mean_dens"));
            fil.setMean_temp(result.getDouble("mean_temp"));
            fil.setEllipticity(result.getDouble("ellipticity"));
            fil.setContrast(result.getDouble("contrast"));
            linked_filam.add(fil);
        }
        result.close();
        statement.close();
                 
        return linked_filam;
    }
    
    //Query 7
    public static ArrayList<Filament> searchFilamentByRange(Connection con, int min_range, int max_range,int limit, int offset) throws SQLException { 
        PreparedStatement statement;
        ResultSet result;
        String query;
        ArrayList<Filament> linked_filam = new ArrayList<Filament>();
         
        query = "SELECT \"F\".\"Id\", \"F\".\"Name\", \"F\".\"Total_Flux\", \"F\".\"Mean_Dens\", \"F\".\"Mean_Temp\", \"F\".\"Ellipticity\", \"F\".\"Contrast\" "
        		+ " FROM \"BRANCH\" AS \"B\" JOIN \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Id\"=\"F\".\"Id\" AND \"B\".\"Filament_Name\"=\"F\".\"Name\""
        		+ " GROUP BY (\"F\".\"Id\", \"F\".\"Name\") HAVING count(\"B\".\"Id\") >= ? AND count(\"B\".\"Id\") <= ?"
        		+ " LIMIT ? OFFSET ?";
         
        statement = con.prepareStatement(query);
        statement.setDouble(1, min_range);
        statement.setDouble(2, max_range);
        statement.setInt(3, limit);
        statement.setInt(4, offset);
         
        result = statement.executeQuery();
         
        while(result.next()) {
            Filament fil = new Filament();
            fil.setId(result.getInt("Id"));
            fil.setName(result.getString("Name"));
            fil.setTotal_flux(result.getDouble("Total_Flux"));
            fil.setMean_dens(result.getDouble("Mean_Dens"));
            fil.setMean_temp(result.getDouble("Mean_Temp"));
            fil.setEllipticity(result.getDouble("Ellipticity"));
            fil.setContrast(result.getDouble("Contrast"));
            linked_filam.add(fil);
        }
         
        result.close();
        statement.close();
         
        return linked_filam;    
    }
    public static ArrayList<Filament> searchFilamentByRange(Connection con, int min_range, int max_range) throws SQLException {  
        PreparedStatement statement;
        ResultSet result;
        String query;
        ArrayList<Filament> linked_filam = new ArrayList<Filament>();
         
        query = "SELECT \"F\".\"Id\", \"F\".\"Name\", \"F\".\"Total_Flux\", \"F\".\"Mean_Dens\", \"F\".\"Mean_Temp\", \"F\".\"Ellipticity\", \"F\".\"Contrast\" " + 
                "FROM \"BRANCH\" AS \"B\" JOIN \"FILAMENT\" AS \"F\" on \"B\".\"Filament_Id\"=\"F\".\"Id\" AND \"B\".\"Filament_Name\"=\"F\".\"Name\"" + 
                "GROUP BY (\"F\".\"Id\", \"F\".\"Name\") HAVING count(\"B\".\"Id\") >= ? AND count(\"B\".\"Id\") <= ?";
         
        statement = con.prepareStatement(query);
        statement.setDouble(1, min_range);
        statement.setDouble(2, max_range);
         
        result = statement.executeQuery();
         
        while(result.next()) {
            Filament fil = new Filament();
            fil.setId(result.getInt("Id"));
            fil.setName(result.getString("Name"));
            fil.setTotal_flux(result.getDouble("Total_Flux"));
            fil.setMean_dens(result.getDouble("Mean_Dens"));
            fil.setMean_temp(result.getDouble("Mean_Temp"));
            fil.setEllipticity(result.getDouble("Ellipticity"));
            fil.setContrast(result.getDouble("Contrast"));
            linked_filam.add(fil);
        }
         
        result.close();
        statement.close();
         
        return linked_filam;    
    }
         
    //Query 8
  	public static ArrayList<Filament> findFilamentCircle(Connection conn, double radius, Position centroid) throws SQLException {
  		ArrayList<Filament> list_filament = new ArrayList<Filament>();
  		PreparedStatement st = conn.prepareStatement("SELECT \"Id\", \"Name\" " +
  													 "FROM \"FILAMENT\" AS \"F\" " +
  													 "WHERE NOT EXISTS (SELECT * " +
  															   		   "FROM \"BOUNDARY\" AS \"B\" JOIN \"GALACTIC_POSITION\" AS \"G\" on \"B\".\"Galactic_Pos\" = \"G\".\"Id\" " +
  															   		   "WHERE sqrt(power(\"G\".\"Galactic_Long\" - ?, 2) + power(\"G\".\"Galactic_Lat\" - ?, 2)) > ? " + 
  															   			  "AND \"F\".\"Id\" =\"B\".\"Filament_Id\" AND \"F\".\"Name\" = \"B\".\"Filament_Name\")");
  		
  		st.setDouble(1, centroid.getG_lon());
  		st.setDouble(2, centroid.getG_lat());
  		st.setDouble(3, radius);
  		
  		ResultSet rs = st.executeQuery();
  		
  		while (rs.next()) {
  			Filament fil = new Filament();
  			int id = rs.getInt("Id");
  			String name = rs.getString("Name");
  			
  			fil.setId(id);
  			fil.setName(name);
  			list_filament.add(fil);
  		}
  		return list_filament;
  	}
  	public static ArrayList<Filament> findFilamentSquare(Connection conn, double side, Position centroid) throws SQLException {
  		ArrayList<Filament> list_filament = new ArrayList<Filament>();
  		PreparedStatement st = conn.prepareStatement("SELECT \"Id\", \"Name\" " +
  													 "FROM \"FILAMENT\" AS \"F\" " +
  													 "WHERE NOT EXISTS (SELECT * " +
  															   		   "FROM \"BOUNDARY\" AS \"B\" JOIN \"GALACTIC_POSITION\" AS \"G\" on \"B\".\"Galactic_Pos\" =\"G\".\"Id\" " +
  															   		   "WHERE (\"G\".\"Galactic_Long\" > ? OR \"G\".\"Galactic_Long\" < ? OR \"G\".\"Galactic_Lat\" < ? OR \"G\".\"Galactic_Lat\" > ?) " +
  															   			  "AND \"F\".\"Id\" = \"B\".\"Filament_Id\" AND \"F\".\"Name\" = \"B\".\"Filament_Name\")");
  		
  		st.setDouble(1, centroid.getG_lon() + side / 2);
  		st.setDouble(2, centroid.getG_lon() - side / 2);
  		st.setDouble(3, centroid.getG_lat() - side / 2);
  		st.setDouble(4, centroid.getG_lat() + side / 2);

  		
  		ResultSet rs = st.executeQuery();
  		
  		while (rs.next()) {
  			Filament fil = new Filament();
  			int id = rs.getInt("id");
  			String name = rs.getString("name");
  			
  			fil.setId(id);
  			fil.setName(name);
  			list_filament.add(fil);
  		}
  		return list_filament;
  	}

    //Query 9
    public static int countBoundary(Connection con, String name) throws SQLException {
        PreparedStatement statement_count;
        ResultSet result_count;
        String query_count;
        int count = 0;
         
        query_count = "SELECT count(*)"
                + " FROM \"BOUNDARY\" AS \"B\" JOIN \"FILAMENT\" AS \"F\" ON \"B\".\"Filament_Name\"=\"F\".\"Name\""
                + " WHERE \"F\".\"Name\"= ? ;";
        statement_count = con.prepareStatement(query_count);
        statement_count.setString(1, name);
        result_count = statement_count.executeQuery();
        if(result_count.next()) {
            count = result_count.getInt(1);
        }
        return count;       
    }
    public static ArrayList<Position> getBoundary(Connection con, String name) throws SQLException {
        ArrayList<Position> bound = new ArrayList<Position>();
        PreparedStatement statement_bound;
        ResultSet result_bound;
        String query_bound;
         
        query_bound = "SELECT \"Galactic_Long\", \"Galactic_Lat\" "
                + " FROM \"GALACTIC_POSITION\" AS \"G\" JOIN \"BOUNDARY\" AS \"B\" ON \"G\".\"Id\"=\"B\".\"Galactic_Pos\" JOIN \"FILAMENT\" AS \"F\" ON \"F\".\"Name\"=\"B\".\"Filament_Name\""
                + " WHERE \"F\".\"Name\"= ?;";
        statement_bound = con.prepareStatement(query_bound);
        statement_bound.setString(1, name);
        result_bound = statement_bound.executeQuery();
        while(result_bound.next()) {
            Position b = new Position();
            b.setG_lon(result_bound.getDouble("Galactic_Long"));
            b.setG_lat(result_bound.getDouble("Galactic_Lat"));
            bound.add(b);
        }
        // Inserisco il punto iniziale alla fine per chiudere il contorno
        bound.add(bound.get(0));
        return bound;
    }
    public static ArrayList<Star> getStarsInFilament(Connection con, String name) throws SQLException {
        PreparedStatement statement_star;
        ResultSet result_star;
        String query_star;
        ArrayList<Star> star = new ArrayList<Star>();
        ArrayList<Position> bound = new ArrayList<Position>();
        
        bound = getBoundary(con, name);
         
        query_star = "SELECT \"S\".\"Id\" AS \"Star_Id\", \"S\".\"Name\" AS \"Star_Name\", \"S\".\"Type\", \"S\".\"Flux\", \"G\".\"Galactic_Long\", \"G\".\"Galactic_Lat\""
                + " FROM \"STAR\" AS \"S\" JOIN \"GALACTIC_POSITION\" AS \"G\" ON \"S\".\"Galactic_Pos\"=\"G\".\"Id\";";
                 
        statement_star = con.prepareStatement(query_star);
        result_star = statement_star.executeQuery();
        
        while(result_star.next()) {
        	Star s = new Star();
            s.setG_lon(result_star.getDouble("Galactic_Long"));  //St_L
            s.setG_lat(result_star.getDouble("Galactic_Lat")); //St_B
            
            if (isIn(con, s, bound)) {
                // La stella è interna al filamento
                s.setId(result_star.getInt("Star_Id"));
                s.setName(result_star.getString("Star_Name"));
                s.setType(result_star.getString("Type"));
                s.setFlux(result_star.getDouble("Flux"));
                star.add(s);
            }
        }
        return star;
    }
    public static StarInfo infoStarsInFilament(Connection con, String name) throws SQLException {
        ArrayList<Star> star = new ArrayList<Star>();
        star = getStarsInFilament(con, name);
        int total_unbound = 0, total_protostellar = 0, total_prestellar = 0;
        int i, total_star;
         
        total_star = star.size();
        for(i = 0; i < total_star; i++) {
            Star s = (Star) star.get(i);
            switch(s.getType()) {
            case "PROTOSTELLAR" :
                total_protostellar++;
                break;
            case "UNBOUND" : 
                total_unbound++;
                break;
            case "PRESTELLAR" :
                total_prestellar++;
                break;
            }
        }
        StarInfo info = new StarInfo();
        info.setOccurrence_star(total_star);
        info.setPercentual_protostellar((float)100*total_protostellar/total_star);
        info.setPercentual_unbound((float)100*total_unbound/total_star);
        info.setPercentual_prestellar((float)100*total_prestellar/total_star);
        return info;
    }
    public static boolean isIn(Connection conn, Star s, ArrayList<Position> bound) throws SQLException {
        double lon_star, lat_star, sum = 0, formule;
        double C_Li, C_Bi, C_Lj, C_Bj;
        int i = 0, count_bound = 0;

        count_bound = bound.size();

        lon_star = s.getG_lon();  //St_L
        lat_star = s.getG_lat(); //St_B

        while(i < count_bound - 1) {
            Position bound_i = (Position) bound.get(i); 
            Position bound_j = (Position) bound.get(i+1);
            C_Li = bound_i.getG_lon();
            C_Lj = bound_j.getG_lon();
            C_Bi = bound_i.getG_lat();
            C_Bj = bound_j.getG_lat();
            formule = ((C_Li - lon_star)*(C_Bj - lat_star)-(C_Bi - lat_star)*(C_Lj - lon_star))/((C_Li - lon_star)*(C_Lj - lon_star)+(C_Bi - lat_star)*(C_Bj - lat_star));
            sum += Math.atan(formule);
            ++i;
        }
        sum = Math.abs(sum);

	    if (sum >= 0.01) {
	    //La stella è interna al filamento
	    	return true;
	    }

	    return false;
  	}
    
    //Query 10
  	public static ArrayList<Star> getStarsInRectangle(Connection conn, double height,
  			double basis, Position centroid) throws SQLException {
  		ArrayList<Star> list_star = new ArrayList<Star>();
  		PreparedStatement st = conn.prepareStatement("SELECT * " +
  													 "FROM \"STAR\" AS \"S\" JOIN \"GALACTIC_POSITION\" AS \"G\" on \"S\".\"Galactic_Pos\" = \"G\".\"Id\" " +
  													 "WHERE (\"G\".\"Galactic_Long\" BETWEEN ? AND ?) AND (\"G\".\"Galactic_Lat\" BETWEEN ? AND ?)");
  		
  		st.setDouble(1, centroid.getG_lon() - basis / 2);
  		st.setDouble(2, centroid.getG_lon() + basis / 2);
  		st.setDouble(3, centroid.getG_lat() - height / 2);
  		st.setDouble(4, centroid.getG_lat() + height / 2);

  		
  		ResultSet rs = st.executeQuery();
  		while (rs.next()) {
  			Star s = new Star();

  			String type = rs.getString("Type");
  			double g_lon = rs.getDouble("Galactic_Long");
  			double g_lat = rs.getDouble("Galactic_Lat");
  			
  			s.setG_lon(g_lon);
  			s.setG_lat(g_lat);
  			s.setType(type);
  			
  			list_star.add(s);
  		}
  		return list_star;
  	}
  	public static ArrayList<Filament> getFilamentsInRectangle(Connection conn, double height, double basis, Position centroid) throws SQLException {
  		ArrayList<Filament> list_filament = new ArrayList<Filament>();
  		PreparedStatement st = conn.prepareStatement("SELECT \"F\".\"Name\" " +
													 "FROM \"FILAMENT\" AS \"F\" " +
													 "WHERE EXISTS (SELECT * " +
															   	   "FROM \"BOUNDARY\" AS \"B\" JOIN \"GALACTIC_POSITION\" AS \"G\" on \"B\".\"Galactic_Pos\" = \"G\".\"Id\" " +
															   	   "WHERE (\"G\".\"Galactic_Long\" BETWEEN ? AND ?) AND (\"G\".\"Galactic_Lat\" BETWEEN ? AND ?) " +
															   	   		"AND \"F\".\"Name\" = \"B\".\"Filament_Name\")");
  		
  		st.setDouble(1, centroid.getG_lon() - basis / 2);
  		st.setDouble(2, centroid.getG_lon() + basis / 2);
  		st.setDouble(3, centroid.getG_lat() - height / 2);
  		st.setDouble(4, centroid.getG_lat() + height / 2);

  		ResultSet rs = st.executeQuery();
  		
  		while (rs.next()) {
  			String name = rs.getString("Name");
  			
  			Filament f = new Filament();
  			f.setName(name);
  			
  			list_filament.add(f);
  		}
  		return list_filament;
  	} 	
  	public static StarInfo[] starsInFilament(Connection conn, double height, double basis, Position centroid) throws SQLException {
  		ArrayList<Star> stars;
  		ArrayList<Filament> filament;
  		StarInfo si[] = {new StarInfo(), new StarInfo()}; 
  		//Nel primo elemento i dati delle stelle interne, nel secondo delle esterne
  		
  		int unbound_in = 0, protostellar_in = 0, prestellar_in = 0, star_in = 0, total_filament = 0,
  				unbound_ext = 0, protostellar_ext = 0, prestellar_ext = 0, star_ext = 0, i = 0;
  		
  		stars = getStarsInRectangle(conn, height, basis, centroid);
  		filament = getFilamentsInRectangle(conn, height, basis, centroid);
  		total_filament = filament.size();
  		
  		System.out.println("NUM FILAMENT: " + total_filament +
  						   "\nNUM STARS: " + stars.size());
  		
  		@SuppressWarnings("unchecked")
		ArrayList<Position>[] bound = new ArrayList[total_filament];
  		
  		
  		for(Star s:stars) {
  			i = 0;
  			for(Filament f:filament) {
  			
  				if (bound[i] == null)
  					bound[i] = getBoundary(conn, f.getName());
  				
	  			if(isIn(conn, s, bound[i])) {
	  	            switch(s.getType()) {
	  	            	case "PROTOSTELLAR" :
	  	            		protostellar_in++;
	  	            		break;
	  	            	case "UNBOUND" : 
	  	            		unbound_in++;
	  	            		break;
	  	            	case "PRESTELLAR" :
	  	            		prestellar_in++;
	  	            		break;
	  	            }
	  	            star_in++;
	  				break;
	  			}
	  			
  	            i++;
  			}
  			if (i == total_filament) {
  	            switch(s.getType()) {
	            	case "PROTOSTELLAR" :
	            		protostellar_ext++;
	            		break;
	            	case "UNBOUND" : 
	            		unbound_ext++;
	            		break;
	            	case "PRESTELLAR" :
	            		prestellar_ext++;
	            		break;
	            }
  	            star_ext++;
  			}
  		}
  		
        si[0].setOccurrence_star(star_in);
        si[0].setPercentual_protostellar((float)100*protostellar_in/star_in);
        si[0].setPercentual_unbound((float)100*unbound_in/star_in);
        si[0].setPercentual_prestellar((float)100*prestellar_in/star_in);
        
        si[1].setOccurrence_star(star_ext);
        si[1].setPercentual_protostellar((float)100*protostellar_ext/star_ext);
        si[1].setPercentual_unbound((float)100*unbound_ext/star_ext);
        si[1].setPercentual_prestellar((float)100*prestellar_ext/star_ext);
        
  		return si;
  	}
    
    //Query 11
    public static int getMinVertix(Connection con, int branch_id) throws SQLException {
        PreparedStatement statement;
        ResultSet result;
        String query;
        int min = 0;
         
        query = "SELECT min(\"Progressive_Num\") AS \"min\""
                + " FROM \"BRANCH_POSITION\""
                + " WHERE \"Branch_Id\" = ?;";
        statement = con.prepareStatement(query);
        statement.setInt(1, branch_id);
        result = statement.executeQuery();
        if(result.next()) {
            min = result.getInt("min");
        }
        return min;
    }
    public static int getMaxVertix(Connection con, int branch_id) throws SQLException {
        PreparedStatement statement;
        ResultSet result;
        String query;
        int max = 0;
         
        query = "SELECT max(\"Progressive_Num\") AS \"max\""
                + " FROM \"BRANCH_POSITION\""
                + " WHERE \"Branch_Id\" = ?;";
        statement = con.prepareStatement(query);
        statement.setInt(1, branch_id);
        result = statement.executeQuery();
        if(result.next()) {
            max = result.getInt("max");
        }
        return max;
    }
    public static Position getPosition(Connection con, int branch_id, int prog_num) throws SQLException {
        PreparedStatement statement;
        ResultSet result;       
        String query;
        Position pos = new Position();
 
        query = "SELECT \"Galactic_Long\", \"Galactic_Lat\""
                + " FROM \"GALACTIC_POSITION\" JOIN \"BRANCH_POSITION\" ON \"Id\"=\"Galactic_Pos\" "
                + " WHERE \"Branch_Id\" = ? AND \"Progressive_Num\" = ?;";
         
        statement = con.prepareStatement(query);
        statement.setInt(1, branch_id);
        statement.setInt(2, prog_num);
        result = statement.executeQuery();
        if(result.next()) {
            pos.setG_lon(result.getDouble("Galactic_Long"));
            pos.setG_lat(result.getDouble("Galactic_Lat"));
        }
        return pos;
    }
    public static ArrayList<Double> distanceVertix(Connection con, int branch_id) throws SQLException {
        PreparedStatement statement_boundary;
        ResultSet result_boundary;      
        String query;
        int min, max;
        double boundary_lon, boundary_lat, distance_min, distance_max;
        double distance_min_final = Double.MAX_VALUE, distance_max_final = Double.MAX_VALUE; //Distanze piu grandi possibili
        ArrayList<Double> l = new ArrayList<Double>();
        
        min = getMinVertix(con, branch_id);
        max = getMaxVertix(con, branch_id);
 
        Position pos_min = getPosition(con, branch_id, min);
        Position pos_max = getPosition(con, branch_id, max);
         
        query = "SELECT \"G\".\"Galactic_Long\", \"G\".\"Galactic_Lat\""
                + " FROM \"GALACTIC_POSITION\" AS \"G\" JOIN \"BOUNDARY\" AS \"B\" ON \"B\".\"Galactic_Pos\"=\"G\".\"Id\" JOIN \"BRANCH\" AS \"R\" ON \"B\".\"Filament_Name\"=\"R\".\"Filament_Name\""
                + " WHERE \"R\".\"Id\" = ?;";
        statement_boundary = con.prepareStatement(query);
        statement_boundary.setInt(1, branch_id);
        result_boundary = statement_boundary.executeQuery();
        
        while(result_boundary.next()) {
            boundary_lon = result_boundary.getDouble("Galactic_Long");
            boundary_lat = result_boundary.getDouble("Galactic_Lat");
            
            distance_min = Math.sqrt(Math.pow(pos_min.getG_lon() - boundary_lon, 2) + Math.pow(pos_min.getG_lat() - boundary_lat, 2));
            distance_max = Math.sqrt(Math.pow(pos_max.getG_lon() - boundary_lon, 2) + Math.pow(pos_max.getG_lat() - boundary_lat, 2));
             
            if(distance_min < distance_min_final) {
                distance_min_final = distance_min;
            }
            if(distance_max < distance_max_final) {
                distance_max_final = distance_max;
            }
        }
        //Inserisco i valori finali in una lista
        if(!(distance_min_final == Double.MAX_VALUE && distance_max_final == Double.MAX_VALUE)) {
        	l.add(distance_min_final);
        	l.add(distance_max_final);
        }
        return l; //Ritorna [min,max] se sono stati trovati, altrimenti lista vuota
    }

    //Query 12
    public static double getDistance(Position p1, Position p2) {
    	double distance;
    	distance = Math.sqrt(Math.pow(p1.getG_lon() - p2.getG_lon(), 2) + Math.pow(p1.getG_lat() - p2.getG_lat(), 2));
    	return distance;
    }
    public static ArrayList<Position> getPositionMainBranch(Connection con, String name) throws SQLException {
    	PreparedStatement statement;
    	ResultSet result;
    	ArrayList<Position> branches_position = new ArrayList<Position>();
 
    	String query;
    	query = "SELECT DISTINCT \"Galactic_Long\", \"Galactic_Lat\"" + 
    			" FROM \"GALACTIC_POSITION\" AS \"G\" JOIN \"BRANCH_POSITION\" AS \"BP\" ON \"G\".\"Id\"=\"BP\".\"Galactic_Pos\" JOIN \"BRANCH\" AS \"B\" on \"B\".\"Id\"=\"BP\".\"Branch_Id\" " + 
    			" WHERE \"B\".\"Filament_Name\" = ? AND \"B\".\"Type\"='S';";
    	statement = con.prepareStatement(query);
    	statement.setString(1, name);
    	result = statement.executeQuery();
    	while(result.next()) {
    		Position pos = new Position();
    		pos.setG_lon(result.getDouble("Galactic_Long"));
    		pos.setG_lat(result.getDouble("Galactic_Lat"));
    		branches_position.add(pos);
    	}
    	
    	return branches_position;
    }
    public static ArrayList<Star> distanceStarsToMainBranch(Connection con, String name) throws SQLException {
        ArrayList<Star> stars = new ArrayList<Star>();
        ArrayList<Position> branches_position = new ArrayList<Position>();
        stars = getStarsInFilament(con, name); 
        branches_position = getPositionMainBranch(con, name);
        for(Star s:stars) {
        	s.setDistanceBranch(Double.MAX_VALUE);
        	Position star_pos = new Position();
        	star_pos.setG_lon(s.getG_lon());
        	star_pos.setG_lat(s.getG_lat());
        	for(Position branch_pos:branches_position) {
        		double new_dist = getDistance(star_pos, branch_pos);
        		double actual_dist = s.getDistanceBranch();
        		if(actual_dist >  new_dist) {
        			s.setDistanceBranch(new_dist);
        		}
        	}
        }
        return stars;
    }
    public static ArrayList<Star> orderStars(ArrayList<Star> stars, String type) {
    	switch (type) {
	    	case "distanza":
	        	DistanceComparator d_c = new DistanceComparator();
	        	stars.sort(d_c);
	        	break;
	    	case "flusso":
	    		FluxComparator f_c = new FluxComparator();
	    		stars.sort(f_c);
	    		break;
    	}
    	    	
    	return stars;
    }

    public static ArrayList<Star> viewStarsLimit(ArrayList<Star> stars, int limit, int offset) {
    	ArrayList<Star> limit_stars = new ArrayList<Star>();
    	int i;
    	for (Star s : stars) {
    		if (limit == 0)
    			break;
    		i = stars.indexOf(s);
    		if (i >= offset) {
    			limit --;
    			limit_stars.add(s);
    		}
    	}
    	return limit_stars;
    }
    
    //Query utili
    public static String[] getSatellitesNames(Connection con) throws SQLException  {
		String[] satellitesNames=null;
		Statement query= con.createStatement();
		ResultSet resultCount = query.executeQuery("SELECT count(*)"
				                                 + "FROM \"SATELLITE\"");
		if (resultCount.next()) {
			int rowCounter = resultCount.getInt(1);
			satellitesNames = new String[rowCounter+1];
			satellitesNames[0]="SELECT";
			Statement query2= con.createStatement();
			ResultSet result = query2.executeQuery("SELECT \"Name\""
												 + "FROM \"SATELLITE\"");
			int i=1;
			while(result.next() && i!=(rowCounter+1)) {
				String satelliteName = result.getString("Name");
				satellitesNames[i]= satelliteName;
				i=i+1;
			}
	    }
		return satellitesNames;
	}
	public static String[] getFilNames(Connection con) throws SQLException  {
		String[] filamentsNames = null;
		Statement query= con.createStatement();
		ResultSet resultCount = query.executeQuery("SELECT count(*) FROM \"FILAMENT\"");
		if (resultCount.next()) {
			int rowCounter = resultCount.getInt(1);
			filamentsNames = new String[rowCounter+1];
			filamentsNames[0]="SELECT";
			Statement query2= con.createStatement();
			ResultSet result = query2.executeQuery("SELECT \"Name\" FROM \"FILAMENT\" ORDER BY \"Name\"");
			int i=1;
			while(result.next() && i!=(rowCounter+1)) {
				String filamentName = result.getString("Name");
				filamentsNames[i]= filamentName;
				i=i+1;
			}
	    }
		return filamentsNames;
	}
	public static String[] getInsNames(Connection con) throws SQLException  {
		String[] instrumentsNames = null;
		Statement query= con.createStatement();
		ResultSet resultCount = query.executeQuery("SELECT count(*) FROM \"INSTRUMENT\"");
		if (resultCount.next()) {
			int rowCounter = resultCount.getInt(1);
			instrumentsNames = new String[rowCounter+1];
			instrumentsNames[0]="SELECT";
			Statement query2= con.createStatement();
			ResultSet result = query2.executeQuery("SELECT \"Name\" FROM \"INSTRUMENT\"");
			int i=1;
			while(result.next() && i!=(rowCounter+1)) {
				String filamentName = result.getString("Name");
				instrumentsNames[i]= filamentName;
				i=i+1;
			}
	    }
		return instrumentsNames;
	}
	public static void insertSatellite(Connection con, String satellite, String datef, String datel) throws SQLException {
		String[] oldSatelliteList = getSatellitesNames(con);
		int i=0;
		while(i!=oldSatelliteList.length) {
			if (oldSatelliteList[i].equals(satellite)) {
				JOptionPane.showMessageDialog(null, "Satellite with name " + satellite + " already exists!");
				return;
			}
			else {
				i=i+1;
			}
		}
		int myId=0;
		Statement query= con.createStatement();
		ResultSet resultCount = query.executeQuery("SELECT count(*) FROM \"SATELLITE\"");
		if (resultCount.next()) {
			myId = resultCount.getInt(1)+1;
		}
		PreparedStatement statement2;
		String query2 = "INSERT INTO \"SATELLITE\" values(?,?,'" + datef + "','" + datel +"')";
		statement2 = con.prepareStatement(query2);
		statement2.setInt(1, myId);
		statement2.setString(2, satellite);
		statement2.executeUpdate();
		JOptionPane.showMessageDialog(null, "Satellite successfully added!");
		return;
	}	
	public static void insertInstrument(Connection con, String instrument, LinkedList<Double> bandDList, int satId) throws SQLException {
		String[] oldInstrumentList = getInsNames(con);
		int i=0;
		while(i!=oldInstrumentList.length) {
			if (oldInstrumentList[i].equals(instrument)) {
				JOptionPane.showMessageDialog(null, "Instrument with name " + instrument + " already exists!");
				return;
			}
			else {
				i=i+1;
			}
		}
		int insId=0;
		Statement query= con.createStatement();
		ResultSet resultCount = query.executeQuery("SELECT count(*) FROM \"INSTRUMENT\"");
		if (resultCount.next()) {
			insId = resultCount.getInt(1)+1;
		}
		
		
		PreparedStatement statement2;
		String query2 = "INSERT INTO \"INSTRUMENT\" values(?,?)";
		statement2 = con.prepareStatement(query2);
		statement2.setInt(1, insId);
		statement2.setString(2, instrument);
		statement2.executeUpdate();
		
		PreparedStatement statement8;
		String query8 = "INSERT INTO \"INS_SAT\" values(?,?)";
		statement8 = con.prepareStatement(query8);
		statement8.setInt(1, insId);
		statement8.setInt(2, satId);
		statement8.executeUpdate();
		
		int w=0;
		while (w!=bandDList.size()) {
			Double bandD = bandDList.get(w);
			
			PreparedStatement statement4;
			String query4 = "SELECT \"Id\" FROM \"BAND\" WHERE \"Band\"=?";
			statement4 = con.prepareStatement(query4);
			statement4.setDouble(1, bandD);
			ResultSet result3 = null;
			result3 = statement4.executeQuery();
			if (result3.next()) {
				PreparedStatement statement5;
				String query5 = "INSERT INTO \"INS_BAND\" values(?,?)";
				statement5 = con.prepareStatement(query5);
				statement5.setInt(1, insId);
				statement5.setInt(2, result3.getInt(1));
				statement5.executeUpdate();
			} else {
				int insBand=0;
				Statement query9 = con.createStatement();
				ResultSet resultCount4 = query9.executeQuery("SELECT count(*) FROM \"BAND\"");
				if (resultCount4.next()) {
					insBand = resultCount4.getInt(1)+1;
				}
				
				PreparedStatement statement6;
				String query6 = "INSERT INTO \"BAND\" values(?,?)";
				statement6 = con.prepareStatement(query6);
				statement6.setInt(1, insBand);
				statement6.setDouble(2, bandD);
				statement6.executeUpdate();	
				
				PreparedStatement statement7;
				String query7 = "INSERT INTO \"INS_BAND\" values(?,?)";
				statement7 = con.prepareStatement(query7);
				statement7.setInt(1, insId);
				statement7.setInt(2, insBand);
				statement7.executeUpdate();
			}
			w++;
		}
		
		JOptionPane.showMessageDialog(null, "Instrument added!");
		return;
		
	}


}