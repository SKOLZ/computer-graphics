package ar.edu.itba.gc.mapping;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

public class RectangularMap extends Mapping {

	@Override
	public Point2d getTexelCoordinates(Vector3d localHitPoint, int hres,
			int vres) {
		double u = (localHitPoint.z + 1)/2;
		double v = (localHitPoint.x + 1)/2;
		
		Point2d result = new Point2d();
		result.x = (int)(((hres - 1) * u)) % hres;
		result.y = (int)(((vres - 1) * v)) % vres;
		return result;
	}

}
