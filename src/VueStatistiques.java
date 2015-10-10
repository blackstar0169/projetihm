import java.awt.*;
import javax.swing.*;

public class VueStatistiques extends JPanel{
    Histogramme graph;
    JComboBox service;
    JTextField date;
    JLabel chiffreAffaire;
    ButtonGroup groupRadio;
    JButton payButton;
    JButton statButton;
    JButton option;
    JButton annuler;

    public VueStatistiques(){
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        float[] stats = {20, 35, 52, 25};
        this.graph = new Histogramme(7,7, "%age tables occupées", "Date", stats, 4);

        String[] strServices = new String[]{"Midi", "Soir"};
        this.service = new JComboBox(strServices);

        JRadioButton sortService = new JRadioButton("Service");
        JRadioButton sortDate = new JRadioButton("Date");
        this.date = new JTextField();
        this.chiffreAffaire = new JLabel();
        JLabel dateLabel = new JLabel("Date");
        JLabel sortLabel = new JLabel("Afficher le chiffre d'affaire par :");
        JLabel serviceLabel = new JLabel("Service");
        this.groupRadio = new ButtonGroup();
        GridBagConstraints c = new GridBagConstraints();

        this.statButton = new JButton("Satistiques");
        this.statButton.setName("statTab");
        this.payButton = new JButton("Paiement");
        this.payButton.setName("payTab");
        this.annuler = new JButton("Annuler");
        this.option = new JButton("Option");


        groupRadio.add(sortService);
        groupRadio.add(sortDate);

        //Menu
        JPanel panneauMenu = new JPanel();
        panneauMenu.setLayout(new GridLayout(1,4));
        option.setPreferredSize(new Dimension(600,50));
        payButton.setEnabled(false);
        panneauMenu.add(payButton);
        panneauMenu.add(statButton);
        panneauMenu.add(option);
        panneauMenu.setSize(600, 100);
        this.add(panneauMenu, BorderLayout.NORTH);


        //Mise en page
        c.gridy=0;
        c.gridx=0;
        mainPanel.add(dateLabel,c);
        c.gridx=1;
        mainPanel.add(date, c);
        c.gridy=1;
        c.gridx=0;
        mainPanel.add(serviceLabel, c);
        c.gridx=1;
        mainPanel.add(service, c);
        c.gridy=2;
        c.gridx=0;
        mainPanel.add(sortLabel, c);
        c.gridx=1;
        mainPanel.add(sortDate, c);
        c.gridx=2;
        mainPanel.add(sortService, c);
        c.gridy=0;
        c.gridx=3;
        mainPanel.add(graph, c);

        this.add(mainPanel, BorderLayout.CENTER);

    }

    public void addActionController(ControleurPaiement ctrl){
        this.payButton.addActionListener(ctrl);
        this.statButton.addActionListener(ctrl);
        this.option.addActionListener(ctrl);
    }
}
