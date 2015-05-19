package ar.edu.itba.gc.mapping;

import javax.vecmath.Point2d;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.world.World;

public class SphericalMap extends Mapping {

	@Override
	public Point2d getTexelCoordinates(Vector3d localHitPoint, int hres, int vres) {
		double theta = Math.acos(localHitPoint.y);
		double phi = Math.atan2(localHitPoint.x, localHitPoint.z);
		System.out.println(localHitPoint.x * localHitPoint.x + localHitPoint.y * localHitPoint.y + localHitPoint.z * localHitPoint.z);
		if (phi < 0) {
			phi += World.TWO_PI;
		}
		double u = phi * World.INV_TWO_PI;
		double v = 1 - theta * World.INV_PI;
		
		Point2d result = new Point2d();
		result.x = (int)((hres - 1) * u);
		result.y = (int)((vres - 1) * v);
		
		return result;
	}
}
