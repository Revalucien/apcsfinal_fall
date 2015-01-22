/* Extend the default java Panel object
*/

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.text.*;
import java.util.*;

public class MPanel extends Panel implements Runnable{
	private BufferedImage can;
	private Graphics gfx;
	private Graphics2D gfx2D;
	private Thread t;
	private Boolean isUpdating;
	private Frame ctx;
	private Rectangle bounds;
	private Font font;
	private String xaxis;
	private String yaxis;
	private AffineTransform rotate;
	private int Y_LEN;
	private int X_LEN;
	private Color ONE_BDRM;
	private Color TWO_BDRM;
	private Color THREE_BDRM;
	private Color FOUR_BDRM;
	private Color FIVE_BDRM;
	private ArrayList<Double[]> l0;
	private ArrayList<Double> l1;
	private MWindow win;
	private Point KEY;
	private int DRAWABLE_HEIGHT;

	public void start () {
		if (t == null) {
			t = new Thread(this);
			t.start();
		} 
	}
	public void stop () { if (t != null) {t.stop(); t = null;}}
	public void run () {
		try {
			while (this.isUpdating) {
				super.repaint();
				this.t.sleep(50);
			}
		}
		catch (InterruptedException ie) {}
	}

	public MPanel (Frame window, ArrayList housing, ArrayList price, MWindow win) {
		this.isUpdating = true;
		this.l0 = housing;
		this.l1 = price;
		this.win = win;
		this.font = new Font("Arial Monospaced", Font.PLAIN, 20);
		this.FIVE_BDRM = new Color(255,0,0);
		this.FOUR_BDRM = new Color(255,0,135);
		this.THREE_BDRM = new Color(255,0,255);
		this.TWO_BDRM = new Color(255,135,255);
		this.ONE_BDRM = new Color(255,135,135);
		this.KEY = new Point(960,150);
		this.ctx = window;
		this.xaxis = new String("Size (square feet)");
		this.yaxis = new String("Price (in thousands)");
		this.bounds = this.ctx.getBounds();
		super.setSize(this.bounds.width,this.bounds.height);
		this.can = (new BufferedImage(this.bounds.width,this.bounds.height, BufferedImage.TYPE_INT_ARGB_PRE));
		this.gfx = this.can.createGraphics();
		this.gfx2D = (Graphics2D)this.gfx;
		this.gfx2D.setFont(this.font);
		super.validate();
	}

	@Override
	public void update (Graphics g) {
		if ((this.ctx.getInsets().top != 0) || (System.getProperty("java.runtime.name").equals(new String("OpenJDK Runtime Environment")))) {
			this.cleanGFX();
			this.drawBD();
			this.drawAxis();
			this.drawData();
			this.paint(g);
			this.isUpdating = false;			//everything has been drawn, stop rendering the graph
		}
	}

	@Override
	public void paint (Graphics g) {
		super.paint(g);
		g.drawImage(this.can,0,this.ctx.getInsets().top,this);
	}

	public void drawAxis () {
		this.gfx.setColor(Color.black);
		this.bounds = this.ctx.getBounds();
		/*
		System.out.println("Top Inset: " + this.ctx.getInsets().top);
		System.out.println("Left Inset: " + this.ctx.getInsets().left);
		System.out.println("right Inset: " + this.ctx.getInsets().right);
		System.out.println("bottom Inset: " + this.ctx.getInsets().bottom);
		*/
		this.gfx2D.translate(0, (this.bounds.height - this.ctx.getInsets().top));
		this.gfx2D.drawString(this.xaxis, (this.bounds.width/2) - (6 * (this.xaxis.length() - 0)), -15);
		this.rotate = new AffineTransform();
		this.rotate.rotate(-Math.PI * .5);
		this.gfx2D.transform(this.rotate);
		this.gfx2D.drawString(this.yaxis, (this.bounds.height/2) - (6 * (this.yaxis.length() - 0)), 25);
		this.rotate.rotate(Math.PI);
		this.gfx2D.transform(this.rotate);
		this.gfx2D.scale(1,-1);
		this.gfx2D.setColor(Color.black);
		this.gfx.drawLine(75, 75, 75, 680);
		this.Y_LEN = 680 - 75;
		this.gfx.drawLine(75, 75, 1220, 75);
		this.X_LEN = 1220 - 75;
		this.gfx2D.setColor(this.ONE_BDRM);
		this.gfx2D.fillRect((int)this.KEY.getX(),(int)this.KEY.getY(),20,20);
		this.gfx2D.setColor(this.TWO_BDRM);
		this.gfx2D.fillRect((int)this.KEY.getX(),(int)this.KEY.getY() + 20,20,20);
		this.gfx2D.setColor(this.THREE_BDRM);
		this.gfx2D.fillRect((int)this.KEY.getX(),(int)this.KEY.getY() + 40,20,20);
		this.gfx2D.setColor(this.FOUR_BDRM);
		this.gfx2D.fillRect((int)this.KEY.getX(),(int)this.KEY.getY() + 60,20,20);
		this.gfx2D.setColor(this.FIVE_BDRM);
		this.gfx2D.fillRect((int)this.KEY.getX(),(int)this.KEY.getY() + 80,20,20);
		this.gfx2D.setColor(Color.black);
		this.gfx2D.scale(1,-1);
		this.gfx2D.drawString("1 Bedroom", (int)this.KEY.getX() + 30, -(int)this.KEY.getY() - 3);
		this.gfx2D.drawString("2 Bedrooms", (int)this.KEY.getX() + 30, -(int)this.KEY.getY() - 23);
		this.gfx2D.drawString("3 Bedrooms", (int)this.KEY.getX() + 30, -(int)this.KEY.getY() - 43);
		this.gfx2D.drawString("4 Bedrooms", (int)this.KEY.getX() + 30, -(int)this.KEY.getY() - 63);
		this.gfx2D.drawString("5 Bedrooms", (int)this.KEY.getX() + 30, -(int)this.KEY.getY() - 83);
		this.gfx2D.scale(1,-1);
		this.gfx2D.translate(75,75);
		for (int i = 0; i < 7; i++) {
			this.gfx2D.drawLine(5,((this.Y_LEN/7)*(1 + i)), 0,((this.Y_LEN/7)*(1 + i)));
			this.gfx2D.scale(1,-1);
			this.gfx2D.drawString((new Integer((i + 1) * 100)).toString(), -42,-((this.Y_LEN/7)*(1 + i)) + 10);
			this.gfx2D.scale(1,-1);
		}
		for (int i = 0; i < 10; i++) {
			this.gfx2D.drawLine(((this.X_LEN/10)*(1 + i)), 0,((this.X_LEN/10)*(1 + i)), 5);
			this.gfx2D.scale(1,-1);
			this.gfx2D.drawString((new Integer((i + 1) * 500)).toString(), ((this.X_LEN/10)*(1 + i)) - 24, 30);
			this.gfx2D.scale(1,-1);
		}
		this.gfx2D.translate(-75,-75);
		this.gfx2D.scale(1,-1);
		this.gfx2D.translate(0, -(this.bounds.height - this.ctx.getInsets().top));
	}

