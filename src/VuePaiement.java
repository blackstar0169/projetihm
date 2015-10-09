import java.awt.*;
import javax.swing.*;

public class VuePaiement extends JPanel{
    private Table[] tables;
    private JTextField paiementTextField;
    private ButtonGroup groupRadio;
    private JButton valider;
    private JButton annuler;
    private JButton option;
    private JButton statButton;
    private JButton payButton;

    public VuePaiement(Table[] t){
        JPanel panneauPaiement = new JPanel();
        JPanel panneauMenu = new JPanel();
        JPanel panneauPlan = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        int cnt = 0;

        JLabel paiementLabel = new JLabel("Somme à payer", SwingConstants.RIGHT);
        JLabel deviseLabel = new JLabel("€");

        this.paiementTextField = new JTextField();

        JRadioButton especes = new JRadioButton("Especes");
        JRadioButton cb = new JRadioButton("CB");
        JRadioButton cheque = new JRadioButton("Chèque");

        this.groupRadio = new ButtonGroup();

        this.valider = new JButton("Valider");
        this.statButton = new JButton("Satistiques");
        this.payButton = new JButton("Paiement");
        this.annuler = new JButton("Annuler");
        this.option = new JButton("Option");

        this.tables = t;
        this.setLayout(new BorderLayout());
        panneauPlan.setLayout(new GridBagLayout());

        valider.setBackground(Color.GRAY);
        valider.setEnabled(false);

        annuler.setBackground(Color.RED);


        //Création du menu
        GridLayout layout = new GridLayout(1,4);
        option.setPreferredSize(new Dimension(600,50));
        payButton.setEnabled(false);
        panneauMenu.setLayout(layout);
        panneauMenu.add(payButton);
        panneauMenu.add(statButton);
        panneauMenu.add(option);
        panneauMenu.setSize(600, 100);
        this.add(panneauMenu, BorderLayout.NORTH);

        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,5,10,5);

        // Affichage des tables

        for(int i=0; i<2; i++){
            for(int j=0; j<10; j++){
                c.insets = new Insets(10,5,10,5);
                try{
                    if(t[cnt].getGroupId() == t[cnt+1].getGroupId()){
                        c.insets = new Insets(10, 5, 10, 0);
                    }else if(t[cnt-1].getGroupId() == t[cnt].getGroupId()){
                        c.insets = new Insets(10, 0, 10, 5);
               }
                }catch(IndexOutOfBoundsException e){}
                c.gridx = j;
                c.gridy = i;
                t[cnt].setMinimumSize(new Dimension(10,10));
                panneauPlan.add(t[cnt], c);
                cnt++;
            }
        }

        for(int i=2; i<4; i++){
            for(int j=0; j<5; j++){
                c.insets = new Insets(10,5,10,5);
                try{
                    if(t[cnt].getGroupId() == t[cnt+1].getGroupId()){
                        c.insets = new Insets(10, 5, 10, 0);
                    }else if(t[cnt-1].getGroupId() == t[cnt].getGroupId()){
                        c.insets = new Insets(10, 0, 10, 5);
                    }
                }catch(IndexOutOfBoundsException e){}

                c.gridx = j;
                c.gridy = i;
                panneauPlan.add(t[cnt], c);
                cnt++;
            }
        }

        // Affichage du formularie de paiement

        panneauPaiement.setLayout(new GridBagLayout());

        c.weightx = 1;
        c.weighty = 1;
        c.fill=GridBagConstraints.BOTH;
        c.gridx=0;
        c.gridy=0;
        c.anchor=GridBagConstraints.LINE_END;
        paiementLabel.setMaximumSize(new Dimension(150, 30));

        panneauPaiement.add(paiementLabel, c);

        c.gridx=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        paiementTextField.setPreferredSize(new Dimension(100, 30));
        panneauPaiement.add(paiementTextField, c);

        c.fill=GridBagConstraints.BOTH;
        deviseLabel.setPreferredSize(new Dimension(30, 30));
        c.anchor=GridBagConstraints.LINE_START;
        c.gridx=2;
        panneauPaiement.add(deviseLabel, c);


        groupRadio.add(especes);
        groupRadio.add(cb);
        groupRadio.add(cheque);

        especes.setSelected(true);
        especes.setAlignmentX(Component.RIGHT_ALIGNMENT);

        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.NONE;

        c.gridx=0;
        c.gridy=1;
        c.anchor=GridBagConstraints.LINE_END;
        c.fill=GridBagConstraints.VERTICAL;
        panneauPaiement.add(especes, c);

        c.gridx=1;

        c.anchor=GridBagConstraints.CENTER;
        panneauPaiement.add(cb, c);

        c.anchor=GridBagConstraints.LINE_START;
        c.gridx=2;
        panneauPaiement.add(cheque, c);

        c.weightx=1.5;
        c.gridx=0;
        c.gridy=2;
        c.fill=GridBagConstraints.HORIZONTAL;
        panneauPaiement.add(valider, c);

        c.gridx=2;
        panneauPaiement.add(annuler, c);


        panneauPaiement.setPreferredSize(new Dimension(1, 1));
        c.insets = new Insets(10, 5, 10, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 5;
	c.gridy = 2;
	c.gridwidth = 5;
	c.gridheight = 4;

        c.fill=GridBagConstraints.BOTH;
        panneauPlan.add(panneauPaiement, c);
        this.add(panneauPlan, BorderLayout.CENTER);

    }

}
