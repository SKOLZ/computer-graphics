package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

abstract class BRDF {

	RGBColor f(ShadeRec sr, Vector3d wo, Vector3d wi) {
		return RGBColor.black();
	}
	
	RGBColor rho() {
		return RGBColor.black();
	}
	
}
