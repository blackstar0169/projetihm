import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class VueReservation extends JPanel{

    private Table[] tables;
    private JTable tableau;
    private JButton valider;
    private JButton annuler;
    private JButton option;
    private JButton nouveauClient;
    private Object[][] reservations;
    private DefaultTableModel model;

    public VueReservation(Table[] t, Object[][] res){
        this.valider = new JButton("Valider");
        this.annuler = new JButton("Annuler");
        this.option = new JButton("Option");
        this.nouveauClient = new JButton("Nouveau client");
        nouveauClient.setName("nouveauClient");
        this.tables = t;
        this.reservations = res;
        String[] entetes = {"Nom", "N°Groupe", "Nombre de personnes"};
        this.model = new DefaultTableModel(res, entetes);
        this.tableau = new JTable(model);
        this.init();
    }

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
                try{
                    if(tables[cnt].getGroupId() == tables[cnt+1].getGroupId() && tables[cnt].getGroupId()!=-1){
                       rightPad=0;
                    }
                }catch(IndexOutOfBoundsException e){}

                try{
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
                c.insets = new Insets(10,5,10,5);
                try{
                    if(tables[cnt].getGroupId() == tables[cnt+1].getGroupId() && tables[cnt].getGroupId()!=-1){
                        c.insets = new Insets(10, 5, 10, 0);
                    }else if(tables[cnt-1].getGroupId() == tables[cnt].getGroupId() && tables[cnt].getGroupId()!=-1){
                        c.insets = new Insets(10, 0, 10, 5);
                    }
                }catch(IndexOutOfBoundsException e){}

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

        revalidate();
        repaint();

    }

    public void addActionControleur(ControleurReservation c){
        valider.addActionListener(c);
        annuler.addActionListener(c);
        option.addActionListener(c);
        nouveauClient.addActionListener(c);
        tableau.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                DefaultTableModel m = (DefaultTableModel)tableau.getModel();
                int row = tableau.getSelectedRow();
                if(row>=0){
                    System.out.println(row);
                    int gid = (int)tableau.getValueAt(row, 1);
                    String nom = (String)tableau.getValueAt(row, 0);
                    int nbrP = (int)tableau.getValueAt(row, 2);
                    c.placerReservation(gid, nom, nbrP);
                }
            }
        });
    }

    public Component[] getMyComponents(){
        JComponent[] c = new JComponent[3];
        c[0] = valider;
        c[1] = nouveauClient;
        c[2] = tableau;
        return c;


    }
}
