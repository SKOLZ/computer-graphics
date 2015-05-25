package ar.edu.itba.gc.util;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

public class Matrixes {

	public static Vector3d matrixMultPoint(Matrix4d m, Vector3d v) {
		Vector3d result = new Vector3d();
		result.x = m.m00 * v.x + m.m01 * v.y + m.m02 * v.z + m.m03;
		result.y = m.m10 * v.x + m.m11 * v.y + m.m12 * v.z + m.m13;
		result.z = m.m20 * v.x + m.m21 * v.y + m.m22 * v.z + m.m23;
		return result;
	}
	
	public static Vector3d matrixMultVector(Matrix4d m, Vector3d v) {
		Vector3d result = new Vector3d();
		result.x = m.m00 * v.x + m.m01 * v.y + m.m02 * v.z;
		result.y = m.m10 * v.x + m.m11 * v.y + m.m12 * v.z;
		result.z = m.m20 * v.x + m.m21 * v.y + m.m22 * v.z;
		return result;
	}
	
	public static Matrix4d newIdenty4d() {
		Matrix4d m = new Matrix4d();
		m.m00 = 1;
		m.m11 = 1;
		m.m22 = 1;
		m.m33 = 1;
		return m;
	}
	
	public static Matrix4d inverse(Matrix4d m) {
		Matrix4d result = new Matrix4d();
		result.invert(m);
		return result;
	}

	public static Matrix4d transpose(Matrix4d m) {
		Matrix4d result = new Matrix4d();
		result.transpose(m);
		return result;
	}
	
}
