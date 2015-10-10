import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

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

    private Table[] tables;

    public ControleurPaiement(JPanel p, CardLayout cl, Table[] t,  Component[] lC, Component[] pC, Component[] sC){
        setLogComponents(lC);
        setPayComponents(pC);
        setStatComponents(sC);
        this.tables = t;
        this.panneau = p;
        this.cartes = cl;
        this.tableCounter = 0;
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
                ArrayList<Table> tab = ControleurTables.getSelection();
                for(int i=0; i<tab.size(); i++){
                    tab.get(i).setStatut(Table.ALAVER);
                }
                ControleurTables.deleteSelection();
                this.tableCounter=0;
                this.payTextField.setText("");
                this.valider.setEnabled(false);
                this.valider.setBackground(Color.GRAY);
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
                            if(Float.parseFloat(payTextField.getText())>=0 || tableCounter>0){
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
            else if(sC[i].getName()=="serviceComboBox")
                this.serviceComboBox = (JComboBox)sC[i];
            else if(sC[i].getName()=="date")
                this.date = (JRadioButton)sC[i];
            else if(sC[i].getName()=="service")
                this.service = (JRadioButton)sC[i];
            else if(sC[i].getName()=="chiffreAffaire")
                this.chiffreAffaire = (JLabel)sC[i];
            else if(sC[i].getName()=="graph")
                this.graph = (Histogramme)sC[i];
        }
    }
}
