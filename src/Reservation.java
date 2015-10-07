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
			{"DURANT", 10},
			{"MICHEL", 23},
			{"CHANIAT", 12},
			{"SUPER", 15},
			{"TARTARE", 2},
			{"DUCLOUX", 86}
		};

		// On charge la vue 
		view = new VueReservation(tables.getAll(), reservation);

		// On charge le controleur
		// ctrl = new ControleurReservation();

		fenetre.add(view, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
