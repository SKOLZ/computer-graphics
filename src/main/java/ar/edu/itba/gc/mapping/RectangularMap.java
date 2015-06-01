package ar.edu.itba.gc.mapping;

import javax.vecmath.Point2d;
import javax.vecmath.Vector3d;

public class RectangularMap extends Mapping {

	@Override
	public Point2d getTexelCoordinates(Vector3d localHitPoint, int hres,
			int vres) {
		double u = Math.abs(localHitPoint.x);
		double v = Math.abs(localHitPoint.z);
		
		Point2d result = new Point2d();
		result.x = (int)(u % hres);
		result.y = (int)(v % vres);
		return result;
	}

}
