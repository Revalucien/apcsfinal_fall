import java.awt.*;
import java.awt.image.*;
import java.text.*;
import java.util.*;

public class MPanel extends Panel {
	private BufferedImage can;
	private Graphics gfx;
	/*private Thread t;

	public void start () { if (t == null) { t = new Thread(this); t.start(); } }
	public void stop () { if (t != null) {t.stop(); t = null;}}
	public void run () {
		try {
			while (true) {
				this.paint(this.gfx);
				this.t.sleep(20);
			}
		}
		catch (Exception e) {}
	}*/

	public MPanel () {
		this.can = (new BufferedImage(640,480, BufferedImage.TYPE_INT_ARGB_PRE));
		this.gfx = this.can.createGraphics();
	}

	@Override
	public void paint (Graphics g) {
		System.out.println("Paint called at: " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
		super.paint(g);
		//g.setColor(Color.white);
		//g.fillRect(0,0,640,480);
		g.setColor(Color.black);
		g.fillRect(0,0,320,480);
		//g.setColor(Color.white);
		g.drawImage((Image)this.can,0,0,this);
		super.update(g);
	}
}
