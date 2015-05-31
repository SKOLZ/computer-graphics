package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class Box extends GeometricObject {

	private double x0, y0, z0;
	private double x1, y1, z1;

	public Box(Material material, Vector3d p0, Vector3d p1) {
		super(material);
		this.x0 = p0.x;
		this.y0 = p0.y;
		this.z0 = p0.z;
		this.x1 = p1.x;
		this.y1 = p1.y;
		this.z1 = p1.z;
	}

	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin,
			Vector3d direction) {

		int faceIn;
		int faceOut;
		double t0;
		double t1;

		double txmin, tymin, tzmin;
		double txmax, tymax, tzmax;
		double a = 1.0 / direction.x;
		if (a >= 0) {
			txmin = (x0 - origin.x) * a;
			txmax = (x1 - origin.x) * a;
		} else {
			txmin = (x1 - origin.x) * a;
			txmax = (x0 - origin.x) * a;
		}
		double b = 1.0 / direction.y;
		if (b >= 0) {
			tymin = (y0 - origin.y) * b;
			tymax = (y1 - origin.y) * b;
		} else {
			tymin = (y1 - origin.y) * b;
			tymax = (y0 - origin.y) * b;
		}
		double c = 1.0 / direction.z;
		if (c >= 0) {
			tzmin = (z0 - origin.z) * c;
			tzmax = (z1 - origin.z) * c;
		} else {
			tzmin = (z1 - origin.z) * c;
			tzmax = (z0 - origin.z) * c;
		}

		if (txmin > tymin) {
			t0 = txmin;
			faceIn = a >= 0 ? 0 : 3;
		} else {
			t0 = tymin;
			faceIn = b >= 0 ? 1 : 4;
		}
		if (tzmin > t0) {
			t0 = tzmin;
			faceIn = c >= 0 ? 2 : 5;
		}

		if (txmax < tymax) {
			t1 = txmax;
			faceOut = a >= 0 ? 3 : 0;
		} else {
			t1 = tymax;
			faceOut = b >= 0 ? 4 : 1;
		}
		if (tzmax < t1) {
			t1 = tzmax;
			faceOut = c >= 0 ? 5 : 2;
		}

		if (t0 < t1 && t1 > Constants.KEPS) {
			if (t0 > Constants.KEPS) {
				tmin = t0;
				sr.setNormal(getNormal(faceIn));
			} else {
				tmin = t1;
				sr.setNormal(getNormal(faceOut));
			}
			Vector3d hitPoint = Vectors.plus(origin, Vectors.scale(direction, tmin));
			
			sr.setHitsAnObject(true);
			sr.setMaterial(this.getMaterial());
			sr.setHitPoint(hitPoint);
			sr.setLocalHitPoint(hitPoint);
			sr.setDirection(direction);
			sr.setU(0);
			sr.setV(0);
			return tmin;
		} else {
			return -1;
		}
	}

	private Vector3d getNormal(int faceHit) {
		switch (faceHit) {
		case 0:
			return new Vector3d(-1, 0, 0);
		case 1:
			return new Vector3d(0, -1, 0);
		case 2:
			return new Vector3d(0, 0, -1);
		case 3:
			return new Vector3d(1, 0, 0);
		case 4:
			return new Vector3d(0, 1, 0);
		case 5:
			return new Vector3d(0, 0, 1);
		}
		return null;
	}

	@Override
	public double shadowHit(Ray ray) {
		double t0;
		double t1;

		double txmin, tymin, tzmin;
		double txmax, tymax, tzmax;
		double a = 1.0 / ray.getDirection().x;
		if (a >= 0) {
			txmin = (x0 - ray.getOrigin().x) * a;
			txmax = (x1 - ray.getOrigin().x) * a;
		} else {
			txmin = (x1 - ray.getOrigin().x) * a;
			txmax = (x0 - ray.getOrigin().x) * a;
		}
		double b = 1.0 / ray.getDirection().y;
		if (b >= 0) {
			tymin = (y0 - ray.getOrigin().y) * b;
			tymax = (y1 - ray.getOrigin().y) * b;
		} else {
			tymin = (y1 - ray.getOrigin().y) * b;
			tymax = (y0 - ray.getOrigin().y) * b;
		}
		double c = 1.0 / ray.getDirection().z;
		if (c >= 0) {
			tzmin = (z0 - ray.getOrigin().z) * c;
			tzmax = (z1 - ray.getOrigin().z) * c;
		} else {
			tzmin = (z1 - ray.getOrigin().z) * c;
			tzmax = (z0 - ray.getOrigin().z) * c;
		}

		if (txmin > tymin) {
			t0 = txmin;
		} else {
			t0 = tymin;
		}
		if (tzmin > t0) {
			t0 = tzmin;
		}

		if (txmax < tymax) {
			t1 = txmax;
		} else {
			t1 = tymax;
		}
		if (tzmax < t1) {
			t1 = tzmax;
		}

		if (t0 < t1 && t1 > Constants.KEPS) {
			if (t0 > Constants.KEPS) {
				return t0;
			} else {
				return t1;
			}
		} else {
			return -1;
		}
	}

	@Override
	public BoundingBox getBoundingBox() {
		double minx = Math.min(x0, x1);
		double miny = Math.min(y0, y1);
		double minz = Math.min(z0, z1);
		double maxx = Math.max(x0, x1);
		double maxy = Math.max(y0, y1);
		double maxz = Math.max(z0, z1);
		return new BoundingBox(minx, maxx, miny, maxy, minz, maxz);
	}

	@Override
	public Vector3d getCentroid() {
		double midx = Math.abs(x0 - x1)/2;
		double midy = Math.abs(y0 - y1)/2;
		double midz = Math.abs(z0 - z1)/2;
		return new Vector3d(midx, midy, midz);
	}

}
