package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class Sphere extends GeometricObject {

	private Vector3d center;
	private double radius;

	public Sphere(Material material, Vector3d center, double radius) {
		super(material);
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
	public ShadeRec hit(ShadeRec sr, Vector3d origin, Vector3d direction) {
		double t;
		Vector3d temp = Vectors.sub(origin, center);
		double a = direction.dot(direction);
		double b = 2.0 * temp.dot(direction);
		double c = temp.dot(temp) - radius * radius;
		double disc = b * b - 4.0 * a * c;

		if (disc < 0.0) {
			return sr;
		} else {
			double e = Math.sqrt(disc);
			double denom = 2.0 * a;
			t = (-b - e) / denom;

			if (t > kEps && t < sr.getT()) {
				Vector3d hitPoint = Vectors.plus(origin,
						Vectors.scale(direction, t));
				Vector3d normal = Vectors.scale(
						Vectors.plus(temp, Vectors.scale(direction, t)),
						1 / radius);
				return new ShadeRec(true, sr.getWorld(), this.getMaterial(),
						normal, hitPoint, hitPoint, direction, t, sr.getDepth());
			}
			t = (-b + e) / denom;
			if (t > kEps && t < sr.getT()) {
				Vector3d hitPoint = Vectors.plus(origin,
						Vectors.scale(direction, t));
				Vector3d normal = Vectors.scale(
						Vectors.plus(temp, Vectors.scale(direction, t)),
						1 / radius);
				return new ShadeRec(true, sr.getWorld(), this.getMaterial(),
						normal, hitPoint, hitPoint, direction, t, sr.getDepth());
			}
		}
		return sr;
	}

	@Override
	public double shadowHit(Ray ray) {
		double t;
		Vector3d temp = Vectors.sub(ray.getOrigin(), center);
		double a = ray.getDirection().dot(ray.getDirection());
		double b = 2.0 * temp.dot(ray.getDirection());
		double c = temp.dot(temp) - radius * radius;
		double disc = b * b - 4.0 * a * c;

		if (disc < 0.0) {
			return -1;
		} else {
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
		return -1;
	}

}
