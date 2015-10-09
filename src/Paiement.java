import javax.swing.*;
import java.awt.*;

public class Paiement{

	public static void main(String[] args){
		VueLogin logView;
                VueStatistiques statView;
                VuePaiement payView;
                ControleurPaiement ctrl;
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
                //login = new ModeleLogin()

		// On charge la vue
		logView = new VueLogin();
                statView = new VueStatistiques();
                payView = new VuePaiement(tables.getAll());

		// On charge le controleur

                panneauPrincipal.add(logView, "login");
                panneauPrincipal.add(statView, "statistiques");
                panneauPrincipal.add(payView, "paiement");
                cartes.first(panneauPrincipal);

                ctrl = new ControleurPaiement(panneauPrincipal, cartes, logView.getLoginTextField(), logView.getLoginLabel());
                logView.getLoginButton().addActionListener(ctrl);

                fenetre.add(panneauPrincipal, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
