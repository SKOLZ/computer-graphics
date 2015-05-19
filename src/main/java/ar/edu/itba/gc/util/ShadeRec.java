package ar.edu.itba.gc.util;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.world.World;

public class ShadeRec {

	private boolean hitAnObject;
	private Vector3d hitPoint;
	private Vector3d localHitPoint;

	private World world;
	private Material material;
	private Vector3d direction;
	private double t;
	private double u;
	private double v;
	private Vector3d normal;
	
	private int depth;
	

	public ShadeRec(World world) {
		super();
		this.hitAnObject = false;
		this.world = world;
		this.t = Double.MAX_VALUE;
		this.depth = 0;
	}

	public ShadeRec(boolean hitAnObject, World world, Material material, Vector3d normal,
			Vector3d hitPoint, Vector3d localHitPoint, Vector3d direction, double t, double u, double v, int depth) {
		this.hitAnObject = hitAnObject;
		this.world = world;
		this.material = material;
		this.normal = normal;
		this.hitPoint = hitPoint;
		this.localHitPoint = localHitPoint;
		this.direction = direction;
		this.t = t;
		this.u = u;
		this.v = v;
		this.depth = depth;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
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

	public void setDirection(Vector3d direction) {
		this.direction = direction;
	}

	public double getT() {
		return t;
	}

	public Vector3d getNormal() {
		return normal;
	}

	public World getWorld() {
		return world;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setNormal(Vector3d normal) {
		this.normal = normal;
	}

	public void setLocalHitPoint(Vector3d localHitPoint) {
		this.localHitPoint = localHitPoint;
	}

}
