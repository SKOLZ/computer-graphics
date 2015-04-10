package ar.edu.itba.gc.camera;

import java.awt.image.BufferedImage;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public abstract class Camera {

	private Vector3d eye;
	private Vector3d lookAt;
	private Vector3d up;

	private Vector3d u;
	private Vector3d v;
	private Vector3d w;

	public Camera(Vector3d eye, Vector3d lookAt) {
		this(eye, lookAt, new Vector3d(0, 1, 0));
	}

	public Camera(Vector3d eye, Vector3d lookAt, Vector3d up) {
		super();
		this.eye = eye;
		this.lookAt = lookAt;
		this.up = up;
		this.u = new Vector3d();
		this.v = new Vector3d();
		this.w = new Vector3d();
	}

	public Vector3d getEye() {
		return eye;
	}

	public Vector3d getLookAt() {
		return lookAt;
	}

	public Vector3d getUp() {
		return up;
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

	public abstract BufferedImage renderScene(World w);
}
