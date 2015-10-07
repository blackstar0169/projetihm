

public class ModeleTable{
													        
	private int numero;
	private byte statut;
	private int groupId;
	private byte nombrePersonnes;

	public ModeleTable(/*int n, byte s, int g, byte nbrP*/){
		this.numero = 1;
		this.statut = 1;
		this.groupId = 1;
		this.nombrePersonnes = 1;
	}

	public void setGroupId(int id){
		this.groupId = id;
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

	public void setNombrePersonnes(byte n){
		this.nombrePersonnes = n;
	}
	public int getNombrePersonnes(){
		return this.nombrePersonnes;
	}

	public Table[] getAll(){
		Table[] res = new Table[30];
		for(int i=0; i<30; i++){
			res[i] = new Table(i, (byte)(i%2), i); 
		}
		return res;
	}
	/*public ModeleTable findOrFail(){

		return ;
	}*/

}       

