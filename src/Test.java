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
	

		JFrame fenetre = new JFrame("Meal Manager");		
		CardLayout card = new CardLayout();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(200, 200);
		
		//Appel des vues
		VueReservation resView = new VueReservation();
		VueNettoyage washView = new VueNettoyage();
		VuePaiement payView = new VuePaiement();
		
		//Appel des models

		//Appel des controleurs
		
		
		Table[] t = new Table[30];
		for(int i=0; i<30; i++){
			t[i] = new Table(i, (byte)(i%2)); 
		}
		vue = new VueRestaurant(t);


		card.add(resView, Test.RESERVATION);
		card.add(washView, Test.NETTOYAGE);
		card.add(View, Test.NETTOYAGE);
		
		fenetre.add(card, BorderLayout.CENTER);
		fenetre.setVisible(true);


	}
}
