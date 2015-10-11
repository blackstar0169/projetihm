import javax.swing.*;
import java.awt.*;

public class Reservation{

	public static void main(String[] args){
		VueReservation view;
		ModeleTable tables;

		JFrame fenetre = new JFrame("Meal Manager");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(800, 600);
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// On charge les model
		tables = new ModeleTable();
		//reservation = new ModeleReservation();
		Object[][] reservation = {
			{"DURANT", 10, 2},
			{"MICHEL", 23, 2},
			{"CHANIAT", 12, 5},
			{"SUPER", 15, 3},
			{"TARTARE", 2, 1},
			{"DUCLOUX", 86, 8}
		};
                Table[] t =tables.getAll();

		// On charge la vue
		view = new VueReservation(t, reservation);

		// On charge le controleur

                ControleurReservation ctrl=new ControleurReservation(tables, t, view.getMyComponents(), view);
		ControleurTables tableCtrl = new ControleurTables(tables, t, ctrl);
                tableCtrl.setMode(ControleurTables.NONE);
                //ListeReservationListener lrl = new ListeReservationListener(ctrl);

                view.addActionControleur(ctrl);

                for(int i=0; i<t.length; i++){
		    t[i].addMouseListener(tableCtrl);
		}

		fenetre.add(view, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
