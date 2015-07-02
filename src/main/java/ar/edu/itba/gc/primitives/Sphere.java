package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public class Sphere extends EmisiveObject {

	private Vector3d center;
	private double radius;
	private double invArea;
	
	public Sphere(double radius) {
		this(null, new Vector3d(0, 0, 0), radius);
	}

	public Sphere(Material material, Vector3d center, double radius) {
		super(material);
		this.center = center;
		this.radius = radius;
		this.invArea = 1/(4 * World.PI * radius * radius);
	}

	public Sphere(Material material) {
		super(material);
		this.center = new Vector3d(0, 0, 0);
		this.radius = 1.0;
		this.invArea = 1/(4 * World.PI * radius * radius);
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
	public double hit(ShadeRec sr, double tmin, Vector3d origin,
			Vector3d direction) {
		count++;
		double t;
		Vector3d temp = Vectors.sub(origin, center);
		double a = direction.dot(direction);
		double b = 2.0 * temp.dot(direction);
		double c = temp.dot(temp) - radius * radius;
		double disc = b * b - 4.0 * a * c;

		if (disc < 0.0) {
			return -1.0;
		} else {
			double e = Math.sqrt(disc);
			double denom = 2.0 * a;
			t = (-b - e) / denom;

			if (t > Constants.KEPS && t < tmin) {
				Vector3d hitPoint = Vectors.plus(origin,
						Vectors.scale(direction, t));
				Vector3d normal = Vectors.scale(
						Vectors.plus(temp, Vectors.scale(direction, t)),
						1 / radius);
				sr.setHitsAnObject(true);
				sr.setMaterial(this.getMaterial());
				sr.setNormal(normal);
				sr.setHitPoint(hitPoint);
				sr.setLocalHitPoint(hitPoint);
				sr.setDirection(direction);
				sr.setU(0);
				sr.setV(0);

				return t;
			}
			t = (-b + e) / denom;
			if (t > Constants.KEPS && t < tmin) {
				Vector3d hitPoint = Vectors.plus(origin,
						Vectors.scale(direction, t));
				Vector3d normal = Vectors.scale(
						Vectors.plus(temp, Vectors.scale(direction, t)),
						1 / radius);
				sr.setHitsAnObject(true);
				sr.setMaterial(this.getMaterial());
				sr.setNormal(normal);
				sr.setHitPoint(hitPoint);
				sr.setLocalHitPoint(hitPoint);
				sr.setDirection(direction);
				sr.setU(0);
				sr.setV(0);

				return t;
			}
			return -1.0;
		}
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

			if (t > Constants.KEPS) {
				return t;
			}
			t = (-b + e) / denom;
			if (t > Constants.KEPS) {
				return t;
			}
		}
		return -1;
	}

	@Override
	public BoundingBox getBoundingBox() {
		double x0 = this.center.x - radius;
		double x1 = this.center.x + radius;

		double y0 = this.center.y - radius;
		double y1 = this.center.y + radius;

		double z0 = this.center.z - radius;
		double z1 = this.center.z + radius;

		return new BoundingBox(x0, x1, y0, y1, z0, z1);
	}

	@Override
	public Vector3d getCentroid() {
		return this.center;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sphere other = (Sphere) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		if (Double.doubleToLongBits(radius) != Double
				.doubleToLongBits(other.radius))
			return false;
		return true;
	}

	@Override
	public Vector3d getNormal(Vector3d samplePoint) {
		return Vectors.scale(Vectors.sub(samplePoint, center), 1 / radius);
	}

	@Override
	public Vector3d sample() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pdf(ShadeRec sr) {
		return invArea;
	}

}
