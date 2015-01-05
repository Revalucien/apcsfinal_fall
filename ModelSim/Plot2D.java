import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Plot2D{
    private Frame fr;  

    public Plot2D(){
	
	setSize(600,600);
	setLocation(100,100);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
	Plot2D p = new Plot2D();
	p.setVisible(true);
    }
}
