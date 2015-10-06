/* 
Organisation :  

1.Menu :
	Accueil :
		- Placer client => Plan des tables et liste des réservations dans 1h => clique sur les tables pour selectionner => menu avec un champ nouveau client et la liste des reservation a placé => message de succès (retour a l'accueil/plan)
		- Chercher réservation => Liste des réservations => Plan avec table(s) réservée(s) démarquée(s)
		- Payer => Montant + Moyen de paiment (radio button) 
		- Statistiques
		- Nettoyage => plan des tables a nettoyés => clique sur une table à nettoyer => popup confirmation
	
	

2.Code :

Main : 
Appelle toute les vues et place dans le card layout
Appelle tout les Model

TableControlleur :
- params : TableModel, ReservationModel, Tables[]

initialise les tables retournées par la vue VuesTables
initialise la liste des reservations à placés

 
*/

import javax.swing.*;
import java.awt.*;

public class Test{
	public static void main(String[] args){
		JFrame fenetre = new JFrame("Meal Manager");		
		CardLayout card = new CardLayout();
		//Appel des vues
		
		//Appel des models

		//Appel des controleurs
		
		
		VueRestaurant vue;
		Table[] t = new Table[30];
		
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(200, 200);
		

		for(int i=0; i<30; i++){
			t[i] = new Table(i, (byte)(i%2)); 
		}
		vue = new VueRestaurant(t);

		fenetre.add(vue, BorderLayout.CENTER);
		fenetre.setVisible(true);


	}
}
