package ar.edu.itba.gc.objects;

import javax.vecmath.Vector4d;

import ar.edu.itba.gc.materials.Material;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Vectors;

public class Sphere extends GeometricObject {

	private Vector4d center;
	private double radius;

	public Sphere(Material material, RGBColor color, Vector4d center,
			double radius) {
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
	public double hit(Vector4d origin, Vector4d direction) {
		double t;
		Vector4d temp = Vectors.sub(origin, center);
		double a = direction.dot(direction);
		double b = 2.0 * temp.dot(direction);
		double c = temp.dot(temp) - radius * radius;
		double disc = b * b - 4.0 * a * c;

		if (disc < 0.0)
			return -1.0;
		else {
			double e = Math.sqrt(disc);
			double denom = 2.0 * a;
			t = (-b - e) / denom;
			if (t > kEps) {
				return t;
			}
			t = (-b + e) / denom;
			if (t > kEps) {
				return t;
			}
		}
		return -1.0;
	}

}
