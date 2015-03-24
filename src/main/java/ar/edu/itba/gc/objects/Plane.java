package ar.edu.itba.gc.objects;

import javax.vecmath.Vector4d;

import ar.edu.itba.gc.materials.Material;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Vectors;

public class Plane extends GeometricObject {

	private Vector4d point;
	private Vector4d normal;

	public Plane(Material material, RGBColor color, Vector4d point,
			Vector4d normal) {
		super(material, color);
		this.point = point;
		this.normal = normal;
	}

	protected Vector4d getPoint() {
		return point;
	}

	protected Vector4d getNormal() {
		return normal;
	}

	@Override
	public double hit(Vector4d origin, Vector4d direction) {
		double t = Vectors.sub(point, origin).dot(normal)
				/ direction.dot(normal);

		if (t > kEps) {
			return t;
		} else {
			return -1.0;
		}
	}

}
