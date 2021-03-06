import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

/**
* Classe du listener des lignes du tableau de réservation.
* @author Anthony DUPLAT
*/

public class ListeReservationListener implements ListSelectionListener{

    private ControleurReservation ctrl;
    private JTable tableau;

    public ListeReservationListener(ControleurReservation c, JTable t){
        this.ctrl=c;
        this.tableau = t;
    }


    /**
    * Reagit au clique sur les tables
    * @param me L'evenement de la souris
    */
    public void valueChanged(ListSelectionEvent me){
        DefaultTableModel m = (DefaultTableModel)tableau.getModel();
        int row = tableau.getSelectedRow();
        if(row>=0){
            int gid = (int)tableau.getValueAt(row, 0);
            String nom = (String)tableau.getValueAt(row, 1);
            int nbrP = (int)tableau.getValueAt(row, 2);
            ctrl.placerReservation(row, gid, nom, nbrP);
        }
    }

}
