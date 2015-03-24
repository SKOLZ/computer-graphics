package primitives;

import javax.vecmath.Vector4d;

import utils.Vectors;

public class Sphere extends GeometricObject{

	private Vector4d center;
	private double radius;

	public Sphere(Material material, RGBColor color, Vector4d center, double radius) {
		super(material, color);
		this.center = center;
		this.radius = radius;
	}

	public Vector4d getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}
	
	public void setCenter(Vector4d center) {
		this.center = center;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	protected boolean hit(Ray ray, double tmin) {
		double t;
		Vector4d temp = Vectors.sub(ray.getOrigin(), center);
		double a = ray.getDirection().dot(ray.getDirection());
		double b = 2.0 * temp.dot(ray.getDirection());
		double c = temp.dot(temp) - radius * radius;
		double disc = b * b - 4.0 * a * c;
		
		if (disc < 0.0) return false; 
		else {
			double e = Math.sqrt(disc);
			double denom = 2.0 * a;
			t = (-b - e) / denom;
			if (t > kEps) {
				tmin = t;
				return true;
			}
			t = (-b + e) / denom;
			if (t > kEps) {
				tmin = t;
				return true;
			}
		}
		return false;
	}
	
}
