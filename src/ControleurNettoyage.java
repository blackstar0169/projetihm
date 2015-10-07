import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.*;


public class ControleurNettoyage implements MouseListener{
	private Table[] tables;
	private ModeleTable model;
	
	public ControleurNettoyage(ModeleTable m, Table[] t){
		this.model = m;
		this.tables = t;
	}

	/**
	* nop
	*/
	public void mousePressed(MouseEvent e) {
	}

	/**
	*	nop
	*/
	public void mouseReleased(MouseEvent e) {
	}

	/**
	*	 nop
	*/
	public void mouseEntered(MouseEvent e) {
	}

	/**
	*	 nop
	*		
	*/
	public void mouseExited(MouseEvent e) {
	}

  /**
	* Reagit au clique sur les differents boutons
	* @param e le MouseEvent genere
	*/
	public void mouseClicked(MouseEvent me){
		Table t = (Table)me.getSource();
		if(t.getStatut() == Table.ALAVER)
			t.setStatut(Table.LIBRE);

		
	}

}
