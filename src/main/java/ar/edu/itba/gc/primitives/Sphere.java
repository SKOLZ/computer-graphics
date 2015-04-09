package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.materials.Material;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Vectors;

public class Sphere extends GeometricObject {

	private Vector3d center;
	private double radius;

	public Sphere(Material material, RGBColor color, Vector3d center,
			double radius) {
		super(material, color);
		this.center = center;
		this.radius = radius;
	}

	public Vector3d getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	public void setCenter(Vector3d center) {
		this.center = center;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public double hit(Vector3d origin, Vector3d direction) {
		double t;
		Vector3d temp = Vectors.sub(origin, center);
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
