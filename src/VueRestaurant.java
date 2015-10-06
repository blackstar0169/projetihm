import javax.swing.*;
import java.awt.*;

public class VueRestaurant{
	
	Table[] tables;
	JPanel plan;

  
  public VueRestaurant(Table[] t){
		JFrame fenetre = new JFrame("Salut");		
		JPanel plan = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		int cnt = 0;

		this.tables = t;

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(200, 200);

		plan.setLayout(new GridBagLayout());

		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,5,10,5);
		
		for(int i=0; i<2; i++){
			for(int j=0; j<10; j++){
				c.gridx = j;
				c.gridy = i;
				t[cnt].setMinimumSize(new Dimension(10,10));
				plan.add(t[cnt], c);
				cnt++;
			}
		}
		
		for(int i=2; i<4; i++){
			for(int j=0; j<5; j++){
				c.gridx = j;
				c.gridy = i;
				plan.add(t[cnt], c);
				cnt++;
			}
		}
		
		
		fenetre.add(plan, BorderLayout.CENTER);
		fenetre.setVisible(true);
	}
}
