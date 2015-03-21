package primitives;

import javax.vecmath.Vector4d;

public class Ray {

	private Vector4d origin;
	private Vector4d direction;
	
	public Ray(Vector4d origin, Vector4d direction) {
		super();
		this.origin = origin;
		this.direction = direction;
	}
	
	public Vector4d getOrigin() {
		return origin;
	}
	
	public Vector4d getDirection() {
		return direction;
	}
	
	
}
