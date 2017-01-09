import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connexion {
	
	public static final String IP = "localhost:5433/examen";
	public static final String USER = "postgres";
	public static final String PASSWORD = "********";
	private PreparedStatement er;
	private PreparedStatement lz;
	private PreparedStatement ha;
	private PreparedStatement nr;
	Connection conn;

	public Connexion(){
		try{
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		String url = "jdbc:postgresql://" + IP + "?user=" + USER + "&password=" + PASSWORD ;
		try {
			conn = DriverManager.getConnection(url);
			er = conn.prepareStatement("SELECT * FROM ajouter_reperage(?,?,cast(? as date),?);");
			lz = conn.prepareStatement("SELECT * FROM classement_heure(cast(? as date),?);");
			ha = conn.prepareStatement("SELECT * FROM historique_aventurier(?);");
			nr = conn.prepareStatement("SELECT * FROM nbre_reperages_aventuriers(?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String enregistrerRep(String zone, String aventurier, String date, int heure){
		String msg = "Aucun reperage ajoute";
		try {
			er.setString(1, zone);
			er.setString(2, aventurier);
			er.setString(3, date);
			er.setInt(4, heure);
			try (ResultSet rs = er.executeQuery()) {
				while(rs.next()) {
					msg = "Reperage ajoute sous l'id : " + rs.getString(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Le reperage n'a pas pu être ajouté";
		}
		return msg;
	}
	
	public String listerZones(String date, int heure) {
		String msg = "";
		try {
			lz.setString(1, date);
			lz.setInt(2, heure);
			try (ResultSet rs = lz.executeQuery()) {
				while(rs.next()) {
					msg += "La Zone " + rs.getString(1) + " possède " + rs.getInt(2) + " aventurier(s)\n";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erreur lors du listing";
		}
		if(msg == null) {
			return "Pas d'aventuriers pour cette heure là à cette date là";
		}
		return msg;
	}
	
	public String historiqueAventurier(String nom){
		String msg = "";
		try  {
			ha.setString(1, nom);
			try (ResultSet rs = ha.executeQuery()) {
				while(rs.next()) {
					msg += "Repérage en " + rs.getString(1) + ", le " + rs.getString(2) + " à "
							+ rs.getInt(3) + "\n";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erreur lors de l'historique";
		}
		if(msg == null)
			return "Pas d'historique pour cet aventurier";
		return msg;
	}
	
	public String nbreReperages(String nom) {
		String msg = "";
		try {
			nr.setString(1, nom);
			try (ResultSet rs = nr.executeQuery()) {
				while(rs.next()) {
					return "L'aventurier à été repéré : " + rs.getString(1) + " fois";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erreur lors du nombre de repérages";
		}
		return "Pas de reperages pour cet aventurier";
	}
}
