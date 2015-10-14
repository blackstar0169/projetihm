import java.awt.*;
import javax.swing.*;

public class VueStatistiques extends JPanel{
    Histogramme graph;
    /*
     *<string> pour eliminer les warning.
     *Car comme Java (et Oracle en général) c'est de la merde, cette classe utilise du code générique qui produit des warnings
     */
    JComboBox<String> service;
    JTextField date;
    JLabel chiffreAffaire;
    JButton payButton;
    JButton option;
    JButton sortButton;
    JRadioButton sortService;
    JRadioButton sortDate;
    JTextField dateTextField;

    public VueStatistiques(){
        this.setLayout(new BorderLayout());

        JPanel panneauMenu = new JPanel();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        float[] stats = {20, 35, 52, 25};
        this.graph = new Histogramme(7,7, "%age tables occupées", "Date", stats, 4);

        String[] strServices = new String[]{"Midi", "Soir"};
        this.service = new JComboBox<>(strServices);
        this.service.setName("service");

        this.sortService = new JRadioButton("Service");
        this.sortService.setName("sortService");
        this.sortService.setSelected(true);
        this.sortDate = new JRadioButton("Date (dd/mm/yyyy)");
        this.sortDate.setName("sortDate");

        this.dateTextField = new JTextField();
        dateTextField.setName("dateTextField");
        this.chiffreAffaire = new JLabel("Sélectionnez un mode de tri");
        chiffreAffaire.setName("chiffreAffaire");
        JLabel dateLabel = new JLabel("Date");
        JLabel sortLabel = new JLabel("Afficher en fonction de :");
        JLabel serviceLabel = new JLabel("Service");
        ButtonGroup groupRadio = new ButtonGroup();
        GridBagConstraints c = new GridBagConstraints();


        this.sortButton = new JButton("Trier");
        this.sortButton.setName("trier");


        groupRadio.add(sortService);
        groupRadio.add(sortDate);

        //Menu
        JButton statButton = new JButton("Satistiques");
        this.payButton = new JButton("Paiement");
        this.payButton.setName("payTab");
        this.option = new JButton("Option");
        this.option.setName("option");
        panneauMenu.setLayout(new GridLayout(1,4));
        option.setPreferredSize(new Dimension(600,50));
        statButton.setEnabled(false);
        panneauMenu.add(payButton);
        panneauMenu.add(statButton);
        panneauMenu.add(option);
        panneauMenu.setSize(600, 100);
        this.add(panneauMenu, BorderLayout.NORTH);


        //Mise en page
        c.gridy=0;
        c.gridx=0;
        c.weightx=0.3;
        c.weighty=1;
        c.insets = new Insets(10,0,0,10);
        c.fill=GridBagConstraints.NONE;
        c.anchor=GridBagConstraints.LINE_END;
        dateLabel.setPreferredSize(new Dimension(50, 25));
        mainPanel.add(dateLabel,c);
        c.gridx=1;
        c.anchor=GridBagConstraints.CENTER;
        dateTextField.setPreferredSize(new Dimension(100, 25));
        mainPanel.add(dateTextField, c);
        c.gridy=1;
        c.gridx=0;
        c.anchor=GridBagConstraints.FIRST_LINE_END;
        mainPanel.add(serviceLabel, c);
        c.gridx=1;
        c.anchor=GridBagConstraints.PAGE_START;
        mainPanel.add(service, c);
        c.gridy=2;
        c.gridx=0;
        c.anchor=GridBagConstraints.FIRST_LINE_END;
        mainPanel.add(sortLabel, c);
        c.gridx=1;
        c.anchor=GridBagConstraints.PAGE_START;
        mainPanel.add(sortDate, c);
        c.gridx=2;
        c.anchor=GridBagConstraints.PAGE_START;
        mainPanel.add(sortService, c);
        c.gridy=3;
        c.gridx=0;
        c.gridwidth=3;
        mainPanel.add(chiffreAffaire, c);
        c.gridy=4;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.PAGE_START;
        mainPanel.add(sortButton, c);

        c.gridy=0;
        c.gridx=3;
        c.gridwidth=10;
        c.gridheight=3;
        c.weightx=5;
        c.gridheight=4;
        c.insets = new Insets(10,10,10,10);

        graph.setPreferredSize(new Dimension(1,1));
        c.fill=GridBagConstraints.BOTH;
        mainPanel.add(graph, c);


        this.add(mainPanel, BorderLayout.CENTER);

    }

    public void addActionController(ControleurPaiement ctrl){
        this.payButton.addActionListener(ctrl);
        this.option.addActionListener(ctrl);
        this.sortButton.addActionListener(ctrl);
    }

    public Component[] getStatComponents(){
        Component[] comp = new Component[5];

        comp[0] = dateTextField;
        comp[1] = chiffreAffaire;
        comp[2] = sortService;
        comp[3] = sortDate;
        comp[4] = service;

        return comp;
    }
}
