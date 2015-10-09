import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControleurPaiement implements ActionListener{
    JTextField loginTextField;
    CardLayout cartes;
    JPanel panneau;
    JLabel loginLabel;

    public ControleurPaiement(JPanel p, CardLayout cl, JTextField ltf, JLabel ll){
        this.panneau = p;
        this.cartes = cl;
        this.loginTextField = ltf;
        this.loginLabel = ll;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getName()=="loginButton"){
                cartes.show(panneau, "paiement");
            }
        }
    }
}
