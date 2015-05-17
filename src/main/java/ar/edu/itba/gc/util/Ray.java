package ar.edu.itba.gc.util;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.world.World;

public class Ray {

	private Vector3d origin;
	private Vector3d direction;
	private int depth;
	private double tmin = Double.MAX_VALUE;
	
	public Ray() {
		this.depth = 0;
	}

	public Ray(Vector3d origin, Vector3d direction) {
		this(origin, direction, 0);
		this.origin = origin;
		this.direction = direction;
	}

	public Ray(Vector3d origin, Vector3d direction, int depth) {
		super();
		this.origin = origin;
		this.direction = direction;
		this.depth = depth;
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

	protected int getDepth() {
		return depth;
	}
	
	public ShadeRec hit(World world) {
		ShadeRec sr = new ShadeRec(world);
//		for (GeometricObject obj : world.objects) {
//			sr = obj.hit(sr, this.origin, this.direction);
//		}
		sr = world.tree.hit(this, sr);
		return sr;
	}

	public void setDirection(Vector3d direction) {
		this.direction = direction;
	}
	
}
