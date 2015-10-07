import javax.swing.*;
import java.awt.*;

public class VueReservation extends JPanel{
	
	private Table[] tables;
  
  public VueReservation(Table[] t, Object[][] reservations){
		JScrollPane tableau;
		GridBagConstraints c = new GridBagConstraints();
		int cnt = 0;

		this.tables = t;

		
		this.setLayout(new GridBagLayout());

		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,5,10,5);

		// Affichage des tables
		
		for(int i=0; i<2; i++){
			for(int j=0; j<10; j++){
				c.gridx = j;
				c.gridy = i;
				t[cnt].setMinimumSize(new Dimension(10,10));
				this.add(t[cnt], c);
				cnt++;
			}
		}
		
		for(int i=2; i<4; i++){
			for(int j=0; j<5; j++){
				c.insets = new Insets(10,5,10,5);
				if(i==2 && j==1){
					c.insets = new Insets(10, 0, 10, 5);
				}else if(i==2 && j==0){
					c.insets = new Insets(10, 5, 10, 0);
				}
				
				c.gridx = j;
				c.gridy = i;
				this.add(t[cnt], c);
				cnt++;
			}
		}

		// Affichage de la liste des réservations
		String[] entetes = {"Nom", "Num Table"};
		tableau = new JScrollPane();
		tableau.setViewportView(new JTable(reservations, entetes));
		tableau.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tableau.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		panneau.add(tableau, BorderlLayout.CENTER);
		c.insets = new Insets(5, 0, 10, 5);
		c.gridx = 5;
		c.gridy = 2;
		c.gridwidth = 5;
		c.gridheight = 4;
		this.add(panneau, c); 
		
		
	}
}
