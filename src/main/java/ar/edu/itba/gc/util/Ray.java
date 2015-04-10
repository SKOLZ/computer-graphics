package ar.edu.itba.gc.util;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.world.World;

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
	
	public ShadeRec hit(World world) {
		ShadeRec sr = new ShadeRec();
		for (GeometricObject obj : world.objects) {
			sr = obj.hit(sr, this.origin, this.direction);
		}
		return sr;
	}
	
}
