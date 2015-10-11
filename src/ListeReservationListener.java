import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;


public class ListeReservationListener implements {

    private ControleurReservation ctrl;

    public ListeReservationListener(ControleurReservation c){
        this.ctrl=c;
    }


    /**
    * Reagit au clique sur les tables
    * @param e le
    */
    public void valueChanged(ListSelectionEvent me){
        if(me.getSource() instanceof JTable){
            JTable tableau = (JTable)me.getSource();
            DefaultTableModel model = (DefaultTableModel)tableau.getModel();
            int row = tableau.rowAtPoint(me.getPoint());
            if(row>=0){
                int gid = (int)tableau.getValueAt(row, 1);
                String nom = (String)tableau.getValueAt(row, 0);
                int nbrP = (int)tableau.getValueAt(row, 2);
                ctrl.placerReservation(gid, nom, nbrP);
                model.removeRow(row);
            }
        }
    }

}
