package ar.edu.itba.gc.util;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.world.World;

public class Ray {

	public static int count = 0;
	private Vector3d origin;
	private Vector3d direction;
	private int depth;

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

	protected int getDepth() {
		return depth;
	}

	public ShadeRec hit(World world) {
		count++;
		ShadeRec sr = new ShadeRec(world);
		double t = Double.MAX_VALUE;
		for (GeometricObject obj : world.getObjects()) {
			double auxT = obj.hit(sr, t, origin, direction);
			if (auxT > 0 && sr.hitsAnObject()) {
				t = auxT;
			}
		}
		return sr;
	}

	public void setDirection(Vector3d direction) {
		this.direction = direction;
	}

}
