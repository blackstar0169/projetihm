import java.swing.*;

public class TableModel{
													        
	private int numero;
	private byte statut;
	private int groupId;
	private byte nombrePersonnes;

	public TablesModel(int n, byte s, int g, byte nbrP){
		this.numero = n;
		this.statut = s;
		this.groupeId = g;
		this.nombrePersonnes = nbrP;
	}

	public void setGroupId(int id){
		this.groupeId = id;
	}
	public int getGroupId(){
		return this.groupId;
	}
																														                
	public void setStatut(byte s){
		this.statut = s;
	}
	public byte getStatut(){
		return this.statut;
	}

	public void setNumero(int n){
		this.numero = n;
	}
	public int getNumero(){
		return this.numero;
	}

	public void setNombrePersonnes(int n){
		this.nombrePersonnes = n;
	}
	public int getNombrePersonnes(){
		return this.nombrePersonnes;
	}

	public TablesModel[] getAll(){
		return res;
	}
	public TablesModel findOrFail(){

		return res;
	}

}       

