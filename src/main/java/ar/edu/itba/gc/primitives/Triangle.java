package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class Triangle extends GeometricObject {

	private Vector3d v0, v1, v2;
	private Vector3d normal;

	public Triangle(Material material, Vector3d v0, Vector3d v1, Vector3d v2,
			Vector3d normal) {
		super(material);
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.normal = normal;
	}

	public Triangle(Material material, Vector3d v0, Vector3d v1, Vector3d v2) {
		super(material);
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.computeNormal();
	}

	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin,
			Vector3d direction) {
		double a = v0.x - v1.x, b = v0.x - v2.x, c = direction.x, d = v0.x
				- origin.x;
		double e = v0.y - v1.y, f = v0.y - v2.y, g = direction.y, h = v0.y
				- origin.y;
		double i = v0.z - v1.z, j = v0.z - v2.z, k = direction.z, l = v0.z
				- origin.z;

		double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
		double q = g * i - e * k, s = e * j - f * i;

		double inv_denom = 1.0 / (a * m + b * q + c * s);

		double e1 = d * m - b * n - c * p;
		double beta = e1 * inv_denom;

		if (beta < 0.0)
			return -1.0;

		double r = e * l - h * i;
		double e2 = a * n + d * q + c * r;
		double gamma = e2 * inv_denom;

		if (gamma < 0.0)
			return -1.0;

		if (beta + gamma > 1.0)
			return -1.0;

		double e3 = a * p - b * r + d * s;
		double t = e3 * inv_denom;

		if (t < Constants.KEPS || t >= tmin)
			return -1.0;

		Vector3d hitPoint = Vectors.plus(origin, Vectors.scale(direction, t));

		sr.setHitsAnObject(true);
		sr.setMaterial(this.getMaterial());
		sr.setNormal(this.normal);
		sr.setHitPoint(hitPoint);
		sr.setLocalHitPoint(hitPoint);
		sr.setDirection(direction);

		return t;
	}

	@Override
	public double shadowHit(Ray ray) {
		double a = v0.x - v1.x, b = v0.x - v2.x, c = ray.getDirection().x, d = v0.x
				- ray.getOrigin().x;
		double e = v0.y - v1.y, f = v0.y - v2.y, g = ray.getDirection().y, h = v0.y
				- ray.getOrigin().y;
		double i = v0.z - v1.z, j = v0.z - v2.z, k = ray.getDirection().z, l = v0.z
				- ray.getOrigin().z;

		double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
		double q = g * i - e * k, s = e * j - f * i;

		double inv_denom = 1.0 / (a * m + b * q + c * s);

		double e1 = d * m - b * n - c * p;
		double beta = e1 * inv_denom;

		if (beta < 0.0)
			return -1;

		double r = e * l - h * i;
		double e2 = a * n + d * q + c * r;
		double gamma = e2 * inv_denom;

		if (gamma < 0.0)
			return -1;

		if (beta + gamma > 1.0)
			return -1;

		double e3 = a * p - b * r + d * s;
		double t = e3 * inv_denom;

		if (t < Constants.KEPS)
			return -1;

		return t;
	}

	private void computeNormal() {
		if (normal != null)
			throw new IllegalStateException();
		Vector3d v = Vectors.sub(v1, v0);
		Vector3d w = Vectors.sub(v2, v0);
		Vector3d auxNormal = new Vector3d(v.y * w.z - v.z * w.y, v.z * w.x
				- v.x * w.z, v.x * w.y - v.y * w.x);
		double sum = Math.abs(auxNormal.x) + Math.abs(auxNormal.y)
				+ Math.abs(auxNormal.z);
		this.normal = new Vector3d(auxNormal.x / sum, auxNormal.y / sum,
				auxNormal.z / sum);
	}

	@Override
	public BoundingBox getBoundingBox() {
		double delta = 0.000001;
		double x0 = Math.min(Math.min(v0.x, v1.x), v2.x) - delta;
		double x1 = Math.max(Math.max(v0.x, v1.x), v2.x) + delta;
		double y0 = Math.min(Math.min(v0.y, v1.y), v2.y) - delta;
		double y1 = Math.max(Math.max(v0.y, v1.y), v2.y) + delta;
		double z0 = Math.min(Math.min(v0.z, v1.z), v2.z) - delta;
		double z1 = Math.max(Math.max(v0.z, v1.z), v2.z) + delta;
		return new BoundingBox(x0, x1, y0, y1, z0, z1);
	}

	@Override
	public Vector3d getCentroid() {
		double x = (v0.x + v1.x + v2.x) / 3;
		double y = (v0.y + v1.y + v2.y) / 3;
		double z = (v0.z + v1.z + v2.z) / 3;
		return new Vector3d(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triangle other = (Triangle) obj;
		if (normal == null) {
			if (other.normal != null)
				return false;
		} else if (!normal.equals(other.normal))
			return false;
		if (v0 == null) {
			if (other.v0 != null)
				return false;
		} else if (!v0.equals(other.v0))
			return false;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		return true;
	}

}
