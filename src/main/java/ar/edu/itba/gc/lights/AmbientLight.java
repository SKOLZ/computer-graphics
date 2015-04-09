package ar.edu.itba.gc.lights;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.ShadeRec;

public class AmbientLight extends Light {

	
	public AmbientLight() {
		super(1.0, new RGBColor(1, 1, 1));
	}


	@Override
	public Vector3d getDirection(ShadeRec sr) {
		return new Vector3d(0,0,0,0);
	}

}
