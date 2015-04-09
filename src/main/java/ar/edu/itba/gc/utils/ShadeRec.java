package ar.edu.itba.gc.utils;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.materials.Material;

public class ShadeRec {

	private boolean hitAnObject;
	private Vector3d hitPoint;
	
	//added this for lights check if used
	private Material material;
	private Ray ray;
	private int depth;
	private Vector3d direction;
	
	public ShadeRec() {
		super();
		this.hitAnObject = false;
	}

	public ShadeRec(boolean hitAnObject, Material material, Vector3d hitPoint) {
		this.hitAnObject = hitAnObject;
		this.material = material;
		this.hitPoint = hitPoint;
	}

	public boolean hitsAnObject() {
		return hitAnObject;
	}

	public Vector3d getHitPoint() {
		return hitPoint;
	}
	
	

}
