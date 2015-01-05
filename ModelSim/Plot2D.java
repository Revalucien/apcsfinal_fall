import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Plot2D{
    private Frame fr;  

    public Plot2D(){
	fr = new Frame("Graph");
	fr.setSize(600,600);
	fr.setLocation(100,100);
	fr.setUndecorated(false);
	fr.setVisible(true);
    }
}
