package ar.edu.itba.gc.parser.global;

import javax.vecmath.Vector3d;

class LookAt {

	private Vector3d eye;
	private Vector3d lookAt;
	private Vector3d up;

	LookAt(Vector3d eye, Vector3d lookAt, Vector3d up) {
		super();
		this.eye = eye;
		this.lookAt = lookAt;
		this.up = up;
	}

	Vector3d getEye() {
		return eye;
	}

	Vector3d getLookAt() {
		return lookAt;
	}

	Vector3d getUp() {
		return up;
	}

}
