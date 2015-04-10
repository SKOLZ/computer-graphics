package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

class Lambertian extends BRDF {

	private static double INV_PI = 0.3183098861837906715;
	
	private double kd;
	private RGBColor cd;

	Lambertian(double kd, RGBColor cd) {
		super();
		this.kd = kd;
		this.cd = cd;
	}
	
	@Override
	RGBColor f(ShadeRec sr, Vector3d wo, Vector3d wi) {
		return RGBColor.mult(rho(), INV_PI);
	}
	
	@Override
	RGBColor rho() {
		return RGBColor.mult(cd, kd);
	}

	double getKd() {
		return kd;
	}

	RGBColor getCd() {
		return cd;
	}

}
