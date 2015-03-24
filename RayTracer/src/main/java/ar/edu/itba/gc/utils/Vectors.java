package ar.edu.itba.gc.utils;

import javax.vecmath.Vector4d;

public class Vectors {

	public static Vector4d sub(final Vector4d v1, final Vector4d v2) {
		Vector4d ret = new Vector4d(v1);
		ret.sub(v2);
		return ret;
	}
	
	public static Vector4d plus(final Vector4d v1, final Vector4d v2) {
		Vector4d ret = new Vector4d(v1);
		ret.add(v2);
		return ret;
	}
	
	public static Vector4d scale(final Vector4d v1, final double d) {
		Vector4d ret = new Vector4d(v1);
		ret.scale(d);
		return ret;
	}
	
}
