import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

public class ControleurReservation extends Controleur implements ActionListener{

    private int personnesAPlacer;
    private int idGroupe;
    private int rowId;
    private String nomGroupe;
    private Table[] tables;

    private JButton valider;
    private JButton nouveauClient;

    private VueReservation vue;

    public ControleurReservation(ModeleTable m, Table[] t,  Component[] rC, VueReservation v){
        setComponents(rC);
        this.tableCounter = 0;
        this.personnesAPlacer = 20;
        this.idGroupe=0;
        this.tables = t;
        this.vue = v;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getName()=="annuler"){
                ControleurTables.deleteSelection();
                this.tableCounter=0;
                idGroupe=0;
                this.personnesAPlacer=0;
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
                ControleurTables.setMode(ControleurTables.NONE);
            }
            else if(b.getName()=="valider"){
                ArrayList<Table> tab = ControleurTables.getSelection();
                for(int i=0; i<tab.size(); i++){
                    tab.get(i).setStatut(Table.RESERVE);
                    tab.get(i).setGroupId(idGroupe);
                }
                ControleurTables.deleteSelection();
                this.tableCounter=0;
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
                idGroupe=0;
                personnesAPlacer=0;
                ControleurTables.setMode(ControleurTables.NONE);
                vue.deleteRow(rowId);
                vue.init();
            }
            else if(b.getName()=="nouveauClient"){
                ControleurTables.setMode(ControleurTables.NONE);
                this.tableCounter=0;
                this.personnesAPlacer=0;
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
                ControleurTables.deleteSelection();
                String nbr = JOptionPane.showInputDialog(null,
                        "Nombre de personnes : ", null);
                try{
                    this.personnesAPlacer=Integer.parseInt(nbr);
                    if(personnesAPlacer<0){
                        JOptionPane.showMessageDialog(null,
                            "Vous devez entrer un nombre entre 1 et 60", "Numero invalide",
                            JOptionPane.ERROR_MESSAGE);
                        personnesAPlacer=0;
                    }else{
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
        }
    }

    public void setComponents(Component[] c){
        for(int i=0; i<c.length; i++){
            if(c[i].getName()=="valider")
                this.valider = (JButton)c[i];
            if(c[i].getName()=="nouveauClient")
                this.nouveauClient = (JButton)c[i];
        }
    }

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

    public void placerReservation(int rId, int gid, String nom, int nbrP){
        personnesAPlacer=nbrP;
        nomGroupe=nom;
        idGroupe=gid;
        ControleurTables.setMode(ControleurTables.RESERVATION);
        rowId = rId;
    }

}
