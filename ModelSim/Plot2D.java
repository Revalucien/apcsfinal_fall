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
	fr.addWindowListener(new WindowListener () {
		public void windowClosing(WindowEvent e) {
		    System.exit(0);
		}
			
		public void windowOpened(WindowEvent e) {}
	        public void windowActivated(WindowEvent e) {}
	        public void windowIconified(WindowEvent e) {}
	        public void windowDeiconified(WindowEvent e) {}
	        public void windowDeactivated(WindowEvent e) {}
	        public void windowClosed(WindowEvent e) {}
	    });
	

    }
}
