import javax.swing.*;
import java.awt.*;

/**
 * Classe Main pour le système de réservation
 * @author Anthony DUPLAT
 */


public class Reservation{

	public static void main(String[] args){
		VueReservation view;
		ModeleTable tables;
		ModeleReservation res;

		JFrame fenetre = new JFrame("Meal Manager");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(800, 600);
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// On charge les model
		tables = new ModeleTable();
		res = new ModeleReservation();
		//reservation = new ModeleReservation();
		Table[] t =tables.getAll();

		// On charge la vue
		view = new VueReservation(t);

		// On charge le controleur

		ControleurReservation ctrl=new ControleurReservation(tables, res, t, view.getMyComponents(), view);
		ControleurTables tableCtrl = new ControleurTables(tables, t, ctrl);
		tableCtrl.setMode(ControleurTables.NONE);
        //ListeReservationListener lrl = new ListeReservationListener(ctrl);

		view.addActionControleur(ctrl);
		// Mise à l'écoute des tables
		for(int i=0; i<t.length; i++){
			t[i].addMouseListener(tableCtrl);
		}

		fenetre.add(view, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
