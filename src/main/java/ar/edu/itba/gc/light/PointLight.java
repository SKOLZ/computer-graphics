package ar.edu.itba.gc.light;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class PointLight extends Light {

	private Vector3d location;

	public PointLight(double ls, RGBColor color, Vector3d location) {
		super(ls, color);
		this.location = location;
	}
	
	public PointLight(double ls, Vector3d location) {
		this(ls, new RGBColor(1,1,1), location);
	}
	
	public PointLight(Vector3d location) {
		this(1.0, new RGBColor(1,1,1), location);
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
