import javax.swing.*;
import java.awt.*;

public class Nettoyage{

	public static void main(String[] args){
		VueNettoyage view;
		ModeleTable tables;
		ControleurTables ctrl;
		Table[] t;

		JFrame fenetre = new JFrame("Wash Manager");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(800, 600);
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// On charge les model
		tables = new ModeleTable();

		// On charge la vue
		view = new VueNettoyage(tables.getAll());
		t=view.getTables();

		// On charge le controleur
		ctrl = new ControleurTables(tables, t);
		for(int i=0; i<t.length; i++){
			t[i].addMouseListener(ctrl);
		}


		fenetre.add(view, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
