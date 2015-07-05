package ar.edu.itba.gc.primitives.mesh;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;

public class SmoothMeshTriangle extends MeshTriangle {

	public SmoothMeshTriangle(int index0, int index1, int index2, Mesh mesh, final boolean reverse) {
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
		sr.setNormal(interpolateNormal(beta, gamma));
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

		double a = p0.x - p1.x, b = p0.x - p2.x, c = ray.getDirection().x, d = p0.x - ray.getOrigin().x;
		double e = p0.y - p1.y, f = p0.y - p2.y, g = ray.getDirection().y, h = p0.y - ray.getOrigin().y;
		double i = p0.z - p1.z, j = p0.z - p2.z, k = ray.getDirection().z, l = p0.z - ray.getOrigin().z;

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

	public Vector3d interpolateNormal(final double beta, final double gamma) {
		final Mesh mesh = this.mesh;
		Vector3d n1 = mesh.normals[index0];
		double n1x = n1.x * (1 - beta - gamma);
		double n1y = n1.y * (1 - beta - gamma);
		double n1z = n1.z * (1 - beta - gamma);

		Vector3d n2 = mesh.normals[index1];
		double n2x = n2.x * (beta);
		double n2y = n2.y * (beta);
		double n2z = n2.z * (beta);

		Vector3d n3 = mesh.normals[index2];
		double n3x = n3.x * (gamma);
		double n3y = n3.y * (gamma);
		double n3z = n3.z * (gamma);

		double x = n1x + n2x + n3x;
		double y = n1y + n2y + n3y;
		double z = n1z + n2z + n3z;

		double length = x * x + y * y + z * z;

		return new Vector3d(x / length, y / length, z / length);
	}

}
