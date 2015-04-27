package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

class PerfectSpecular extends BRDF {

	private double kr;
	private RGBColor cr;

	PerfectSpecular(double kr, RGBColor cr, Sampler sampler) {
		super(sampler, 1);
		this.kr = kr;
		this.cr = cr;
	}

	double getKr() {
		return kr;
	}

	RGBColor getCr() {
		return cr;
	}

	@Override
	RGBColor sampleF(ShadeRec sr, Vector3d wo, Vector3d wi) {
		double ndotwo = sr.getNormal().dot(wo);
		Vector3d auxWi = Vectors.plus(Vectors.scale(wo, -1),
				Vectors.scale(Vectors.scale(sr.getNormal(), ndotwo), 2.0));
		wi.x = auxWi.x;
		wi.y = auxWi.y;
		wi.z = auxWi.z;
		return (RGBColor.mult(cr, kr / Math.abs(sr.getNormal().dot(wi))));
	}

}
