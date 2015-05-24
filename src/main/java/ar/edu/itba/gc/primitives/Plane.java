package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Constants;
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
		count++;
		double t = Vectors.sub(point, origin).dot(normal)
				/ direction.dot(normal);

		if (t > Constants.KEPS) {
			if (t < sr.getT()) {
				Vector3d hitPoint = Vectors.plus(origin,
						Vectors.scale(direction, t));

				return new ShadeRec(true, sr.getWorld(), this.getMaterial(),
						this.normal, hitPoint, hitPoint, direction, t, 0, 0,
						sr.getDepth());
			}
		}
		return sr;
	}

	@Override
	public double shadowHit(Ray ray) {
		double t = Vectors.sub(this.point, ray.getOrigin()).dot(this.normal)
				/ ray.getDirection().dot(this.normal);
		if (t > Constants.KEPS)
			return t;
		return -1;
	}

	@Override
	public BoundingBox getBoundingBox() {
		return BoundingBox.bigBox();
	}

	@Override
	public Vector3d getCentroid() {
		return this.point;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plane other = (Plane) obj;
		if (normal == null) {
			if (other.normal != null)
				return false;
		} else if (!normal.equals(other.normal))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}

}
