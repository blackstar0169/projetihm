import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
* Classe gérant la vue des réservations.
* @author Anthony DUPLAT
*/

public class VueReservation extends JPanel{

    private Table[] tables;
    private JTable tableau;
    private JButton valider;
    private JButton annuler;
    private JButton option;
    private JButton nouveauClient;
    private Object[][] reservations;
    private DefaultTableModel model;

    public VueReservation(Table[] t){
        this.valider = new JButton("Valider");
        this.annuler = new JButton("Annuler");
        this.option = new JButton("Option");
        option.setName("option");
        this.nouveauClient = new JButton("Nouveau client");
        nouveauClient.setName("nouveauClient");
        this.tables = t;
        this.reservations = null;
        String[] entetes = {"N°Groupe", "Nom", "Nombre de personnes", "Date", "Creneau"};
        this.model = new DefaultTableModel(null, entetes);
        this.tableau = new JTable(model);
        this.init();
    }

    /**
     * Permet d'initialiser la vue. Peut être appelée pour rafraichir la vue.
     */
    public void init(){
        this.removeAll();
        JScrollPane scrollPane;
        JPanel panneauMenu = new JPanel();
        JPanel panneauPlan = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        int cnt = 0, leftPad, rightPad;


        this.setLayout(new BorderLayout());
        panneauPlan.setLayout(new GridBagLayout());

        //Création du menu
        valider.setBackground(Color.GRAY);
        valider.setEnabled(false);
        valider.setName("valider");
        annuler.setBackground(Color.RED);
        annuler.setName("annuler");
        nouveauClient.setBackground(Color.BLUE);
        nouveauClient.setForeground(Color.WHITE);
        GridLayout layout = new GridLayout(1,4);
        valider.setPreferredSize(new Dimension(600,100));
        panneauMenu.setLayout(layout);
        panneauMenu.add(nouveauClient);
        panneauMenu.add(valider);
        panneauMenu.add(annuler);
        panneauMenu.add(option);
        panneauMenu.setSize(600, 100);
        this.add(panneauMenu, BorderLayout.SOUTH);

        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,5,10,5);

        // Affichage des tables

        for(int i=0; i<2; i++){
            for(int j=0; j<10; j++){
                rightPad = leftPad = 5;
                //Permet d'eviter de sortitr du tableau des tables
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
                tables[cnt].setRangee(i);
                panneauPlan.add(tables[cnt], c);
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
                    if(tables[cnt-1].getGroupId() == tables[cnt].getGroupId() && tables[cnt].getGroupId()!=-1){
                        leftPad=0;
                    }
                }catch(IndexOutOfBoundsException e){}
                c.insets = new Insets(10, leftPad, 10, rightPad);
                c.gridx = j;
                c.gridy = i;
                tables[cnt].setRangee(i);
                panneauPlan.add(tables[cnt], c);
                cnt++;
            }
        }

        // Affichage de la liste des réservations
        scrollPane = new JScrollPane(this.tableau);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1,1));

        c.insets = new Insets(10, 5, 10, 5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 5;
        c.gridy = 2;
        c.gridwidth = 5;
        c.gridheight = 4;

        panneauPlan.add(scrollPane, c);
        this.add(panneauPlan, BorderLayout.CENTER);

        // Rafraichi la vue
        revalidate();
        repaint();

    }

    /**
     * Ajoute les listeners aux éléments qui en ont besoin.
     * @param c L' ActionListener qui surveillera les boutons.
     */
    public void addActionControleur(ControleurReservation c){
        valider.addActionListener(c);
        annuler.addActionListener(c);
        option.addActionListener(c);
        nouveauClient.addActionListener(c);
        tableau.getSelectionModel().addListSelectionListener(new ListeReservationListener(c, this.tableau));
    }

    /**
     * Renvoi les composants pouvant être manipulés.
     * @return Component[] contenant mes composants nécéssitants un traitement.
     */
    public Component[] getMyComponents(){
        JComponent[] c = new JComponent[3];
        c[0] = valider;
        c[1] = nouveauClient;
        c[2] = tableau;
        return c;
    }

    /**
     * Supprime une ligne du tableau.
     * @param id L'id de la ligne à supprimer.
     */
    public void deleteRow(int id){
        this.model.removeRow(id);
        revalidate();
        repaint();
    }

    /**
     * Modifie les tables à afficher.
     * @param t le tableau de table à afficher.
     */
    public void setTables(Table[] t){
        this.tables=t;
    }

    /**
     * Modifie le listener du tableau.
     * @param c Le controleur chargé d'écouter les actions sur le tableau de réservations.
     */
    public void setTableauListener(ControleurReservation c){
        ListeReservationListener lrl = new ListeReservationListener(c, tableau);
    }

    /**
     * Modifie la liste des réservations à afficher dans le tableau.
     * @param r La liste des réservations à afficher.
     */
    public void setReservations(ModeleReservation[] r){
        //Si il y a des réservations à afficher
        if(r!=null){
            Object[][] obj = new Object[r.length][5];
            for(int i=0; i<r.length; i++){
               obj[i][0] = r[i].getId();
               obj[i][1] = r[i].getNom();
               obj[i][2] = r[i].getPlaces();
               obj[i][3] = r[i].getDate();
               obj[i][4] = r[i].getCreneau();
            }
            this.reservations = obj;
            String[] entetes = {"N°Groupe", "Nom", "Nombre de personnes", "Date", "Creneau"};
            this.model = new DefaultTableModel(obj, entetes);
            this.tableau = new JTable(model);
            this.init();
            revalidate();
            repaint();
        }
        else{
            String[] entetes = {"N°Groupe", "Nom", "Nombre de personnes", "Date", "Creneau"};
            this.model = new DefaultTableModel(null, entetes);
            this.tableau = new JTable(model);
            this.init();
            revalidate();
            repaint();
        }
    }

}
