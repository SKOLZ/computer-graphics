package ar.edu.itba.gc.parser.world.transform;

import javax.vecmath.Vector3d;

class Rotation {

	private String axis;
	private Double degrees;

	Rotation(Vector3d direction, Double degrees) {
		super();
		if (direction.x != 0) {
			axis = "X";
		} else if (direction.y != 0) {
			axis = "Y";
		} else {
			axis = "Z";
		}
		this.degrees = degrees;
	}

	String getAxis() {
		return axis;
	}

	Double getDegrees() {
		return degrees;
	}

}
