import java.awt.*;
import javax.swing.*;

/**
* Classe gérant la vue du login.
* @author Anthony DUPLAT
*/

public class VueLogin extends JPanel{
    private JLabel loginLabel;
    private JButton loginButton;
    private JPasswordField password;

    public VueLogin(){
        this.setLayout(new GridBagLayout());
        this.setAlignmentY(Component.CENTER_ALIGNMENT);

        GridBagConstraints c = new GridBagConstraints();
        this.loginButton = new JButton("Se connecter");
        this.password = new JPasswordField();
        this.loginLabel = new JLabel("", SwingConstants.CENTER);
        JLabel mdpLabel = new JLabel("Mot de passe :", SwingConstants.RIGHT);

        loginButton.setPreferredSize(new Dimension(150, 30));
        loginButton.setName("loginButton");
        password.setPreferredSize(new Dimension(300, 30));
        password.setName("password");

        loginLabel.setPreferredSize(new Dimension(250,30));
        loginLabel.setForeground(Color.RED);
        loginLabel.setName("loginLabel");
        mdpLabel.setPreferredSize(new Dimension(150,30));


        c.gridx=0;
        c.gridy=0;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.PAGE_END;
        c.weightx=1;
        c.weighty=0.5;
        c.gridwidth=3;
        this.add(loginLabel, c);

        c.gridwidth=1;
        c.gridx=0;
        c.gridy=1;
        c.fill=GridBagConstraints.NONE;
        c.anchor=GridBagConstraints.FIRST_LINE_END;
        this.add(mdpLabel,c);

        c.gridx=1;
        c.gridy=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets= new Insets(0,10,0,10);
        c.anchor=GridBagConstraints.PAGE_START;
        this.add(password, c);

        c.gridx=2;
        c.gridy=1;
        c.insets= new Insets(0,0,0,0);
        c.anchor=GridBagConstraints.CENTER;
        c.fill=GridBagConstraints.NONE;
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        this.add(loginButton, c);

    }

    /**
    * Récupère le bouton du login.
    * @return JButton le bouton du login.
    */
    public JButton getLoginButton(){
        return this.loginButton;
    }
}
