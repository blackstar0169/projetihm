import java.sql.*;

/**
 * Classe gérant la récupèreation des tables dans la base de données (bdd)
 * @author Anthony DUPLAT
 */

public class ModeleTable{

	private int numero;
	private byte statut;
	private int groupId;
	private String nom;

	public ModeleTable(){}

    /**
     * Modifie le id du groupe de la table.
     * @param id L'id du group de la table. 
     */
	public void setGroupId(int id){
		this.groupId = id;
	}
    /**
     * Récupère le id du groupe de la table.
     * @return int L'id du group de la table. 
     */
	public int getGroupId(){
		return this.groupId;
	}
    /**
     * Modifie le statut de la table.
     * @param s Le statut de la table. 
     */
	public void setStatut(byte s){
		this.statut = s;
	}
    /**
     * Récupère le statut de la table.
     * @return byte Le statut de la table. 
     */
	public byte getStatut(){
		return this.statut;
	}
    /**
     * Modifie le id de la table.
     * @param n L'id de la table. 
     */
	public void setNumero(int n){
		this.numero = n;
	}
    /**
     * Récupère le id de la table.
     * @return id L'id de la table. 
     */ 
	public int getNumero(){
		return this.numero;
	}

    /**
     * Récupères toutes les tables stockées dans la bdd.
     * @return Table[] Le tableau contenant les tables récupérées.
     */
	public Table[] getAll(){
	    Connection connexion;
        Statement declaration;


        try{//On test si le dialogue avec la bdd se passe bien
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
            // On se connecte
            connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","");
            declaration = connexion.createStatement();

            String sql = "SELECT * FROM `Table`";
            ResultSet r = declaration.executeQuery(sql);
            r.last();

            Table[] tables = new Table[r.getRow()];
            r.beforeFirst();
            int i=0;
            // On récupère toutes les tables que l'on stock dans le tableau qui sera renvoyer.
            while(r.next()){
                tables[i] = new Table(r.getInt("id"), (byte)r.getInt("statut"), r.getInt("groupId"),  r.getString("nom"));
                i++;
            }
            r.close();

            // On ferme la connection
            declaration.close();
            connexion.close();


            return tables;
        }catch(SQLException e){
            System.out.println("Erreur sql : "+e.getMessage());
            return null;
        }
    }

    /**
     * Actualise les tables de la bdd.
     * @param t Le tableau contenant les tables à modifier dans la bdd.
     * @return -1 en cas d'erreur, 0 si tout c'est bien passé.
     */
    public int updateTables(Table[] t){
        Connection connexion;
        Statement declaration;

        try{//On test si le dialogue avec la bdd se passe bien
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
            connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","");
            declaration = connexion.createStatement();
            String nom;

            // On update les table une par une
            for(int i=0; i<t.length; i++){
                nom="'"+t[i].getNom()+"'";
                if(t[i].getNom()==null){
                    nom = "null";
                }
                String sql = "UPDATE `Table` SET statut="+t[i].getStatut()+", nom="+nom+", groupId="+t[i].getGroupId()+" WHERE id="+t[i].getNumero()+";";
                ResultSet r = declaration.executeQuery(sql);
            }
            // On ferme la connection
            declaration.close();
            connexion.close();

        }catch(SQLException e){
            System.out.println("Erreur sql : "+e.getMessage());
            return -1;
        }
        return 0;

    }

    /**
     * Actualise une tables de la bdd qui a le même numero que la table passée en paramètre.
     * @param t La table a modifié dans la bdd.
     * @return -1 en cas d'erreur, 0 si tout c'est bien passé.
     */
    public int updateOneTable(Table t){
        Connection connexion;
        Statement declaration;

        try{
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
            connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","");
            declaration = connexion.createStatement();

            String nom;
            nom="'"+t.getNom()+"'";
            // Si on a pas de nom alors on insère null dans la bdd
            if(t.getNom()==null){
                nom = "null";
            }
            String sql = "UPDATE `Table` SET statut="+t.getStatut()+", nom="+nom+", groupId="+t.getGroupId()+" WHERE id="+t.getNumero()+";";
            ResultSet r = declaration.executeQuery(sql);

            // Fermeture de la connection
            declaration.close();
            connexion.close();

        }catch(SQLException e){
            System.out.println("Erreur sql : "+e.getMessage());
            return -1;
        }
        return 0;

    }

}

