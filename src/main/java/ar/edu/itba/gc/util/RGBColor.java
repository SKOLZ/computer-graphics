package ar.edu.itba.gc.util;

public class RGBColor {

	public static RGBColor black() {
		return new RGBColor(0, 0, 0);
	}

	public static RGBColor white() {
		return new RGBColor(1, 1, 1);
	}

	public static RGBColor sum(RGBColor c1, RGBColor c2) {
		return new RGBColor(c1.getR() + c2.getR(), c1.getG() + c2.getG(),
				c1.getB() + c2.getB());
	}

	public static RGBColor mult(RGBColor c, double d) {
		return new RGBColor(c.getR() * d, c.getG() * d, c.getB() * d);
	}

	public static RGBColor mult(RGBColor c1, RGBColor c2) {
		return new RGBColor(c1.r * c2.r, c1.g * c2.g, c1.b * c2.b);
	}

	private double r;
	private double g;
	private double b;

	public RGBColor(double r, double g, double b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
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

	public void sum(RGBColor color) {
		this.r += color.r;
		this.g += color.g;
		this.b += color.b;
	}

	public void divide(int n) {
		this.r /= n;
		this.g /= n;
		this.b /= n;
		
	}

	public void mult(double n) {
		this.r *= n;
		this.g *= n;
		this.b *= n;
		
	}

	public void divide(double n) {
		this.r /= n;
		this.g /= n;
		this.b /= n;
	}

}
