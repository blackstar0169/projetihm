import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControleurPaiement implements ActionListener{
    JTextField loginTextField;
    JTextField payTextField;
    CardLayout cartes;
    JPanel panneau;
    JLabel loginLabel;
    JButton valider;
    ButtonGroup payGroupRadio;

    public ControleurPaiement(JPanel p, CardLayout cl, Component[] lC, Component[] pC, Component[] sC){
        setLogComponents(lC);
        setPayComponents(pC);
       // setStatComponents(sC);
        this.panneau = p;
        this.cartes = cl;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getName()=="statTab"){
                cartes.show(panneau, "statistiques");

            }
            if(b.getName()=="payTab" || b.getName()=="loginButton"){
                cartes.show(panneau, "paiement");
            }
        }
    }

    public void setLogComponents(Component[] lC){
        for(int i=0; i<lC.length; i++){
            if(lC[i].getName()=="loginLabel")
                this.loginLabel = (JLabel)lC[i];
            else if(lC[i].getName()=="loginTextField")
                this.loginTextField = (JTextField)lC[i];
        }
    }

    public void setPayComponents(Component[] pC){
        for(int i=0; i<pC.length; i++){
            if(pC[i].getName()=="paiementTextField")
                this.payTextField = (JTextField)pC[i];
        }
    }
}
