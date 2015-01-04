import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MWindow {
	private Frame wframe;
	private Window fwindow;
	private GraphicsConfiguration gc;

	public MWindow () {
		this.wframe = new Frame("ModelSim");
 		this.fwindow = new Window(this.wframe);
 		this.gc = this.fwindow.getGraphicsConfiguration();
 		this.wframe.setSize(640,480);
 		this.wframe.setLocation(100,100);
 		this.wframe.setUndecorated(false);
 		this.wframe.setVisible(true);
 		this.wframe.addWindowListener(new WindowListener () {
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
		this.computeGradientDescent();
	}

	public Frame getFrame () {
		return this.wframe;
	}

	public void computeGradientDescent () {
		ArrayList housing = new ArrayList<Double[]> ();
		ArrayList price = new ArrayList<Double> ();
		ArrayList housing_alt = new ArrayList<Double[]> ();		//prepare training examples for matrix multiplication
		ArrayList costs = new ArrayList<Double> ();
		int m = 0;				//number of training examples
		double[] mu = new double [] {0, 0};
		double[] sigma = new double [] {0, 0};
		double[] theta = new double [] {0, 0, 0};
		double alpha = 1.2d;
		int num_iter = 5000;
		try {
			BufferedReader read = new BufferedReader(new FileReader("ex1data2.txt"));
			String line;
			while ((line = read.readLine()) != null) {
				Double[] x = new Double[2];
				x[0] = new Double (line.split(",")[0]);
				x[1] = new Double (line.split(",")[1]);
				housing.add(x);
				price.add(new Double(line.split(",")[2]));
			}
			read.close();
		}
		catch (Exception e) {}
		m = price.size();
		/*
		for (int i = 0; i < housing.size(); i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(((Double[])housing.get(i))[j] + " ");
			}
			System.out.println();
		}
		*/
		for (int i = 0; i < housing.size(); i++) {
			for (int k = 0; k < 2; k++) {
				mu[k] += ((Double[])housing.get(i))[k];
			}
		}
		mu[0] = mu[0]/(double)housing.size();
		mu[1] = mu[1]/(double)housing.size();
		for (int i = 0; i < housing.size(); i++) {
			for (int k = 0; k < 2; k++) {
				double element = (((Double[])housing.get(i))[k] - mu[k]);
				element = element * element;
				sigma[k] += element;
			}
		}
		sigma[0] = Math.sqrt(((double)1/(double)(housing.size() - 1))*sigma[0]);
		sigma[1] = Math.sqrt(((double)1/(double)(housing.size() - 1))*sigma[1]);
		for (int i = 0; i < housing.size(); i++) {
			for (int k = 0; k < 2; k++) {
				((Double[])housing.get(i))[k] = new Double((((Double[])housing.get(i))[k] - mu[k]) / sigma[k]);
			}
		}
		for (int i = 0; i < housing.size(); i++) {
			Double[] t = new Double [] {new Double(1.0d), new Double(0), new Double(0)};
			t[1] = ((Double[])housing.get(i))[0];
			t[2] = ((Double[])housing.get(i))[1];
			housing_alt.add(t);
		}
		for (int i = 0; i < num_iter; i++) {
			ArrayList tm0 = new ArrayList<Double> ();
			ArrayList tm1 = new ArrayList<Double> ();
			Double tm2 = new Double (0);		//dot product is a real number
			Double tm3 = new Double (0);		//dot product is a real number
			double sum_tm0 = 0;
			double sum_tm1 = 0;
			double sum_tm2 = 0;
			double sum_tm3 = 0;
			double sum_tm4 = 0;
			for (int x = 0; x < housing_alt.size(); x++) {
				Double t = new Double(0);
				for (int y = 0; y < theta.length; y++) {
					t = t + (theta[y] * ((Double[])housing_alt.get(x))[y]);
				}
				t = t - ((Double)price.get(x));
				tm0.add(t);
			}
			for (int x = 0; x < tm0.size(); x++) {
				sum_tm0 = sum_tm0 + ((Double)tm0.get(x));
				Double t = new Double(((Double)tm0.get(x)) * ((Double[])housing_alt.get(x))[1]);		//dot prouduct
				sum_tm1 = sum_tm1 + t;
			}
			double cost = ((double)1/(double)(2 * m)) * (sum_tm0 * sum_tm0);
			costs.add(new Double(cost));
			tm0 = null;
			double tmp_0 = theta[0] - (alpha * ((double)1/(double)m) * sum_tm0);
			double tmp_1 = theta[1] - (alpha * ((double)1/(double)m) * sum_tm1);
			theta[0] = tmp_0;
			theta[1] = tmp_1;
			for (int x = 0; x < housing_alt.size(); x++) {
				Double t = new Double(0);
				for (int y = 0; y < theta.length; y++) {
					t = t + (theta[y] * ((Double[])housing_alt.get(x))[y]);
				}
				t = t - ((Double)price.get(x));
				tm1.add(t);
			}
			for (int x = 0; x < tm1.size(); x++) {
				Double t0 = new Double(((Double)tm1.get(x)) * ((Double[])housing_alt.get(x))[0]);		//dot product
				Double t1 = new Double(((Double)tm1.get(x)) * ((Double[])housing_alt.get(x))[1]);		//dot product
				Double t2 = new Double(((Double)tm1.get(x)) * ((Double[])housing_alt.get(x))[2]);		//dot product
				sum_tm2 = sum_tm2 + t0;
				sum_tm3 = sum_tm3 + t1;
				sum_tm4 = sum_tm4 + t2;
			}
			tm1 = null;
			double tmp_2 = theta[0] - (alpha * ((double)1/(double)m) * sum_tm2);
			double tmp_3 = theta[1] - (alpha * ((double)1/(double)m) * sum_tm3);
			double tmp_4 = theta[2] - (alpha * ((double)1/(double)m) * sum_tm4);
			theta[0] = tmp_2;
			theta[1] = tmp_3;
			theta[2] = tmp_4;
		}
		System.out.println("Mu: [" + mu[0] + ", " + mu[1] + "]");
		System.out.println("Sigma: [" + sigma[0] + ", " + sigma[1] + "]");
		System.out.println("housing size: " + housing.size());
		System.out.println("price size: " + price.size());
		System.out.println("function finished computing theta!");
		System.out.println("theta 0: " + theta[0]);
		System.out.println("theta 1: " + theta[1]);
		System.out.println("theta 2: " + theta[2]);
		double new_price = (1 * theta[0]) + (((1650 - mu[0])/sigma[0]) * theta[1]) + (((3 - mu[1]) / sigma[1]) * theta[2]);
		System.out.println("Price of a 1650 sqft house with 3 bedrooms is $" + new_price);
	}
}
