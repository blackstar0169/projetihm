public class ControleurTable extends ActionListener{
	public ControleurTable(){
		VueRestaurant vue;
		Table[] t = new Table[30];

		for(int i=0; i<30; i++){
			t[i] = new Table(i, (byte)(i%2)); 
		}
		vue = new VueRestaurant(t);

	}
}
