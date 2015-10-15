import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Classe gérant les actions dans l'application de paiement.
 * @author Anthony DUPLAT
 */

public class ControleurPaiement extends Controleur implements ActionListener{
    private JPasswordField passTextField;
    private JTextField payTextField;
    private JTextField dateTextField;

    private JComboBox serviceComboBox;

    private CardLayout cartes;
    private JPanel panneau;

    private JLabel loginLabel;
    private JLabel chiffreAffaire;

    private JButton valider;

    private JRadioButton especes;
    private JRadioButton cheque;
    private JRadioButton cb;
    private JRadioButton service;
    private JRadioButton date;

    private Histogramme graph;

    private ModeleTable modeleTable;

    private Table[] tables;

    private long difTemps;

    public ControleurPaiement(JPanel p, CardLayout cl, Table[] t, Component[] lC, Component[] pC, Component[] sC, VuePaiement v){
        this.modeleTable = new ModeleTable();
        setLogComponents(lC);
        setPayComponents(pC);
        setStatComponents(sC);
        this.tables = t;
        this.panneau = p;
        this.cartes = cl;
        this.tableCounter = 0;
        this.difTemps = 0;

        //On raffraichi toutes les minutes
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new Refresh(this.tables, v), 0, 30000);
    }

    /**
     * Réagit au clique de la souris sur un bouton
     * @param e L'ActionEvent généré
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getName()=="statTab"){ //Si on clique sur l'onglet statistiques
                cartes.show(panneau, "statistiques");
            }
            else if(b.getName()=="payTab") { // Si on clique sur l'onglet de paiement
                cartes.show(panneau, "paiement");
            }
            else if(b.getName()=="loginButton"){ // Si on clique sur le bonton de login
                char[] input = passTextField.getPassword();
                String pass = new String("root"); // Le mot de passe
                if(pass.equals(new String(input))){
                    cartes.show(panneau, "paiement");
                    loginLabel.setText("");
                }
                else
                    loginLabel.setText("Mot de passe incorrect");

                Arrays.fill(input, '0');
                passTextField.selectAll();
            }
            else if(b.getName()=="annuler"){ // Si clique sur annuler
                // On réserte la sélection et on déselectionne les tables
                ControleurTables.deleteSelection();
                this.tableCounter=0;
                this.payTextField.setText("");
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
            }
            else if(b.getName()=="valider"){ // Si on clique sur valider

                //On récupère la date
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, (int)this.difTemps);
                //On récupère le mode de paiement sélectionné
                String type=new String("carte bleue");
                if(especes.isSelected()){
                    type = "especes";
                }else if(cheque.isSelected()){
                    type = "cheque";
                }
                try{ // On verifie que le prix rentré est correct 
                    //On met tout dans le meme try pour annuler l'insertion de la commande dans la bdd en cas d'erreur
                    float prix = Float.parseFloat(payTextField.getText());
                    ModelePaiement mP= new ModelePaiement();
                    mP.insert(cal, prix, type);
                    // On recupère la selection
                    ArrayList<Table> tab = ControleurTables.getSelection();
                    // On met toutes les tables à laver
                    for(int i=0; i<tab.size(); i++){
                        tab.get(i).setStatut(Table.ALAVER);
                        tab.get(i).setNom(null);
                    }
                    // On déselectionne les tables
                    ControleurTables.deleteSelection();
                    this.tableCounter=0;
                    this.payTextField.setText("");
                    this.valider.setEnabled(false);
                    this.valider.setBackground(Color.GRAY);
                    //On insère le paiement dans la bdd 
                    modeleTable.updateTables(this.tables);
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,
                        "Veuillez entrez un rpix valide.",
                        "Erreur prix",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(b.getName()=="trier"){ // Si on appuie sur trier
                float ca = -1;
                ModelePaiement mP = new ModelePaiement();
                if(service.isSelected()){ //Si on selection le chiffre d'affaire par service
                    ca = mP.getCAService((String)serviceComboBox.getSelectedItem()); // On sélectonne le chiffre d'affaire en focntion du service

                }else if(date.isSelected()){ //Si on selection le chiffre d'affaire par date
                    try{ // On verifie que la date est bien valide
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        cal.setTime(sdf.parse(dateTextField.getText()));
                        ca = mP.getCADate(cal); // On va chercher le chiffre d'affaire en fonction de la date
                    }catch(Exception npe){
                        JOptionPane.showMessageDialog(null,
                        "Veuillez entrez une date de la forme 03/06/2012.",
                        "Erreur date",
                        JOptionPane.ERROR_MESSAGE);
                    }
                }

                if(ca>-1){ // Si on a récupérer un chiffre d'affaire
                    chiffreAffaire.setText("Chiffre d'affaire : "+Float.toString(ca));
                }
            }
            else if(b.getName()=="option"){ // Si on clique sur option
                String strDate = JOptionPane.showInputDialog(null,
                        "Réglage de temps (ex: 03/06/1996 15:06) ", null);

                try{
                    Calendar cal = Calendar.getInstance();
                    Calendar now = Calendar.getInstance();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    cal.setTime(sdf.parse(strDate));
                    difTemps = (int)((cal.getTimeInMillis()-now.getTimeInMillis())/1000);//Transformation en secondes
                    if((cal.getTimeInMillis()-now.getTimeInMillis())%1000 > 0){
                        difTemps++;
                    }
                }catch(Exception exep){
                    JOptionPane.showMessageDialog(null,
                            "Vous devez entrer une date valide (ex: 03/06/1996 15:06).", "Date invalide",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Récupère et instancie les éléments du JPanel du login.
     * @param lC Le tableau contenant les composants. 
     */
    public void setLogComponents(Component[] lC){
        for(int i=0; i<lC.length; i++){
            if(lC[i].getName()=="loginLabel")
                this.loginLabel = (JLabel)lC[i];
            else if(lC[i].getName()=="password")
                this.passTextField = (JPasswordField)lC[i];
        }
    }

    /**
     * Récupère et instancie les éléments du JPanel du paiement.
     * @param pC Le tableau contenant les composants. 
     */
    public void setPayComponents(Component[] pC){
        for(int i=0; i<pC.length; i++){
            if(pC[i].getName()=="payTextField"){
                this.payTextField = (JTextField)pC[i];
                // On surveille si un prix et entré
                // Si c'est le cas et que l'on a sélectionner des tables alors on active le bouton valider
                this.payTextField.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                       warn();
                    }
                    public void removeUpdate(DocumentEvent e) {
                       warn();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        warn();
                    }

                    public void warn() {
                        try{
                            if(Float.parseFloat(payTextField.getText())>=0 && tableCounter>0){
                                valider.setEnabled(true);
                                valider.setBackground(Color.GREEN);
                            }else{
                                valider.setEnabled(false);
                                valider.setBackground(Color.GRAY);
                            }
                        }catch(NumberFormatException e){
                            valider.setEnabled(false);
                            valider.setBackground(Color.GRAY);
                        }
                    }
                });
            }
            else if(pC[i].getName()=="valider")
                this.valider = (JButton)pC[i];
            else if(pC[i].getName()=="especes")
                this.especes = (JRadioButton)pC[i];
            else if(pC[i].getName()=="cheque")
                this.cheque = (JRadioButton)pC[i];
            else if(pC[i].getName()=="cb")
                this.cb = (JRadioButton)pC[i];
        }
    }

    /**
     * Récupère et instancie les éléments du JPanel des statistiques.
     * @param sC Le tableau contenant les composants. 
     */
    public void setStatComponents(Component[] sC){
        for(int i=0; i<sC.length; i++){
            if(sC[i].getName()=="dateTextField")
                this.dateTextField = (JTextField)sC[i];
            else if(sC[i].getName()=="service")
                this.serviceComboBox = (JComboBox)sC[i];
            else if(sC[i].getName()=="sortDate")
                this.date = (JRadioButton)sC[i];
            else if(sC[i].getName()=="sortService")
                this.service = (JRadioButton)sC[i];
            else if(sC[i].getName()=="chiffreAffaire")
                this.chiffreAffaire = (JLabel)sC[i];
            else if(sC[i].getName()=="graph")
                this.graph = (Histogramme)sC[i];
          }
    }

    /**
     * Modifie le compteur des tables sélectionnées.
     * @param n Le nombre de tables sélectionnées.
     * @return true Si la sélection peut se faire. 
     */
    public boolean setTableCounter(int n){
        this.tableCounter = n;
        try{
            //Si on a renté un prix et que l'on a sélectionner des tables
            if(Float.parseFloat(payTextField.getText())>=0 && tableCounter>0){ 
                valider.setEnabled(true); // On active le bouton valider
                valider.setBackground(Color.GREEN);
            }else{
                valider.setEnabled(false); // On désactive le bouton valider
                valider.setBackground(Color.GRAY);
            }
       }catch(NumberFormatException e){ //Si le prix est invalide désactive le bouton valider
            valider.setEnabled(false); 
            valider.setBackground(Color.GRAY);
       }
       return true;
    }

}
