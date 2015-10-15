import javax.swing.*;
import java.awt.*;
import java.util.*;


/**
 * Classe Main pour le système de paiement
 * @author Anthony DUPLAT
 */

public class Paiement{

	public static void main(String[] args){
		VueLogin logView;
                VueStatistiques statView;
                VuePaiement payView;
                ControleurPaiement ctrl;
                ControleurTables tableCtrl;
		ModeleTable tables;

		JFrame fenetre = new JFrame("Payday Manager");
                JPanel panneauPrincipal = new JPanel();
                CardLayout cartes = new CardLayout();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(800, 600);
		fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

                panneauPrincipal.setLayout(cartes);

		// On charge les model
		tables = new ModeleTable();
                Table[] t=tables.getAll();
                //login = new ModeleLogin()

		// On charge la vue
		logView = new VueLogin();
                statView = new VueStatistiques();
                payView = new VuePaiement(t);

                panneauPrincipal.add(logView, "login");
                panneauPrincipal.add(statView, "statistiques");
                panneauPrincipal.add(payView, "paiement");
                cartes.first(panneauPrincipal);
                //Chargement du controleur
                ctrl = new ControleurPaiement(panneauPrincipal, cartes, t, logView.getComponents(), payView.getPayComponents(), statView.getStatComponents(), payView);
                tableCtrl = new ControleurTables(tables, t, ctrl);
                tableCtrl.setMode(ControleurTables.PAIEMENT);

                logView.getLoginButton().addActionListener(ctrl);
                payView.addActionController(ctrl);
                statView.addActionController(ctrl);

                payView.addMouseController(tableCtrl);

                fenetre.add(panneauPrincipal, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
