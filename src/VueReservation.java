import javax.swing.*;
import java.awt.*;

public class VueReservation extends JPanel{

	private Table[] tables;
	private JTable tableau;
	private JButton valider;
	private JButton annuler;
	private JButton option;
	private JButton nouveauClient;

  public VueReservation(Table[] t, Object[][] reservations){
		JScrollPane scrollPane;
		JPanel panneauMenu = new JPanel();
		JPanel panneauPlan = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		int cnt = 0, leftPad, rightPad;

		this.valider = new JButton("Valider");
		this.annuler = new JButton("Annuler");
		this.option = new JButton("Option");
		this.nouveauClient = new JButton("Nouveau client");


		this.tables = t;
		this.setLayout(new BorderLayout());
		panneauPlan.setLayout(new GridBagLayout());

		//Création du menu
		valider.setBackground(Color.GRAY);
		valider.setEnabled(false);
		annuler.setBackground(Color.GRAY);
		annuler.setEnabled(false);
		nouveauClient.setBackground(Color.BLUE);
		nouveauClient.setForeground(Color.WHITE);
		GridLayout layout = new GridLayout(1,4);
		valider.setPreferredSize(new Dimension(600,100));
		panneauMenu.setLayout(layout);
		panneauMenu.add(nouveauClient);
		panneauMenu.add(valider);
		panneauMenu.add(annuler);
		panneauMenu.add(option);
		panneauMenu.setSize(600, 100);
		this.add(panneauMenu, BorderLayout.SOUTH);

		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,5,10,5);

		// Affichage des tables

		for(int i=0; i<2; i++){
                    for(int j=0; j<10; j++){
                        rightPad = leftPad = 5;
                        try{
                            if(t[cnt].getGroupId() == t[cnt+1].getGroupId() && t[cnt].getGroupId()!=-1){
                               rightPad=0;
                            }
                        }catch(IndexOutOfBoundsException e){}

                        try{
                            if(t[cnt-1].getGroupId() == t[cnt].getGroupId() && t[cnt].getGroupId()!=-1){
                                leftPad=0;
                            }
                        }catch(IndexOutOfBoundsException e){}
                        c.insets = new Insets(10, leftPad, 10, rightPad);
                        c.gridx = j;
                        c.gridy = i;
                        t[cnt].setMinimumSize(new Dimension(10,10));
                        panneauPlan.add(t[cnt], c);
                        cnt++;
                    }
                }

                for(int i=2; i<4; i++){
                    for(int j=0; j<5; j++){
                        c.insets = new Insets(10,5,10,5);
                        try{
                            if(t[cnt].getGroupId() == t[cnt+1].getGroupId()){
                                c.insets = new Insets(10, 5, 10, 0);
                            }else if(t[cnt-1].getGroupId() == t[cnt].getGroupId()){
                                c.insets = new Insets(10, 0, 10, 5);
                            }
                        }catch(IndexOutOfBoundsException e){}

                        c.gridx = j;
                        c.gridy = i;
                        panneauPlan.add(t[cnt], c);
                        cnt++;
                    }
                }

		// Affichage de la liste des réservations
		String[] entetes = {"Nom", "Num Table"};
		this.tableau = new JTable(reservations, entetes);
		scrollPane = new JScrollPane(this.tableau);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(1,1));

		c.insets = new Insets(10, 5, 10, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 5;
		c.gridy = 2;
		c.gridwidth = 5;
		c.gridheight = 4;

		panneauPlan.add(scrollPane, c);
		this.add(panneauPlan, BorderLayout.CENTER);

	}
}
