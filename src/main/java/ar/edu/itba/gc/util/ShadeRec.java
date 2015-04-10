package ar.edu.itba.gc.util;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;

public class ShadeRec {

	private boolean hitAnObject;
	private Vector3d hitPoint;
	private Vector3d localHitPoint;

	// added this for lights check if used
	private Material material;
	private Vector3d direction;
	private double t;
	private Vector3d normal;

	public ShadeRec() {
		super();
		this.hitAnObject = false;
		this.t = Double.MAX_VALUE;
	}

	public ShadeRec(boolean hitAnObject, Material material, Vector3d normal,
			Vector3d hitPoint, Vector3d localHitPoint, Vector3d direction, double t) {
		this.hitAnObject = hitAnObject;
		this.material = material;
		this.normal = normal;
		this.hitPoint = hitPoint;
		this.localHitPoint = localHitPoint;
		this.direction = direction;
		this.t = t;
	}

	public boolean hitsAnObject() {
		return hitAnObject;
	}

	public Vector3d getHitPoint() {
		return hitPoint;
	}

	public Vector3d getLocalHitPoint() {
		return localHitPoint;
	}

	public Material getMaterial() {
		return material;
	}

	public Vector3d getDirection() {
		return direction;
	}

	public double getT() {
		return t;
	}

	public Vector3d getNormal() {
		return normal;
	}

}
