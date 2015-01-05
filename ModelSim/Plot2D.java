import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Plot2D extends JFrame{

    public Plot2D(){
	setSize(600,600);
	setLocation(100,100);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //this part
    public void paintComponent(Graphics g){
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;
	
    }

    public static void main(String[] args){
	Plot2D p = new Plot2D();
	p.setVisible(true);
    }
}
