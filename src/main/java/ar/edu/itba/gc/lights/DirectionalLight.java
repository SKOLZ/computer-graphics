package ar.edu.itba.gc.lights;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.ShadeRec;

public class DirectionalLight extends Light{

	private Vector3d direction;
	
	public DirectionalLight(Vector3d direction) {
		super(1.0, new RGBColor(1, 1, 1));

	}

	@Override
	public Vector3d getDirection(ShadeRec sr) {
		return direction;
	}

}
