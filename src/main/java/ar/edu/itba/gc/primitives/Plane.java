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
	public double hit(ShadeRec sr, double tmin, Vector3d origin, Vector3d direction) {
		count++;
		double t = Vectors.sub(point, origin).dot(normal)
				/ direction.dot(normal);

		if (t > Constants.KEPS) {
			if (t < tmin) {
				Vector3d hitPoint = Vectors.plus(origin,
						Vectors.scale(direction, t));

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
		}
		return -1.0;
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
