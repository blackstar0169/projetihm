import java.sql.*;
import java.util.*;

public class ModelePaiement{

    /**
     * Insert un nouvel enrgegistrement de paiement dans la bdd.
     * @param cal La date de paiement
     * @param prix Le prix qu'a payé le client
     * @param type Le mode de paiement utilisé
     * @return -1 en cas d'échec et 0 en cas de succès
     */
    public int insert(Calendar cal, float prix, String type){
        Connection connexion, connexion2;
        Statement declaration, declaration2;

        try{
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
            connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","");
            declaration = connexion.createStatement();

            connexion2 = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/projetihm","projetihm","mhitejorp");
            declaration2 = connexion2.createStatement();


            // Récupérer les services
            String sql = "SELECT designation, COUNT(*) AS cnt FROM `Service` WHERE debut<='"+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+"' AND fin>='"+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND)+"';";
            //System.out.println(sql);
            ResultSet serv = declaration2.executeQuery(sql);
            String service;
            serv.first();
            if(serv.getInt("cnt")==0){
                service = new String("midi");
            }else{
                service = serv.getString("designation");
            }


            Timestamp date = new Timestamp(cal.getTimeInMillis());
            sql = "INSERT INTO `Paiement`(date, prix, mode, service) VALUES('"+date+"', "+prix+", '"+type+"', '"+service+"');";
            ResultSet r = declaration.executeQuery(sql);

            declaration.close();
            connexion.close();
            declaration2.close();
            connexion2.close();


        }catch(SQLException e){
            System.out.println("Erreur sql : "+e.getMessage());
            return -1;
        }
        return 0;

    }

    
    public float getCADate(Calendar cal){
        Connection connexion;
        Statement declaration;
        float ca = 0;

        try{
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
            connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","");
            declaration = connexion.createStatement();

            // On calcule l'interval de recupération du CA
            Calendar dB = Calendar.getInstance();
            Calendar dE = Calendar.getInstance();

            dB.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
            dE.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);

            // On transforme les Calendar en Timestamp compris par la bdd
            Timestamp dBegin = new Timestamp(dB.getTimeInMillis());
            Timestamp dEnd = new Timestamp(dE.getTimeInMillis());
            String sql = "SELECT prix FROM `Paiement` WHERE date>='"+dBegin+"' AND date<='"+dEnd+"';";
            ResultSet r = declaration.executeQuery(sql);
            r.beforeFirst();

            while(r.next()){
                ca += r.getFloat("prix");
            }



            declaration.close();
            connexion.close();

        }catch(SQLException e){
            System.out.println("Erreur sql : "+e.getMessage());
            return -1;
        }
        return ca;
    }

    /**
     * Récupères le chiffre d'affaire en fonction du service.
     * @param s Le service pour lequel on doit récupérer le chiffre d'affaire.
     * @return int Le chiffre d'affaire du jour.
     */
    public float getCAService(String s){
        Connection connexion;
        Statement declaration;
        float ca = 0;

        try{
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
            connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","");
            declaration = connexion.createStatement();
            String sql = "SELECT prix FROM `Paiement` WHERE service='"+s+"';";
            ResultSet r = declaration.executeQuery(sql);
            r.beforeFirst();

            while(r.next()){
                ca += r.getFloat("prix");
            }

            declaration.close();
            connexion.close();

        }catch(SQLException e){
            System.out.println("Erreur sql : "+e.getMessage());
            return -1;
        }
        return ca;
    }

}

