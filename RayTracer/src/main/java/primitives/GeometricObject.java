package primitives;


public abstract class GeometricObject {
	private Material material;
	private RGBColor color;
	public static final double kEps = 0.0001;
	
	public GeometricObject(Material material, RGBColor color) {
		this.material = material;
		this.color = color;
	}
	
	public Material getMaterial() {
		return material;
	}

	public RGBColor getColor() {
		return color;
	}

	protected boolean hit(Ray ray, double tmin){
		return false;
	}
}
