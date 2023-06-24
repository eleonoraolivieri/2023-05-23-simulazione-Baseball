package it.polito.tdp.baseball.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.baseball.model.Adiacenza;
import it.polito.tdp.baseball.model.Appearances;

import it.polito.tdp.baseball.model.People;
import it.polito.tdp.baseball.model.Team;



public class BaseballDAO {
	
	public void readAllPlayers(Map<String,People> idMap){
		String sql = "SELECT * "
				+ "FROM people";
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getString("playerID"))) {
				People people = new People(rs.getString("playerID"), 
						rs.getString("birthCountry"), 
						rs.getString("birthCity"), 
						rs.getString("deathCountry"), 
						rs.getString("deathCity"),
						rs.getString("nameFirst"), 
						rs.getString("nameLast"), 
						rs.getInt("weight"), 
						rs.getInt("height"), 
						rs.getString("bats"), 
						rs.getString("throws"),
						getBirthDate(rs), 
						getDebutDate(rs), 
						getFinalGameDate(rs), 
						getDeathDate(rs));
				idMap.put(rs.getString("playerID"), people);
				}
			}

			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	public List<Team> readAllTeams(){
		String sql = "SELECT * "
				+ "FROM  teams";
		List<Team> result = new ArrayList<Team>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Team( rs.getInt("iD"),
						rs.getInt("year"), 
						rs.getString("teamCode"), 
						rs.getString("divID"), 
						rs.getInt("div_ID"), 
						rs.getInt("teamRank"),
						rs.getInt("games"), 
						rs.getInt("gamesHome"), 
						rs.getInt("wins"), 
						rs.getInt("losses"), 
						rs.getString("divisionWinnner"), 
						rs.getString("leagueWinner"),
						rs.getString("worldSeriesWinnner"), 
						rs.getInt("runs"), 
						rs.getInt("hits"), 
						rs.getInt("homeruns"), 
						rs.getInt("stolenBases"),
						rs.getInt("hitsAllowed"), 
						rs.getInt("homerunsAllowed"), 
						rs.getString("name"), 
						rs.getString("park")  ) );
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	
	//=================================================================
	//==================== HELPER FUNCTIONS   =========================
	//=================================================================
	
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getBirthDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("birth_date") != null) {
				return rs.getTimestamp("birth_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getDebutDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("debut_date") != null) {
				return rs.getTimestamp("debut_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getFinalGameDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("finalgame_date") != null) {
				return rs.getTimestamp("finalgame_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * @param rs
	 * @return
	 */
	private LocalDateTime getDeathDate(ResultSet rs) {
		try {
			if (rs.getTimestamp("death_date") != null) {
				return rs.getTimestamp("death_date").toLocalDateTime();
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	public List<People> getVertici(int anno,int salario, Map<String,People> idMap){
		String sql = "SELECT p.playerID as id "
				+ "FROM people p, salaries s "
				+ "WHERE p.playerID = s.playerID "
				+ "AND s.year = ? and s.salary > ?*1000000";
		List<People> result = new LinkedList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, salario);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				result.add(idMap.get(rs.getString("id")));
			}
			
			rs.close();
			st.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Adiacenza> getAdiacenze(int anno,int salario, Map<String,People> idMap){
		String sql = "WITH giocatore AS( "
				+ "SELECT p.playerID "
				+ "FROM people p, salaries s "
				+ "WHERE p.playerID = s.playerID "
				+ "AND s.year = ? and s.salary > ?*1000000) "
				+ "SELECT g1.playerID, g2.playerID "
				+ "FROM giocatore g1, giocatore g2, appearances a1,appearances a2 "
				+ "WHERE g1.playerID <>g2.playerID "
				+ "AND g1.playerID = a1.playerID "
				+ "AND g2.playerID = a2.playerID "
				+ "AND a1.teamID = a2.teamID "
				+ "AND a1.year = ? "
				+ "AND a2.year = a1.year";
		List<Adiacenza> result = new LinkedList<Adiacenza>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, salario);
			st.setInt(3, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				People sorgente = idMap.get(rs.getString("g1.playerID"));
				People destinazione = idMap.get(rs.getString("g2.playerID"));
				if(sorgente != null && destinazione != null) {
					result.add(new Adiacenza(sorgente, 
							destinazione));
				} else {
					System.out.println("Errore in getAdiacenze");
				}
			}
			rs.close();
			st.close();
			conn.close();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

}
}