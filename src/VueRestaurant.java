import javax.swing.*;
import java.awt.*;

public class VueRestaurant extends JPanel{
	
	private Table[] tables;
  
  public VueRestaurant(Table[] t){
		GridBagConstraints c = new GridBagConstraints();
		int cnt = 0;

		this.tables = t;

		
		this.setLayout(new GridBagLayout());

		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,5,10,5);
		
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
				c.gridx = j;
				c.gridy = i;
				this.add(t[cnt], c);
				cnt++;
			}
		}
		
		
	}
}
