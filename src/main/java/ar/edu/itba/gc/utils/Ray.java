package ar.edu.itba.gc.utils;

import java.util.List;

import javax.vecmath.Vector4d;

import ar.edu.itba.gc.objects.GeometricObject;

public class Ray {

	private Vector4d origin;
	private Vector4d direction;
	private double tmin = Double.MAX_VALUE;
		
	public Ray() {
	}

	public Ray(Vector4d origin, Vector4d direction) {
		super();
		this.origin = origin;
		this.direction = direction;
	}
	
	public Vector4d getOrigin() {
		return origin;
	}
	
	public Vector4d getDirection() {
		return direction;
	}
	
	public double getTmin() {
		return tmin;
	}
	
	public ShadeRec hit(List<GeometricObject> objects, RGBColor background) {
		ShadeRec sr = new ShadeRec(background);
		for (GeometricObject obj : objects) {
			double t = obj.hit(this.origin, this.direction);
			if (t > 0.0 && t < this.tmin) {
				this.tmin = t;
				sr = new ShadeRec(true, obj.getColor());
			}
		}
		return sr;
	}
	
}
