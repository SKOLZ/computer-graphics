package ar.edu.itba.gc.cameras;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.utils.Vectors;
import ar.edu.itba.gc.world.ViewPlane;

public abstract class Camera {

	private Vector3d eye;
	private Vector3d lookAt;
	private Vector3d up;
	
	private Vector3d u;
	private Vector3d v;
	private Vector3d w;
	
	public Vector3d getEye() {
		return eye;
	}
	
	public void setEye(Vector3d eye) {
		this.eye = eye;
	}

	public Vector3d getLookAt() {
		return lookAt;
	}
	
	public void setLookAt(Vector3d lookAt) {
		this.lookAt = lookAt;
	}
	
	public Vector3d getUp() {
		return up;
	}
	
	public void setUp(Vector3d up) {
		this.up = up;
	}
	
	public Vector3d getU() {
		return u;
	}
	
	public Vector3d getV() {
		return v;
	}
	
	public Vector3d getW() {
		return w;
	}
	
	public void computeUVW() {
		w = Vectors.sub(eye, lookAt);
		w.normalize();
		u.cross(up, w);
		u.normalize();
		v.cross(w, u);
		
		if (eye.x == lookAt.x && eye.z == lookAt.z && eye.y > lookAt.y) {
			u = new Vector3d(0, 0, 1);
			v = new Vector3d(1, 0, 0);
			w = new Vector3d(0, 1, 0);	
		}
		if (eye.x == lookAt.x && eye.z == lookAt.z && eye.y < lookAt.y) {
			u = new Vector3d(1, 0, 0);
			v = new Vector3d(0, 0, 1);
			w = new Vector3d(0, -1, 0);
		}
	}
	
	public abstract void renderScene(ViewPlane vp);
}
