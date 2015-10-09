/*

3 applications différentes :

Réservation:
- Affiche les tables dispo/non-dispo ainsi que la liste des réservation à placées

Nettoyage:
- Affiche le plan de la salle avec les atables à nettoyées

Paiement:
- Affiche l'interface de paiment aisin que les statistiques


*/

import javax.swing.*;
import java.awt.*;

public class Test{
	final static String RESERVATION = "Reservatoin";
	final static String NETTOYAGE = "Nettoyage";
	final static String PAIEMENT = "Paiement";

	public static void main(String[] args){
                float[] tab = {20, 35, 52, 25};

		JFrame fenetre = new JFrame("Meal Manager");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(600, 600);

		//Appel des vues
		Histogramme graph = new Histogramme(5, 5, "Temps", "% tables occupées", tab, 4);

		fenetre.add(graph, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
