import java.sql.*;
import java.util.Calendar;
public class ModeleReservation {

    private Statement declaration;
    private int places;
    private int id;
    private Calendar date;
    private String nom;

    public ModeleReservation(int id) {
    	try{
		    Class.forName("org.mariadb.jdbc.Driver");
		    try{
				Connection connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/projetihm","projetihm","mhitejorp");
				Statement declaration = connexion.createStatement();				
		    }catch (SQLException e) {
				System.err.println("Erreur lors de la requete SQL");
				e.printStackTrace();
		    }
		}catch (ClassNotFoundException e) {
		    System.err.println("Erreur lors du chargement de la classe");
		}
    }

    public ModeleReservation[] getInterval(Calendar begin, int interval){
    	// Récupérer les services
    	// Regarder dans quel service on est 
    	// Regarder sousraire begin a l'heure du service
    	// diviser le résultat par 15 poure avoi le crénau
    	ResultSet r = this.declaration.executeQuery("SELECT * FROM reservation 
    		INNER JOIN client ON reservation.initiateur=client.id
    		WHERE reservation.jour>="+jourBegin+" AND creneau>="+creneau+" OR jour<="+jourEnd+" AND creneau<="+creneau" ORDER BY reservation.jour;");
    	r.last();

    	ModeleReservation reservation = new ModeleReservation[r.getRow()];
    	r.first();
    	for(int i=0; i<reservation.length; i++){
    		r.getIni("");
    	}




    }

}