import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

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

    public ControleurPaiement(JPanel p, CardLayout cl, Table[] t, Component[] lC, Component[] pC, Component[] sC){
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
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                refreshTables();
            }
        }, 0, 60000);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getName()=="statTab"){
                cartes.show(panneau, "statistiques");
            }
            else if(b.getName()=="payTab") {
                cartes.show(panneau, "paiement");
            }
            else if(b.getName()=="loginButton"){
                char[] input = passTextField.getPassword();
                String pass = new String("root");
                if(pass.equals(new String(input))){
                    cartes.show(panneau, "paiement");
                    loginLabel.setText("");
                }
                else
                    loginLabel.setText("Mot de passe incorrect");

                Arrays.fill(input, '0');
                passTextField.selectAll();
            }
            else if(b.getName()=="annuler"){
                ControleurTables.deleteSelection();
                this.tableCounter=0;
                this.payTextField.setText("");
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
            }
            else if(b.getName()=="valider"){


                //Update de la table de paiement
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, (int)this.difTemps);
                String type=new String("carte bleue");
                if(especes.isSelected()){
                    type = "especes";
                }else if(cheque.isSelected()){
                    type = "cheque";
                }
                try{
                    float prix = Float.parseFloat(payTextField.getText());
                    ModelePaiement mP= new ModelePaiement();
                    mP.insert(cal, prix, type);

                    ArrayList<Table> tab = ControleurTables.getSelection();

                    for(int i=0; i<tab.size(); i++){
                        tab.get(i).setStatut(Table.ALAVER);
                        tab.get(i).setNom(null);
                    }
                    ControleurTables.deleteSelection();
                    this.tableCounter=0;
                    this.payTextField.setText("");
                    this.valider.setEnabled(false);
                    this.valider.setBackground(Color.GRAY);
                    modeleTable.updateTables(this.tables);
                }catch(NumberFormatException nfe){

                }
            }
            else if(b.getName()=="trier"){
                float ca = -1;
                ModelePaiement mP = new ModelePaiement();
                if(service.isSelected()){
                    ca = mP.getCAService((String)serviceComboBox.getSelectedItem());

                }else if(date.isSelected()){
                    try{
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        cal.setTime(sdf.parse(dateTextField.getText()));
                        ca = mP.getCADate(cal);
                    }catch(Exception npe){
                        JOptionPane.showMessageDialog(null,
                        "Veuillez entrez une date de la forme 03/06/2012.",
                        "Erreur date",
                        JOptionPane.ERROR_MESSAGE);
                    }
                }

                if(ca>-1){
                    chiffreAffaire.setText("Chiffre d'affaire : "+Float.toString(ca));
                }
            }
            else if(b.getName()=="option"){
                String strDate = JOptionPane.showInputDialog(null,
                        "Réglage de temps (ex: 03/06/1996 15:06) ", null);

                try{
                    Calendar cal = Calendar.getInstance();
                    Calendar now = Calendar.getInstance();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    cal.setTime(sdf.parse(strDate));
                    difTemps = (int)((cal.getTimeInMillis()-now.getTimeInMillis())/1000);//Transformation en secondes
                    //difTemps = (int)(difTemps/60); //Transformation en minutes

                }catch(Exception exep){
                    JOptionPane.showMessageDialog(null,
                            "Vous devez entrer une date valide (ex: 03/06/1996 15:06).", "Date invalide",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void setLogComponents(Component[] lC){
        for(int i=0; i<lC.length; i++){
            if(lC[i].getName()=="loginLabel")
                this.loginLabel = (JLabel)lC[i];
            else if(lC[i].getName()=="password")
                this.passTextField = (JPasswordField)lC[i];
        }
    }

    public void setPayComponents(Component[] pC){
        for(int i=0; i<pC.length; i++){
            if(pC[i].getName()=="payTextField"){
                this.payTextField = (JTextField)pC[i];
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

    public boolean setTableCounter(int n){
        this.tableCounter = n;
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
       return true;
    }

    public void refreshTables(){
        Table[] t= modeleTable.getAll();
        //Pour ne pas détruire les références des tables entre la vue et le controleur
        for(int i=0; i<t.length; i++){
            tables[i].setNom(t[i].getNom());
            tables[i].setGroupId(t[i].getGroupId());
            tables[i].setStatut(t[i].getStatut());
        }
        System.out.println("talbes !!");
    }
}
