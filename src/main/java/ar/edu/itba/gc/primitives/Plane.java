package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.materials.Material;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Vectors;

public class Plane extends GeometricObject {

	private Vector3d point;
	private Vector3d normal;

	public Plane(Material material, RGBColor color, Vector3d point,
			Vector3d normal) {
		super(material, color);
		this.point = point;
		this.normal = normal;
	}

	protected Vector3d getPoint() {
		return point;
	}

	protected Vector3d getNormal() {
		return normal;
	}

	@Override
	public double hit(Vector3d origin, Vector3d direction) {
		double t = Vectors.sub(point, origin).dot(normal)
				/ direction.dot(normal);

		if (t > kEps) {
			return t;
		} else {
			return -1.0;
		}
	}

}
