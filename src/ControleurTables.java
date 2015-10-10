import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;


public class ControleurTables implements MouseListener{
	public static final byte NETTOYAGE = 1;
        public static final byte RESERVATION = 2;
        public static final byte PAIEMENT = 3;

        private static Table[] tables;
        private VuePaiement vue;
	private ModeleTable model;
        private static ArrayList<Table> selection;
        private byte mode;
        private static Controleur ctrl;

	public ControleurTables(ModeleTable m, Table[] t, Controleur c){
	    this.model = m;
	    this.tables = t;
            this.ctrl = c;
            this.selection = new ArrayList<Table>();
	}
        public ControleurTables(ModeleTable m, Table[] t){
            this.model = m;
	    this.tables = t;
            this.mode = 1;//Nettoyage
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
                    for(int i=0; i<tables.length; i++){
                        if(tables[i].getGroupId()==t.getGroupId()){
                            tables[i].setStatut(Table.LIBRE);
                        }
                    }
                    //Update le model
                }
                else if(t.isSelected()==false && t.getStatut() == Table.LIBRE && this.mode==ControleurTables.RESERVATION){
                    if(this.ctrl.setTableCounter(this.selection.size()+1)){ //On verifie si on a encore des table a sélectionner
                        t.setSelected(true);
                        this.selection.add(t);
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
                else if(t.isSelected()){
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

        public void setMode(byte m){
            this.mode=m;
        }
        public byte getMode(){
            return this.mode;
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
