package ar.edu.itba.gc.parser.world.transform;

import javax.vecmath.Vector3d;

class Scaling {

	private Vector3d scalingVector;

	Scaling(Vector3d scalingVector) {
		super();
		this.scalingVector = scalingVector;
	}

	double getX() {
		return scalingVector.x;
	}

	double getY() {
		return scalingVector.y;
	}

	double getZ() {
		return scalingVector.z;
	}

}
