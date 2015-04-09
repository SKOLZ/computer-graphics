package ar.edu.itba.gc.utils;

public class RGBColor {
	
	private double r;
	private double g;
	private double b;
	
	public RGBColor(double r, double g, double b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public static RGBColor sum(RGBColor c1, RGBColor c2) {
		return new RGBColor(c1.getR() + c2.getR(),
				c1.getG() + c2.getG(), 
				c1.getB() + c2.getB());
	}

	public double getR() {
		return r;
	}
	
	public double getG() {
		return g;
	}
	
	public double getB() {
		return b;
	}

	public void applyGamma(double gammaInverse) {
		r = Math.pow(r, gammaInverse);
		g = Math.pow(g, gammaInverse);
		b = Math.pow(b, gammaInverse);
	}

	public void correctColor() {
		double max = Math.max(r, Math.max(g, b));
		if (max > 1.0) {
			r /= max;
			g /= max;
			b /= max;
		}
	}
	
	public static RGBColor mult(RGBColor c, double d) {
		return new RGBColor(c.getR() * d, c.getG() * d, c.getB() * d);
	}
	
	
	
	
	
}
