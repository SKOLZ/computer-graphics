package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;

public abstract class GeometricObject {

	private Material material;
	public static final double kEps = 0.001;

	public GeometricObject(Material material) {
		this.material = material;
	}

	public Material getMaterial() {
		return material;
	}

	public abstract ShadeRec hit(ShadeRec sr, Vector3d origin,
			Vector3d direction);

	public abstract double shadowHit(Ray ray);

}
