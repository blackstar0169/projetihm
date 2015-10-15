import java.sql.*;
import java.util.Calendar;

/**
 * Classe gérant la récupèreation des réservations dans la base de données (bdd)
 * @author Anthony DUPLAT
 */

public class ModeleReservation {

    private int places;
    private int id;
    private int creneau;
    private String date;
    private String nom;

    public ModeleReservation(){}

    public ModeleReservation(int i, int p, int c, String d, String n) {
        this.id=i;
        this.places = p;
        this.creneau = c;
        this.date = d;
        this.nom = n;
    }

    /**
     * Récupères toutes les réservations stockées dans la bdd.
     * @param begin La date de debut de sélection des réservaitons.
     * @param minutesInterval L'interval de sélection en minutes.
     * @return ModeleReservation[] Le tableau contenant les réservations récupérées.
     */
    public ModeleReservation[] getInterval(Calendar begin, int minutesInterval){
        Connection connexion;
        Statement declaration;

        try{
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
            connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/projetihm","projetihm","mhitejorp");
            declaration = connexion.createStatement();



            int hDebut, mDebut, sDebut, sTemp, creneauBegin, creneauEnd;
            int hFin, mFin, sFin;

            // Calcule des heures de l'interval
            hDebut = begin.get(Calendar.HOUR_OF_DAY);
            mDebut = begin.get(Calendar.MINUTE);
            sDebut = begin.get(Calendar.SECOND);

            sTemp = hDebut*3600+mDebut*60+sDebut+minutesInterval*60;

            hFin = (int)sTemp/3600;
            sTemp -= hFin*3600;
            mFin = (int)sTemp/60;
            sTemp -= mFin*60;
            sFin = sTemp;

            // Récupérer les services
            String sql = "SELECT designation, debut FROM Service WHERE debut<='"+hDebut+":"+mDebut+":"+sDebut+"' AND fin>='"+hFin+":"+mFin+":"+sFin+"';";
            ResultSet serv = declaration.executeQuery(sql);            
            serv.first();
            if(serv.getRow()==0){ // Si il n'y a pas de crénaux
                System.err.println("Pas de creneau");
                return null;
            }

            Time t = serv.getTime("debut");

            //Calcules des crenaux
            sTemp = (hDebut*60+mDebut)-(t.getHours()*60+t.getMinutes());
            creneauBegin = (int)(sTemp/15);
            creneauEnd = (int)(creneauBegin+minutesInterval/15);

            sql = "SELECT * FROM Reservation INNER JOIN Client ON Reservation.initiateur=Client.id WHERE Reservation.jour='"+begin.get(Calendar.YEAR)+"/"+(begin.get(Calendar.MONTH)+1)+"/"+begin.get(Calendar.DAY_OF_MONTH)+"' AND Reservation.creneau>="+creneauBegin+" AND Reservation.creneau<="+creneauEnd+" ORDER BY Reservation.id;";
            System.out.println(sql);
            ResultSet r = declaration.executeQuery(sql);
            r.last();

            ModeleReservation[] reservation = new ModeleReservation[r.getRow()];
            r.beforeFirst();
            int i=0;
            while(r.next()){ // On récupères toutes les information des réservations de la bdd et on les stockent dans un tableau
                String d =  begin.get(Calendar.DAY_OF_MONTH)+"/"+begin.get(Calendar.MONTH)+"/"+begin.get(Calendar.YEAR);
                reservation[i] = new ModeleReservation(r.getInt("id"), r.getInt("places"), r.getInt("creneau"), d,  r.getString("nom"));
                i++;
            }
            r.close();
            declaration.close();
            connexion.close();

            return reservation;
        }catch(SQLException e){
            System.out.println("Erreur sql : "+e.getMessage());
            return null;
        }

    }

    /**
     * Récupères l'id de la réservation.
     * @return int L'id de la réservation.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Récupères le nom du client de la réservation.
     * @return String Le nom du client.
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Récupères la date de la réservation.
     * @return String La date sous forme d'une chaine de caractères.
     */
    public String getDate(){
        return this.date;
    }

    /**
     * Récupères le nombre de place réservées.
     * @return int le nombre de places (environs 4 milliards on est large).
     */
    public int getPlaces(){
        return this.places;
    }
    
    /**
     * Récupère le créneau de la réservation.
     * @return int Le créneau de la réservation.
     */ 
    public int getCreneau(){
        return this.creneau;
    }


}
