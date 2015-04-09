package ar.edu.itba.gc.utils;

import javax.vecmath.Vector3d;

public class Vectors {

	public static Vector3d sub(final Vector3d v1, final Vector3d v2) {
		Vector3d ret = new Vector3d(v1);
		ret.sub(v2);
		return ret;
	}
	
	public static Vector3d plus(final Vector3d v1, final Vector3d v2) {
		Vector3d ret = new Vector3d(v1);
		ret.add(v2);
		return ret;
	}
	
	public static Vector3d scale(final Vector3d v1, final double d) {
		Vector3d ret = new Vector3d(v1);
		ret.scale(d);
		return ret;
	}
	
}
