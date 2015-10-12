import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;


public class ListeReservationListener implements ListSelectionListener{

    private ControleurReservation ctrl;
    private JTable tableau;

    public ListeReservationListener(ControleurReservation c, JTable t){
        this.ctrl=c;
        this.tableau = t;
    }


    /**
    * Reagit au clique sur les tables
    * @param e le
    */
    public void valueChanged(ListSelectionEvent me){
        DefaultTableModel m = (DefaultTableModel)tableau.getModel();
        int row = tableau.getSelectedRow();
        if(row>=0){
            int gid = (int)tableau.getValueAt(row, 1);
            String nom = (String)tableau.getValueAt(row, 0);
            int nbrP = (int)tableau.getValueAt(row, 2);
            ctrl.placerReservation(row, gid, nom, nbrP);
        }
    }

}
