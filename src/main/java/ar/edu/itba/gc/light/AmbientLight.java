package ar.edu.itba.gc.light;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;

public class AmbientLight extends Light {

	public static Light white() {
		return new AmbientLight();
	}
	
	public static Light black() {
		return new AmbientLight(new RGBColor(0,0,0));
	}
	
	public AmbientLight() {
		this(new RGBColor(1, 1, 1));
	}
	
	public AmbientLight(RGBColor color) {
		super(true, 1.0, color);
	}

	@Override
	public Vector3d getDirection(ShadeRec sr) {
		return new Vector3d(0, 0, 0);
	}

	@Override
	public boolean inShadow(Ray ray, ShadeRec sr) {
		return false;
	}

}
