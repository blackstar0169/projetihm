import java.util.*;
import javax.swing.JPanel;

/**
 * Classe gérant le système d'actualisation des tables
 * @author Anthony DUPLAT
 */

public class Refresh extends TimerTask{
    private Table[] tables;
    private VueReservation view;
    private VuePaiement paiement;

    public Refresh(Table[] t){
        this.tables = t;
        this.view=null;
        this.paiement=null;
    }
    public Refresh(Table[] t, VuePaiement p){
        this.tables = t;
        this.paiement=p;
        this.view=null;
    }
    public Refresh(Table[] t, VueReservation v){
        this.tables = t;
        this.view = v;
        this.paiement = null;
    }



    @Override
    public void run() {
        ModeleTable modele = new ModeleTable();
        Table[] t= modele.getAll();
        //Pour ne pas détruire les références des tables entre la vue et le controleur
        for(int i=0; i<t.length; i++){
            tables[i].setNom(t[i].getNom());
            tables[i].setGroupId(t[i].getGroupId());
            tables[i].setStatut(t[i].getStatut());
        }
        // Si on a fournit une VueReservation on l'actualise
        if(view != null){
            view.init();
        }
        // Si on a fournit une VuePaiement on l'actualise
        if(paiement !=null){
            paiement.init();
        }
    }

}

