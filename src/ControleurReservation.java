import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

public class ControleurReservation extends Controleur implements ActionListener{

    private int peoplesToPlace;
    private Table[] tables;
    private JButton valider;

    public ControleurReservation(ModeleTable m, Table[] t,  Component[] rC){
        setComponents(rC);
        this.tableCounter = 0;
        this.peoplesToPlace = 5;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getName()=="annuler"){
                ControleurTables.deleteSelection();
                this.tableCounter=0;
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
            }
            else if(b.getName()=="valider"){
                ArrayList<Table> tab = ControleurTables.getSelection();
                for(int i=0; i<tab.size(); i++){
                    tab.get(i).setStatut(Table.ALAVER);
                    tab.get(i).setGroupId(-1);
                }
                ControleurTables.deleteSelection();
                this.tableCounter=0;
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
            }
        }
    }

    public void setComponents(Component[] c){
        for(int i=0; i<c.length; i++){
            if(c[i].getName()=="valider")
                this.valider = (JButton)c[i];
        }
    }

    public boolean setTableCounter(int n){
        if((n-1)*2<peoplesToPlace){
            this.tableCounter =n;
            return true;
        }
        return false;
    }
}
