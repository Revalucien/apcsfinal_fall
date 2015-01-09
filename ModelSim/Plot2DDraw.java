import java.awt.*;

public class Plot2DDraw extends Panel {
    private Panel pn;
    private Graphics g;

    public Plot2DDraw(){
	pn.paint(g);
    }

    @Override
    public void paint(Graphics g){
	g.setColor(Color.BLACK);
	g.drawLine(10,10,20,20);
    }
}
