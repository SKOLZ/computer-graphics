package primitives;

import javax.vecmath.Vector4d;

import utils.Vectors;

public class Plane extends GeometricObject {

	private Vector4d point;
	private Vector4d normal;
	
	public Plane(Material material, RGBColor color, Vector4d point, Vector4d normal) {
		super(material, color);
		this.point = point;
		this.normal = normal;
	}
	
	@Override
	protected boolean hit(Ray ray, double tmin) {
		
		double t = Vectors.sub(point, ray.getOrigin()).dot(normal) / ray.getDirection().dot(normal);
		
		if (t > kEps) {
			tmin = t;
			return true;
		} else {
			return false;
		}
	}

}
