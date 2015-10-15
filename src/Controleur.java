/**
 * Classe mère des controlleurs facilitant l'implémentation de la sélection des tables.
 * @author Anthony DUPLAT
 */

public class Controleur{


    protected int tableCounter;


    /**
     * Modifie le nombre de tables sélectionnées.
     * @param n Le nombre de table sélectionné.
     * @return true Si la sélection peux se faire.
     */
    public boolean setTableCounter(int n){
       this.tableCounter = n;
       return true;
    }
    /**
     * Récupère le nombre de tables sélectionnées.
     * @return le nombre de table sélectionner. 
     */
    public int getTableCounter(){
        return this.tableCounter;
    }
}
