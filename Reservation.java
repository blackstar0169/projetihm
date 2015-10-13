import java.sql.*;
import java.util.Calendar;
public class Reservation {

    private Client initiateur;
    private int places;
    private Calendar jour;
    private Service service;
    private int creneau;
    public Reservation(int id) {

	try{
	    Class.forName("org.mariadb.jdbc.Driver");
	    try{
		Connection connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/projetihm","projetihm","mhitejorp");
		Statement declaration = connexion.createStatement();
		ResultSet r = declaration.executeQuery("SELECT * from Reservation WHERE id="+id);
		r.first();
		this.initiateur = new Client(r.getInt("initiateur"));
		this.places = r.getInt("places");
		Calendar cal = Calendar.getInstance();
		cal.setTime(r.getDate("jour"));
		this.jour = cal ;
		this.service = new Service(r.getInt("service"));
		this.creneau = r.getInt("creneau");
		r.close();
		declaration.close();
		connexion.close();
	    }catch (SQLException e) {
		System.err.println("Erreur lors de la requete SQL");
		e.printStackTrace();
	    }
	}catch (ClassNotFoundException e) {
	    System.err.println("Erreur lors du chargement de la classe");
	}
    }
    public static Reservation[] obtenirToutesReservations(){
	int i = 0;
	Reservation [] reservations = null;
	try{
	    Class.forName("org.mariadb.jdbc.Driver");
	    try{
		Connection connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/projetihm","projetihm","mhitejorp");
		Statement declaration = connexion.createStatement();
		ResultSet r = declaration.executeQuery("SELECT * from Reservation");
		r.last(); // Place le pointeur au dernier élément
		reservations = new Reservation [r.getRow()]; // Crée un tableau de N Clients
		r.beforeFirst();// Replace le pointeur avant le first
		while (r.next()){
		    reservations[i] = new Reservation(r.getInt("id"));
		    i++;
		}
		r.close();
		declaration.close();
		connexion.close();
	    }catch (SQLException e) {
		System.err.println("Erreur lors de la requete SQL");
		e.printStackTrace();
	    }
	}catch (ClassNotFoundException e) {
	    System.err.println("Erreur lors du chargement de la classe");
	}
	return reservations;
    }
    public static Reservation[] obtenirToutesReservationsparDate(Calendar date,Service serv){
	int i = 0;
	Reservation [] reservations = null;
	try{
	    Class.forName("org.mariadb.jdbc.Driver");
	    try{
		Connection connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/projetihm","projetihm","mhitejorp");
		Statement declaration = connexion.createStatement();
		ResultSet r = declaration.executeQuery("SELECT * from Reservation WHERE jour='"+ date.get(Calendar.YEAR)+"-"+date.get(Calendar.MONTH)+"-"+ date.get(Calendar.DAY_OF_MONTH)+"'"+  "AND service="+serv.getId());
		r.last(); // Place le pointeur au dernier élément
		reservations = new Reservation [r.getRow()]; // Crée un tableau de N Clients
		r.beforeFirst();// Replace le pointeur avant le first
		while (r.next()){
		    reservations[i] = new Reservation(r.getInt("id"));
		    i++;
		}
		r.close();
		declaration.close();
		connexion.close();
	    }catch (SQLException e) {
			System.err.println("Erreur lors de la requete SQL");
			e.printStackTrace();
	    }
	}catch (ClassNotFoundException e) {
	    System.err.println("Erreur lors du chargement de la classe");
	}
	return reservations;
    }


    public Client getInitiateur() {
	return initiateur;
    }

    public int getPlaces() {
	return places;
    }

    public Calendar getJour() {
	return jour;
    }

    public Service getService() {
	return service;
    }

    public int getCreneau() {
	return creneau;
    }
    //java -cp "../lib/mariadb-java-client.jar:."
}
