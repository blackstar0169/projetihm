import javax.swing.*;
import java.awt.*;

public class Histogramme extends JPanel{
    private int xStep;
    private int yStep;
    private String xLabel;
    private String yLabel;
    private float[] data;
    private int numberOfElements;


    public Histogramme(int xS, int yS, String xL, String yL, float[] d, int n){
        this.xStep = xS;
        this.yStep = yS;
        this.xLabel = xL;
        this.yLabel = yL;
        this.data = d;
        this.numberOfElements = n;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);

        // Tracage de l'axe des absices et des ordonnées
        int x[] = new int[3];
        int y[] = new int[3];
        x[0]= 20;
        y[0]= 20;
        x[1]= 20;
        y[1] = this.getHeight()-20;
        x[2] = this.getWidth()-20;
        y[2] = y[1];

        g.drawPolyline(x, y, 3);
        g.drawString(this.xLabel, x[2]-this.xLabel.length()*6, y[1]+14);
        g.drawString(this.yLabel, 5, 12);

        //Tracage du graph
        int maxHeight = y[1]-y[0];
        int maxWidth = x[2]-x[1];
        int w, h, xg;

        try{
            w = maxWidth/this.numberOfElements;
        }catch(ArithmeticException e){
            w = 0;
        }

        for(int i=0; i<this.numberOfElements; i++){
            g.setColor(Color.GREEN);

            h = (int)(this.data[i]*maxHeight/100);

            xg = i*w+x[0]; // Nombre de barres dessinées + la position relative de l'origine des abscisses
            g.fillRect(xg+1, y[1]-h, w, h);
            g.setColor(Color.BLACK);
            g.drawRect(xg, y[1]-h, w, h);

        }

    }
}


