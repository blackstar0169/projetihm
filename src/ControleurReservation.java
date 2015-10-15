import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ControleurReservation extends Controleur implements ActionListener{

    private int personnesAPlacer;
    private int idGroupe;
    private int rowId;
    private String nomGroupe;
    private Table[] tables;


    private JButton valider;
    private JButton nouveauClient;


    private ModeleTable modeleTable;
    private ModeleReservation modeleRes;

    private VueReservation vue;

    private int difTemps;
    private Calendar temps;

    public ControleurReservation(ModeleTable mT, ModeleReservation mR, Table[] t,  Component[] rC, VueReservation v){
        setComponents(rC);
        this.tableCounter = 0;
        this.personnesAPlacer = 20;
        this.idGroupe=0;
        this.tables = t;
        this.vue = v;
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 9 , 02, 11, 30);
        ModeleReservation[] res = mR.getInterval(cal, 60);
        v.setReservations(res);
        v.setTableauListener(this);
        this.modeleTable = mT;
        this.modeleRes = mR;
        // Initialisation du timer de rafraichissement
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new Refresh(this.tables, v), 0, 30000);
    }

    /**
    * Reagit au clique sur les boutons
    * @param e L'ActionEvent genere
    */
    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getName()=="annuler"){ //Si on annule
                ControleurTables.deleteSelection(); // On déselectionne les tables
                this.tableCounter=0; // On reset les compteurs
                idGroupe=0;
                this.personnesAPlacer=0;
                this.nomGroupe=null;
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
                ControleurTables.setMode(ControleurTables.NONE); //On empèche l'utilisateur de sélectionner une table avant d'avoir le nombre de clients à placer
            }
            else if(b.getName()=="valider"){ //Si on valide
                ArrayList<Table> tab = ControleurTables.getSelection();
                // On récupère les tables sélectionnées
                for(int i=0; i<tab.size(); i++){
                    tab.get(i).setStatut(Table.RESERVE);
                    tab.get(i).setGroupId(idGroupe);
                    tab.get(i).setNom(nomGroupe);
                }
                //On reset les compteurs et on désélectionne les tables
                ControleurTables.deleteSelection();
                this.tableCounter=0; 
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
                idGroupe=0;
                personnesAPlacer=0;
                ControleurTables.setMode(ControleurTables.NONE);
                modeleTable.updateTables(this.tables); // On update la bdd des tables
                vue.deleteRow(rowId);
                vue.init();
                this.nomGroupe=null;
                //On peut supprimer la réservation dans la table mais elle n'est qu'en lecture seule
            }
            else if(b.getName()=="nouveauClient"){ // Si on entre un nouveau client
                ControleurTables.setMode(ControleurTables.NONE);
                this.tableCounter=0;
                this.personnesAPlacer=0;
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
                // On demande le nombre de clients
                ControleurTables.deleteSelection();
                String nbr = JOptionPane.showInputDialog(null,
                        "Nombre de personnes : ", null);
                try{
                    // On teste si ce nombre est valide
                    this.personnesAPlacer=Integer.parseInt(nbr);
                    if(personnesAPlacer<0){
                        // Si le nombre n'est pas valide on affiche un message d'erreur
                        JOptionPane.showMessageDialog(null,
                            "Vous devez entrer un nombre entre 1 et 60", "Numero invalide",
                            JOptionPane.ERROR_MESSAGE);
                        personnesAPlacer=0;
                    }else{
                        //Si tout ce passe bien on affect un id pas encore placé
                        idGroupe=0;
                        boolean exist=true;
                        while(exist){
                            exist=false;
                            for(int i=0; i<tables.length; i++){
                                if(idGroupe==tables[i].getGroupId()){
                                    idGroupe++;
                                    exist=true;
                                }
                            }
                        }

                        ControleurTables.setMode(ControleurTables.RESERVATION);
                    }
                }catch(NumberFormatException exep){
                    JOptionPane.showMessageDialog(null,
                            "Vous devez entrer un nombre entre 1 et 60", "Numero invalide",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
            else if(b.getName()=="option"){
                //Si on clique sur option on change le temps
                String strDate = JOptionPane.showInputDialog(null,
                        "Réglage de temps (ex: 03/06/1996 15:06) ", null);

                try{ // On test si la date est valide
                    temps = Calendar.getInstance();
                    Date d;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);;
                    d = sdf.parse(strDate);
                    temps.set(d.getYear()+1900, d.getMonth(), d.getDate(), d.getHours(), d.getMinutes(), d.getSeconds());
                    updateReservation();

                }catch(Exception exep){
                    JOptionPane.showMessageDialog(null,
                            "Vous devez entrer une date valide (ex: 03/06/1996 15:06).", "Date invalide",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Modifie les éléments à manipuler.
     * @param c Le tableau de composants à manipuler.
     */
    public void setComponents(Component[] c){
        for(int i=0; i<c.length; i++){
            if(c[i].getName()=="valider")
                this.valider = (JButton)c[i];
            if(c[i].getName()=="nouveauClient")
                this.nouveauClient = (JButton)c[i];
        }
    }

    /**
     * Modifie le compteur des tables sélectionnées.
     * @param n Le nombre de tables sélectionnées.
     * @return true Si la sélection peut se faire. 
     */
    public boolean setTableCounter(int n){
        if((n-1)*2<personnesAPlacer){
            this.tableCounter =n;
            if(tableCounter*2>=personnesAPlacer && personnesAPlacer>0){
                this.valider.setEnabled(true);
                this.valider.setBackground(Color.GREEN);
            }
            return true;
        }
        return false;
    }

    /**
     * Initialise la réservation à placer.
     * @param rId L'id de la ligne dans le tableau.
     * @param gid L'id de la réservation.
     * @param nom Le nom du client.
     * @param nbrP Le nombre de personnes à placer.
     */
    public void placerReservation(int rId, int gid, String nom, int nbrP){
        personnesAPlacer=nbrP;
        nomGroupe=nom;
        idGroupe=gid;
        ControleurTables.setMode(ControleurTables.RESERVATION);
        rowId = rId;
    }
    /**
    * Recharge les réservation dans la bdd.
    */
    public void updateReservation(){
        ModeleReservation[] res = modeleRes.getInterval(temps, 60);
        vue.setReservations(res);
        vue.setTableauListener(this);
    }

}
