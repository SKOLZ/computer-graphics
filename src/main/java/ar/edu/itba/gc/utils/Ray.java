package ar.edu.itba.gc.utils;

import java.util.List;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.primitives.GeometricObject;

public class Ray {

	private Vector3d origin;
	private Vector3d direction;
	private double tmin = Double.MAX_VALUE;
		
	public Ray() {
	}

	public Ray(Vector3d origin, Vector3d direction) {
		super();
		this.origin = origin;
		this.direction = direction;
	}
	
	public void setOrigin(Vector3d origin) {
		this.origin = origin;
	}

	public Vector3d getOrigin() {
		return origin;
	}
	
	public Vector3d getDirection() {
		return direction;
	}
	
	public double getTmin() {
		return tmin;
	}
	
	public ShadeRec hit(List<GeometricObject> objects, RGBColor background) {
		ShadeRec sr = new ShadeRec();
		Vector3d normal;
		Vector3d localHitPoint;
		for (GeometricObject obj : objects) {
			double t = obj.hit(this.origin, this.direction);
			if (t > 0.0 && t < this.tmin) {
				this.tmin = t;
				Vector3d hitPoint = Vectors.plus(origin, Vectors.scale(direction, t)); 
				sr = new ShadeRec(true, obj.getMaterial(), hitPoint);
				normal = sr.getNormal();
				localHitPoint = sr.getLocalHitPoint();
			}
		}
		return sr;
	}
	
}