	public void drawBD () {		//plot points based on price and number of square feet
		this.bounds = this.ctx.getBounds();
		this.gfx2D.translate(0, (this.bounds.height - this.ctx.getInsets().top));
		this.gfx2D.scale(1,-1);
		this.gfx2D.translate(75,75);
		this.gfx2D.setStroke(new BasicStroke(3));
		this.gfx2D.setColor(this.ONE_BDRM);
		this.gfx2D.drawLine((int)((this.X_LEN/5)*((double)0/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)0, 1)))/100000)),(int)((this.X_LEN/5)*((double)5000/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)5000, 1)))/100000)));
		this.gfx2D.setColor(this.TWO_BDRM);
		this.gfx2D.drawLine((int)((this.X_LEN/5)*((double)0/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)0, 2)))/100000)),(int)((this.X_LEN/5)*((double)5000/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)5000, 2)))/100000)));
		this.gfx2D.setColor(this.THREE_BDRM);
		this.gfx2D.drawLine((int)((this.X_LEN/5)*((double)0/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)0, 3)))/100000)),(int)((this.X_LEN/5)*((double)5000/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)5000, 3)))/100000)));
		this.gfx2D.setColor(this.FOUR_BDRM);
		this.gfx2D.drawLine((int)((this.X_LEN/5)*((double)0/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)0, 4)))/100000)),(int)((this.X_LEN/5)*((double)5000/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)5000, 4)))/100000)));
		this.gfx2D.setColor(this.FIVE_BDRM);
		this.gfx2D.drawLine((int)((this.X_LEN/5)*((double)0/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)0, 5)))/100000)),(int)((this.X_LEN/5)*((double)5000/1000)), (int)((this.Y_LEN/7)*(((this.win.computePrice((double)5000, 5)))/100000)));
		this.gfx2D.setColor(Color.black);
		this.gfx2D.translate(-75,-75);
		this.gfx2D.scale(1,-1);
		this.gfx2D.translate(0, -(this.bounds.height - this.ctx.getInsets().top));
		this.gfx2D.setStroke(new BasicStroke(2));
	}

	public void drawData () {		//plot points based on price and number of square feet
		this.gfx.setColor(Color.black);
		this.bounds = this.ctx.getBounds();
		this.gfx2D.translate(0, (this.bounds.height - this.ctx.getInsets().top));
		this.gfx2D.scale(1,-1);
		this.gfx2D.translate(75,75);
		for (int i = 0; i < l0.size(); i++) {
			if (((Double[])l0.get(i))[1] == 1) {
				this.gfx2D.setColor(this.ONE_BDRM);
			}
			else if (((Double[])l0.get(i))[1] == 2) {
				this.gfx2D.setColor(this.TWO_BDRM);
			}
			else if (((Double[])l0.get(i))[1] == 3) {
				this.gfx2D.setColor(this.THREE_BDRM);
			}
			else if (((Double[])l0.get(i))[1] == 4) {
				this.gfx2D.setColor(this.FOUR_BDRM);
			}
			else if (((Double[])l0.get(i))[1] == 5) {
				this.gfx2D.setColor(this.FIVE_BDRM);
			}
			this.gfx2D.setStroke(new BasicStroke(10));
			this.drawPoint((int)((this.X_LEN/5)*(((Double[])l0.get(i))[0]/1000)), (int)((this.Y_LEN/7)*((Double)l1.get(i))/100000));
		}
		this.gfx2D.setColor(Color.black);
		this.gfx2D.translate(-75,-75);
		this.gfx2D.scale(1,-1);
		this.gfx2D.translate(0, -(this.bounds.height - this.ctx.getInsets().top));
	}

	public void cleanGFX() {
		this.gfx.setColor(Color.white);
		this.gfx.fillRect(0,0,this.bounds.width,this.bounds.height);
		this.gfx.setColor(Color.black);
		this.gfx2D.setStroke(new BasicStroke(2));
	}

	public void drawPoint(int x, int y) {
		this.gfx2D.drawLine(x,y,x,y);
	}
}
