import javax.swing.*;
import java.awt.*;
import java.lang.IndexOutOfBoundsException;

/**
* Classe gérant la vue du nettoyage.
* @author Anthony DUPLAT
*/

public class VueNettoyage extends JPanel{

	private Table[] tables;

	public VueNettoyage(){
	}

	public VueNettoyage(Table[] t){
        this.tables = t;
        init();
    }

    /**
     * Permet d'initialiser la vue. Peut être appelée pour rafraichir la vue.
     */
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
                    // Si la table de droite à le même id et que cet id est différent de -1 on les colles ensembles
                    if(tables[cnt].getGroupId() == tables[cnt+1].getGroupId() && tables[cnt].getGroupId()!=-1){
                       rightPad=0;
                    }
                }catch(IndexOutOfBoundsException e){}

                try{
                    // Si la table de gauche à le même id et que cet id est différent de -1 on les colles ensembles
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
               rightPad = leftPad = 5;
                try{
                    // Si la table de droite à le même id et que cet id est différent de -1 on les colles ensembles
                    if(tables[cnt].getGroupId() == tables[cnt+1].getGroupId() && tables[cnt].getGroupId()!=-1)
                        rightPad=0;
                }catch(IndexOutOfBoundsException e){}
                try{
                    // Si la table de gauche à le même id et que cet id est différent de -1 on les colles ensembles
                    if(tables[cnt-1].getGroupId() == tables[cnt].getGroupId() && tables[cnt].getGroupId()!=-1)
                        leftPad=0;
                }catch(IndexOutOfBoundsException e){}
                c.insets = new Insets(10, leftPad, 10, rightPad);
                c.gridx = j;
                c.gridy = i;
                this.add(tables[cnt], c);
                cnt++;
            }
        }
        revalidate();
        repaint();
	}

    /**
     * Modifie les tables à afficher.
     * @param t le tableau de tables à afficher.
     */
	public void setTables(Table[] t){
		this.tables = t;
		repaint();
	}

    /**
     * Récupère les tables affichée.
     * @return Table[] le tableau de tables affichées.
     */
	public Table[] getTables(){
		return this.tables;
	}
}
