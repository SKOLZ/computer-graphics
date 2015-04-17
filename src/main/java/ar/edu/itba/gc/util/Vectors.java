package ar.edu.itba.gc.util;

import javax.vecmath.Vector3d;

public class Vectors {

	public static double distance(Vector3d first, Vector3d second) {
		Vector3d sub = sub(first, second);
		return Math.sqrt(Math.pow(sub.x, 2) + Math.pow(sub.y, 2)
				+ Math.pow(sub.z, 2));
	}

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
