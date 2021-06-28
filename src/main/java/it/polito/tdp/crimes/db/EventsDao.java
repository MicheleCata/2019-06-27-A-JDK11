package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getCategory (){
		String sql = "SELECT DISTINCT e.`offense_category_id` as categoria "
				+ "From events e";
		
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection() ;
		try {
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("categoria"));
			}
		
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	}
	
	public List<Integer> getAnno(){
		String sql= "SELECT DISTINCT Year(e.`reported_date`) as anno "
				+ "From events e "
				+ "order by Year(e.`reported_date`) asc";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection() ;
		try {
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getInt("anno"));
			}
		
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	}
	
	public List<String> getVertici(String categoria, int anno){
		String sql = "SELECT DISTINCT e.`offense_type_id` as evento "
				+ "From events e "
				+ "where e.`offense_category_id`= ? AND Year(e.`reported_date`)= ?";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection() ;
		try {
			
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("evento"));
			}
			conn.close();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
