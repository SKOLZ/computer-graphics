package ar.edu.itba.gc.mapping;

import javax.vecmath.Point2d;
import javax.vecmath.Vector3d;

public abstract class Mapping {

	public abstract Point2d getTexelCoordinates(Vector3d localHitPoint, int hres, int vres);
	
}
