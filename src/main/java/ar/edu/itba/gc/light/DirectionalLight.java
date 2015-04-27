package ar.edu.itba.gc.light;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class DirectionalLight extends Light {

	public static Light upWhite() {
		return new DirectionalLight(new Vector3d(0, 1, 0));
	}

	public static Light downWhite() {
		return new DirectionalLight(new Vector3d(0, -1, 0));
	}

	private Vector3d direction;

	public DirectionalLight(Vector3d direction) {
		this(direction, new RGBColor(1, 1, 1));
	}

	public DirectionalLight(Vector3d direction, RGBColor color) {
		super(true, 1.0, color);
		this.direction = direction;
	}

	@Override
	public Vector3d getDirection(ShadeRec sr) {
		return Vectors.scale(direction, -1);
	}

	@Override
	public boolean inShadow(Ray ray, ShadeRec sr) {
		return false;
	}

}
