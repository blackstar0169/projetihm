import java.sql.*;

public class ModeleTable{

	private int numero;
	private byte statut;
	private int groupId;
	private String nom;

	public ModeleTable(){}

	public void setGroupId(int id){
		this.groupId = id;
	}
	public int getGroupId(){
		return this.groupId;
	}

	public void setStatut(byte s){
		this.statut = s;
	}
	public byte getStatut(){
		return this.statut;
	}

	public void setNumero(int n){
		this.numero = n;
	}
	public int getNumero(){
		return this.numero;
	}

	public Table[] getAll(){
	    Connection connexion;
            Statement declaration;


            try{
            try{
                Class.forName("org.mariadb.jdbc.Driver");
            }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
                connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","13060169");
                declaration = connexion.createStatement();

                String sql = "SELECT * FROM `Table`";
                //System.out.println(sql);
                ResultSet r = declaration.executeQuery(sql);
                r.last();

                Table[] tables = new Table[r.getRow()];
                r.beforeFirst();
                int i=0;
                while(r.next()){
                    tables[i] = new Table(r.getInt("id"), (byte)r.getInt("statut"), r.getInt("groupId"),  r.getString("nom"));
                    i++;
                }
                r.close();
                declaration.close();
                connexion.close();


                return tables;
            }catch(SQLException e){
                System.out.println("Erreur sql : "+e.getMessage());
                return null;
            }
        }

        public int updateTables(Table[] t){
            Connection connexion;
            Statement declaration;


            try{
                try{
                    Class.forName("org.mariadb.jdbc.Driver");
                }catch(ClassNotFoundException ce){System.err.println("Erreur lors du chargement de la classe");}
                connexion = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/duplata","duplata","13060169");
                declaration = connexion.createStatement();
                String nom;

                for(int i=0; i<t.length; i++){
                    nom="'"+t[i].getNom()+"'";
                    if(t[i].getNom()==null){
                        nom = "null";
                    }
                    String sql = "UPDATE `Table` SET statut="+t[i].getStatut()+", nom="+nom+", groupId="+t[i].getGroupId()+" WHERE id="+t[i].getNumero()+";";
                    ResultSet r = declaration.executeQuery(sql);
                }


                declaration.close();
                connexion.close();

            }catch(SQLException e){
                System.out.println("Erreur sql : "+e.getMessage());
                return -1;
            }

            return 0;

        }
	/*public ModeleTable findOrFail(){

		return ;
	}*/

}

