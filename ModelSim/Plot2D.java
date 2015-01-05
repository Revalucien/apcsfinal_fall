import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Plot2D{
    private Frame fr;
    private Graphics gr;
    private Graphics2D gr2d;

    public Plot2D(){
	fr = new Frame("Graph");
	gr = fr.getGraphics();
	gr2d = (Graphics2D) gr;
	fr.setSize(600,600);
	fr.setLocation(740,100);
	fr.setUndecorated(false);
	fr.setVisible(true);
    }
}
