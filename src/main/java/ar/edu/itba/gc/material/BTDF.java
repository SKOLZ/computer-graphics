package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;


public abstract class BTDF {
	
	public abstract RGBColor sampleF(ShadeRec sr, Vector3d wo, Vector3d wt);

	public abstract boolean tir(ShadeRec sr);

	RGBColor f(ShadeRec sr, Vector3d wo, Vector3d wt) {
		return RGBColor.black();
	}
	
	public RGBColor rho() {
		return RGBColor.black();
	}
	
}
