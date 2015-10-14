import java.sql.*;
import java.util.Calendar;

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
            //System.out.println(sql);
            ResultSet serv = declaration.executeQuery(sql);
            serv.first();
            if(serv.getRow()==0){
                System.err.println("Pas de creneau");
                return null;
            }

            Time t = serv.getTime("debut");

            sTemp = hDebut*3600+mDebut*60+sDebut-t.getHours()*3600+t.getMinutes()*60+t.getSeconds();
            creneauBegin = 0;//(int)(sTemp/60)/15;
            creneauEnd = (int)(creneauBegin+minutesInterval/15);




            sql = "SELECT * FROM Reservation INNER JOIN Client ON Reservation.initiateur=Client.id WHERE Reservation.jour='"+begin.get(Calendar.YEAR)+"/"+begin.get(Calendar.MONTH)+"/"+begin.get(Calendar.DAY_OF_MONTH)+"' AND Reservation.creneau>="+creneauBegin+" AND Reservation.creneau<="+creneauEnd+" ORDER BY Reservation.id;";
            //System.out.println(sql);
            ResultSet r = declaration.executeQuery(sql);
            r.last();

            ModeleReservation[] reservation = new ModeleReservation[r.getRow()];
            r.beforeFirst();
            int i=0;
            while(r.next()){
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

    public int getId(){
        return this.id;
    }
    public void setId(int i){
        this.id = i;
    }

    public String getNom(){
        return this.nom;
    }
    public void setNom(String n){
        this.nom = n;
    }

    public String getDate(){
        return this.date;
    }
    public void setDate(String d){
        this.date=d;
    }

    public int getPlaces(){
        return this.places;
    }
    public void setPlaces(int p){
        this.places=p;
    }

    public void setCreneau(int c){
        this.creneau = c;
    }
    public int getCreneau(){
        return this.creneau;
    }


}
