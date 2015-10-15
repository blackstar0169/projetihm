import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* Classe permetant de créer un composant table et de le dessiner.
* @author Anthony DUPLAT
*/

public class Table extends JComponent {

	public static final byte LIBRE = 0;
	public static final byte RESERVE = 1;
	public static final byte ALAVER = 2;

	private int numero;
	private byte statut;
	private int groupId;
    private boolean selected;
    private int rangee;
    private String nom;


	public Table(int num, byte statut, int gId, String n){
		this.numero = num;
		this.statut = statut;
		this.groupId = gId;
        this.selected=false;
        this.nom = n;
	}

	/**
	* Classe permetant de dessiner la table.
	*/
	public void paintComponent(Graphics g){
		Color couleur;
		//On modifie la couleur en fonction du statut
		if(this.statut == Table.LIBRE){
		    couleur = Color.GREEN;
		}else if(this.statut == Table.ALAVER){
		    couleur = Color.ORANGE;
		}else{
		    couleur = Color.RED;
		}

        if(this.selected){
            couleur = Color.BLUE;
        }

		g.setColor(couleur);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if(nom != null){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
            g.drawString(nom, 10, 20);
        }
	}

	/**
	* Modifie le numero de la table
	* @param n Le nouveau numero de la table
	*/
	public int setNumero(int n){
		return this.numero = n;
	}
	/**
     * Renvoi le numero de la table.
     * @return int Le numero de la table.
     */
	public int getNumero(){
		return this.numero;
	}

	/**
	* Modifie le statut de la table.
	* @param s Le nouveau statut de la table (LIBRE, RESERVE, ALAVER).
	*/
	public void setStatut(byte s){
		this.statut = s;
		repaint();
	}
	/**
     * Renvoi le statut de la table.
     * @return byte Le statut de la table.
     */
	public byte getStatut(){
		return this.statut;
	}

	/**
	* Modifie le numero du groupe de la table.
	* @param id Le nouveau numero du groupe de la table.
	*/
	public void setGroupId(int id){
		this.groupId = id;
                repaint();
	}
	/**
     * Renvoi l'id du groupe de la table.
     * @return int L'id du groupe de la table.
     */
	public int getGroupId(){
		return this.groupId;
	}

	/**
	* Modifie le nom du groupe sur la table.
	* @param n Le nouveau numero nom du groupe.
	*/
    public void setNom(String n){
        this.nom = n;
    }
    /**
     * Renvoi le nom du groupe de la table.
     * @return String Le nom du groupe de la table.
     */
    public String getNom(){
        return this.nom;
    }

    /**
	* Modifie l'état de sélection de la table.
	* @param b True pour sélectionner la table.
	*/
    public void setSelected(boolean b){
        this.selected = b;
        repaint();
    }
    /**
     * Renvoi l'état de sélection de la table de la table.
     * @return boolean true si la table est sélectionnée.
     */
    public boolean isSelected(){
        return this.selected;
    }

    /**
     * Renvoi le numero de rangé de la table.
     * @return int Le nuemero de rangé de la table.
     */
    public int getRangee(){
        return this.rangee;
    }
    /**
	* Modifie le numero de la rangée
	* @param n Le nouveau numero de la rangée
	*/
    public void setRangee(int r){
        this.rangee=r;
    }

}
