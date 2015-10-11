import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table extends JComponent {

	public static final byte LIBRE = 0;
	public static final byte RESERVE = 1;
	public static final byte ALAVER = 2;

	private int numero;
	private byte statut;
	private int groupId;
        private boolean selected;
        private int rangee;


	public Table(int num, byte statut, int gId){
		this.numero = num;
		this.statut = statut;
		this.groupId = gId;
                this.selected=false;
	}

	public void paintComponent(Graphics g){
		Color couleur;
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
	}

	public int setNumero(int n){
		return this.numero = n;
	}
	public int getNumero(){
		return this.numero;
	}

	public void setStatut(byte s){
		this.statut = s;
		repaint();
	}
	public byte getStatut(){
		return this.statut;
	}

	public void setGroupId(int id){
		this.groupId = id;
                repaint();
	}
	public int getGroupId(){
		return this.groupId;
	}

        public void setSelected(boolean b){
            this.selected = b;
            repaint();
        }
        public boolean isSelected(){
            return this.selected;
        }

        public int getRangee(){
            return this.rangee;
        }
        public void setRangee(int r){
            this.rangee=r;
        }

}
