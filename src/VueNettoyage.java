import javax.swing.*;
import java.awt.*;
import java.lang.IndexOutOfBoundsException;

public class VueNettoyage extends JPanel{

	private Table[] tables;

	public VueNettoyage(){
	}

	public VueNettoyage(Table[] t){
            this.tables = t;
            init();
        }

        public void init(){
            removeAll();
            GridBagConstraints c = new GridBagConstraints();
            int cnt = 0, rightPad, leftPad;
            this.setLayout(new GridBagLayout());

            c.weightx = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(10,5,10,5);

	    // Affichage des tables
            for(int i=0; i<2; i++){
                for(int j=0; j<10; j++){
                    rightPad = leftPad = 5;
                    try{
                        if(tables[cnt].getGroupId() == tables[cnt+1].getGroupId() && tables[cnt].getGroupId()!=-1){
                           rightPad=0;
                        }
                    }catch(IndexOutOfBoundsException e){}

                    try{
                        if(tables[cnt-1].getGroupId() == tables[cnt].getGroupId() && tables[cnt].getGroupId()!=-1){
                            leftPad=0;
                        }
                    }catch(IndexOutOfBoundsException e){}
                    c.insets = new Insets(10, leftPad, 10, rightPad);
                    c.gridx = j;
                    c.gridy = i;
                    tables[cnt].setMinimumSize(new Dimension(10,10));
                    this.add(tables[cnt], c);
                    cnt++;
                }
            }

            for(int i=2; i<4; i++){
                for(int j=0; j<5; j++){
                    c.insets = new Insets(10,5,10,5);
                    try{
                        if(tables[cnt].getGroupId() == tables[cnt+1].getGroupId() && tables[cnt].getGroupId()!=-1){
                            c.insets = new Insets(10, 5, 10, 0);
                        }else if(tables[cnt-1].getGroupId() == tables[cnt].getGroupId() && tables[cnt].getGroupId()!=-1){
                            c.insets = new Insets(10, 0, 10, 5);
                        }
                    }catch(IndexOutOfBoundsException e){}

                    c.gridx = j;
                    c.gridy = i;
                    this.add(tables[cnt], c);
                    cnt++;
                }
            }
            revalidate();
            repaint();
	}

	public void setTables(Table[] t){
		this.tables = t;
		repaint();
	}
	public Table[] getTables(){
		return this.tables;
	}
}
