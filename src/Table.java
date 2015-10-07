import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table extends JComponent {

	public static final byte LIBRE = 0;
	public static final byte RESERVE = 1;
	public static final byte ALAVER = 2;

	public int numero;
	public byte statut;
	public int groupId;
	
	public Table(int num, byte statut, int gId){
		this.numero = num;
		this.statut = statut;
		this.groupId = gId;
	}

	public void paintComponent(Graphics g){
		Color couleur;
		if(statut == Table.LIBRE){
			couleur = Color.GREEN;
		}else if(statut == Table.ALAVER){
			couleur = Color.ORANGE;
		}else{
			couleur = Color.RED;
		}

		g.setColor(couleur);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	public int setNumero(int n){
		return this.numero = n;
	}
	public int getNumero(){
		return this.numero;
	}
	
	public void setStatut(byte s){
		this.statut = s;
	}
	public byte getStatut(){
		return this.statut;
	}

}
