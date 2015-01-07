import java.awt.*;
import java.awt.image.*;

public class MPanel {
	private BufferedImage can;

	public MPanel () {
		this.can = new BufferedImage(640,480, BufferedImage.TYPE_INT_ARGB_PRE);
		this.gfx = this.can.createGraphics();
	}

	@Override
	public void paint (Graphics g) {
		this.gfx.setColor(Color.white);
		this.gfx.fillRect(0,0,640,480);
		g.drawImage(this.gfx,0,0,0,0);
	}
}
