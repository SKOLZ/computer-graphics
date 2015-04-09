package ar.edu.itba.gc.lights;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.ShadeRec;
import ar.edu.itba.gc.utils.Vectors;

public class PointLight extends Light {
	
	private Vector3d location;

	protected PointLight(Vector3d location) {
		super(1.0, new RGBColor(1, 1, 1));
		this.location = location;
	}

	
	@Override
	public Vector3d getDirection(ShadeRec sr) {
		Vector3d direction = Vectors.sub(location, sr.getHitPoint()); 
		direction.normalize();
		return direction;
	}
	
	public Vector3d getLocation() {
		return location;
	}
	
}
