package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.materials.Material;
import ar.edu.itba.gc.utils.RGBColor;

public abstract class GeometricObject {
	
	private Material material;
	private RGBColor color;
	public static final double kEps = 0.0001;

	public GeometricObject(Material material, RGBColor color) {
		this.material = material;
		this.color = color;
	}

	public Material getMaterial() {
		return material;
	}

	public RGBColor getColor() {
		return color;
	}

	public abstract double hit(Vector3d origin, Vector3d direction);
}
