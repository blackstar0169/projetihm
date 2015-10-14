import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;


public class ControleurTables implements MouseListener{
        public static final byte NONE = 0;
	public static final byte NETTOYAGE = 1;
        public static final byte RESERVATION = 2;
        public static final byte PAIEMENT = 3;

        private static Table[] tables;
        private ModeleTable model;
        private static ArrayList<Table> selection;
        private static byte mode;
        private static Controleur ctrl;

        private VueNettoyage vue;

	public ControleurTables(ModeleTable m, Table[] t, Controleur c){
	    this.model = m;
	    this.tables = t;
            this.ctrl = c;
            this.selection = new ArrayList<Table>();
            this.mode=0;
	}
        public ControleurTables(ModeleTable m, Table[] t, VueNettoyage v){
            this.model = m;
	    this.tables = t;
            this.mode = 1;//Nettoyage
            this.vue = v;
        }

	/**
	* nop
	*/
	public void mousePressed(MouseEvent e) {
	}

	/**
	*	nop
	*/
	public void mouseReleased(MouseEvent e) {
	}

	/**
	*	 nop
	*/
	public void mouseEntered(MouseEvent e) {
	}

	/**
	*	 nop
	*
	*/
	public void mouseExited(MouseEvent e) {
	}

        /**
	* Reagit au clique sur les tables
	* @param e le MouseEvent genere
	*/
	public void mouseClicked(MouseEvent me){
            if(me.getSource() instanceof Table){
                Table t = (Table)me.getSource();

                if(t.getStatut() == Table.ALAVER && this.mode==ControleurTables.NETTOYAGE){
                    t.setStatut(Table.LIBRE);
                    t.setGroupId(-1);
                    t.setNom("");
                    vue.init();
                    model.updateOneTable(t);
                    //Update le model
                }
                else if(t.isSelected()==false && t.getStatut() == Table.LIBRE && this.mode==ControleurTables.RESERVATION){
                    if(ctrl.setTableCounter(selection.size()+1)){
                        //Si la table sélectionnée est une table adgacente et qu'elle fait partie de la même rangée ou que l'on a aucune selection
                        if(selection.size()==0){
                            t.setSelected(true);
                            selection.add(t);
                        }else if(t.getRangee()==selection.get(0).getRangee()){ //Si on est sur la même rangée
                            for(int i=0; i<selection.size(); i++){
                                if(t.getNumero()+1 == selection.get(i).getNumero() || t.getNumero()-1 == selection.get(i).getNumero()){
                                    t.setSelected(true);
                                    selection.add(t);
                                }
                            }
                        }

                    }
                }
                else if(t.isSelected()==false && t.getStatut() == Table.RESERVE && this.mode==ControleurTables.PAIEMENT){

                    if(this.ctrl.getTableCounter()==0){ //On verifie si on a pas déja selectionner des tables
                        for(int i=0; i<this.tables.length; i++){ //On selection toutes les tables collées (ayant le même groupe de personnes)
                            if(this.tables[i].getGroupId() == t.getGroupId()){
                                tables[i].setSelected(true);
                                selection.add(tables[i]);
                            }
                        }
                        this.ctrl.setTableCounter(selection.size());
                    }

                }
                else if(t.isSelected() && mode==PAIEMENT){
                    t.setSelected(false);
                    for(int i=0; i<tables.length; i++){ //On deselection toutes les tables collées (ayant le même groupe de personnes)
                        if(tables[i].getGroupId() == t.getGroupId()){
                            tables[i].setSelected(false);
                            selection.remove(tables[i]);
                        }
                    }
                    this.ctrl.setTableCounter(selection.size());
                }
            }

	}

        public static void setMode(byte m){
            mode=m;
        }
        public static byte getMode(){
            return mode;
        }

        public static ArrayList<Table> getSelection(){
            return selection;
        }
        public boolean hasSelection(){
            if(this.selection.size() ==0)
                return true;
            return false;
        }
        public static void deleteSelection(){
            for(int i=0; i<tables.length; i++){
                tables[i].setSelected(false);
            }
            selection.clear();
            ctrl.setTableCounter(0);
        }
}
