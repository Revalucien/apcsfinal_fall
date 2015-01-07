import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Plot2D{
    private Frame fr;
    private Graphics gr;
    private Graphics2D gr2d;
    private Image img;

    public Plot2D(){
	fr = new Frame("Graph");
	fr.setSize(600,600);
	fr.setLocation(740,100);
	fr.setUndecorated(false);
	fr.setVisible(true);
	gr = fr.getGraphics();
	gr2d = (Graphics2D) gr;
	gr.setColor(Color.BLACK);
	gr.drawLine(20,20,30,30);
	

    }
}
