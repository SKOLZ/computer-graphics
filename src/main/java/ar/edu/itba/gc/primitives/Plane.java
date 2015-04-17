package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class Plane extends GeometricObject {

	private Vector3d point;
	private Vector3d normal;

	public Plane(Material material, Vector3d point, Vector3d normal) {
		super(material);
		this.point = point;
		this.normal = normal;
	}

	protected Vector3d getPoint() {
		return point;
	}

	protected Vector3d getNormal() {
		return normal;
	}

	@Override
	public ShadeRec hit(ShadeRec sr, Vector3d origin, Vector3d direction) {
		double t = Vectors.sub(point, origin).dot(normal)
				/ direction.dot(normal);

		if (t > kEps) {
			if (t < sr.getT()) {
				Vector3d hitPoint = Vectors.plus(origin,
						Vectors.scale(direction, t));

				return new ShadeRec(true, sr.getWorld(), this.getMaterial(),
						hitPoint, hitPoint, this.normal, direction, t);
			}
		}
		return sr;
	}

	@Override
	public double shadowHit(Ray ray) {
		double t = Vectors.sub(this.point, ray.getOrigin()).dot(this.normal)
				/ ray.getDirection().dot(this.normal);
		if (t > kEps)
			return t;
		return -1;
	}

}
