package ar.edu.itba.gc.primitives.mesh;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;

public class FlatMeshTriangle extends MeshTriangle {

	public FlatMeshTriangle(int index0, int index1, int index2, final Mesh mesh, final boolean reverse) {
		super(index0, index1, index2, mesh, reverse);
	}

	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin, Vector3d direction) {
		if (!getBoundingBox().hit(origin, direction)) {
			return Double.NEGATIVE_INFINITY;
		}

		double a = p0.x - p1.x, b = p0.x - p2.x, c = direction.x, d = p0.x - origin.x;
		double e = p0.y - p1.y, f = p0.y - p2.y, g = direction.y, h = p0.y - origin.y;
		double i = p0.z - p1.z, j = p0.z - p2.z, k = direction.z, l = p0.z - origin.z;

		double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
		double q = g * i - e * k, r = e * l - h * i, s = e * j - f * i;

		double inv_denom = 1.0 / (a * m + b * q + c * s);

		double e1 = d * m - b * n - c * p;
		double beta = e1 * inv_denom;

		if (beta < 0.0 || beta > 1.0) {
			return Double.NEGATIVE_INFINITY;
		}

		double e2 = a * n + d * q + c * r;
		double gamma = e2 * inv_denom;

		if (gamma < 0.0 || gamma > 1.0) {
			return Double.NEGATIVE_INFINITY;
		}

		if (beta + gamma > 1.0) {
			return Double.NEGATIVE_INFINITY;
		}

		double e3 = a * p - b * r + d * s;
		double t = e3 * inv_denom;

		if (t < Constants.KEPS) {
			return Double.NEGATIVE_INFINITY;
		}

		// ray.origin.add(ray.direction.scale(t));
		double x = origin.x + (direction.x * t);
		double y = origin.y + (direction.y * t);
		double z = origin.z + (direction.z * t);

		sr.setHitsAnObject(true);
		sr.setMaterial(this.getMaterial());
		sr.setNormal(normal);
		sr.setLocalHitPoint(new Vector3d(x, y, z));
		sr.setHitPoint(sr.getLocalHitPoint());
		sr.setDirection(direction);

		if (mesh.u != null && mesh.v != null) {
			sr.setU(interpolateU(beta, gamma));
			sr.setV(interpolateV(beta, gamma));
		}

		return t;
	}

	@Override
	public double shadowHit(Ray ray) {
		if (!getBoundingBox().hit(ray.getOrigin(), ray.getDirection())) {
			return Double.NEGATIVE_INFINITY;
		}

		Vector3d v0 = mesh.vertices[index0];
		Vector3d v1 = mesh.vertices[index1];
		Vector3d v2 = mesh.vertices[index2];

		double a = v0.x - v1.x, b = v0.x - v2.x, c = ray.getDirection().x, d = v0.x - ray.getOrigin().x;
		double e = v0.y - v1.y, f = v0.y - v2.y, g = ray.getDirection().y, h = v0.y - ray.getOrigin().y;
		double i = v0.z - v1.z, j = v0.z - v2.z, k = ray.getDirection().z, l = v0.z - ray.getOrigin().z;

		double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
		double q = g * i - e * k, r = e * l - h * i, s = e * j - f * i;

		double inv_denom = 1.0 / (a * m + b * q + c * s);

		double e1 = d * m - b * n - c * p;
		double beta = e1 * inv_denom;

		if (beta < 0.0 || beta > 1.0) {
			return Double.NEGATIVE_INFINITY;
		}

		double e2 = a * n + d * q + c * r;
		double gamma = e2 * inv_denom;

		if (gamma < 0.0 || gamma > 1.0) {
			return Double.NEGATIVE_INFINITY;
		}

		if (beta + gamma > 1.0) {
			return Double.NEGATIVE_INFINITY;
		}

		double e3 = a * p - b * r + d * s;
		double t = e3 * inv_denom;

		if (t < Constants.KEPS) {
			return Double.NEGATIVE_INFINITY;
		}

		return t;
	}

}
