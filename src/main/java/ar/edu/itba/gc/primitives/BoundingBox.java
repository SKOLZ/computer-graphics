package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.Axis;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;

public class BoundingBox {

	public static BoundingBox bigBox() {
		return new BoundingBox(Double.MIN_VALUE, Double.MAX_VALUE,
				Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE,
				Double.MAX_VALUE);
	}

	private double x0, x1, y0, y1, z0, z1;

	public BoundingBox() {
		super();
		this.x0 = -1;
		this.x1 = 1;
		this.y0 = -1;
		this.y1 = 1;
		this.z0 = -1;
		this.z1 = 1;
	}

	public BoundingBox(double x0, double x1, double y0, double y1, double z0,
			double z1) {
		super();
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		this.z0 = z0;
		this.z1 = z1;
	}

	public boolean hit(Ray ray) {
		double ox = ray.getOrigin().x;
		double oy = ray.getOrigin().y;
		double oz = ray.getOrigin().z;
		double dx = ray.getDirection().x;
		double dy = ray.getDirection().y;
		double dz = ray.getDirection().z;

		double tx_min, ty_min, tz_min;
		double tx_max, ty_max, tz_max;

		double a = 1.0 / dx;
		if (a >= 0) {
			tx_min = (x0 - ox) * a;
			tx_max = (x1 - ox) * a;
		} else {
			tx_min = (x1 - ox) * a;
			tx_max = (x0 - ox) * a;
		}

		double b = 1.0 / dy;
		if (b >= 0) {
			ty_min = (y0 - oy) * b;
			ty_max = (y1 - oy) * b;
		} else {
			ty_min = (y1 - oy) * b;
			ty_max = (y0 - oy) * b;
		}

		double c = 1.0 / dz;
		if (c >= 0) {
			tz_min = (z0 - oz) * c;
			tz_max = (z1 - oz) * c;
		} else {
			tz_min = (z1 - oz) * c;
			tz_max = (z0 - oz) * c;
		}

		double t0, t1;

		// find largest entering t value

		if (tx_min > ty_min)
			t0 = tx_min;
		else
			t0 = ty_min;

		if (tz_min > t0)
			t0 = tz_min;

		// find smallest exiting t value

		if (tx_max < ty_max)
			t1 = tx_max;
		else
			t1 = ty_max;

		if (tz_max < t1)
			t1 = tz_max;

		return (t0 < t1 && t1 > Constants.KEPS);
	}

	public boolean inside(Vector3d p) {
		return ((p.x > x0 && p.x < x1) && (p.y > y0 && p.y < y1) && (p.z > z0 && p.z < z1));
	}

	public void expand(BoundingBox bbox) {
		if (bbox == null)
			return;
		this.x0 = Math.min(this.x0, bbox.x0);
		this.x1 = Math.max(this.x1, bbox.x1);

		this.y0 = Math.min(this.y0, bbox.y0);
		this.y1 = Math.max(this.y1, bbox.y1);

		this.z0 = Math.min(this.z0, bbox.z0);
		this.z1 = Math.max(this.z1, bbox.z1);
	}

	public Axis getLongestAxis() {
		double x = Math.abs(x1 - x0);
		double y = Math.abs(y1 - y0);
		double z = Math.abs(z1 - z0);
		if (x >= y) {
			if (x >= z)
				return Axis.X;
			else
				return Axis.Z;
		} else if (y > z) {
			return Axis.Y;
		} else {
			return Axis.Z;
		}
	}

}
