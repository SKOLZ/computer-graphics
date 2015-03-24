package primitives;

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

	public RGBColor applyGamma(double gammaInverse) {
		return new RGBColor(Math.pow(r, gammaInverse), Math.pow(g, gammaInverse), Math.pow(b, gammaInverse));
	}
	
	
	
	
	
	
}
