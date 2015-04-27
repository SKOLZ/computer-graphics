package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

abstract class BRDF {
	
	private Sampler sampler;
	
	public BRDF(Sampler sampler, double exp) {
		this.setSampler(sampler, exp);
	}

	RGBColor f(ShadeRec sr, Vector3d wo, Vector3d wi) {
		return RGBColor.black();
	}
	
	RGBColor rho() {
		return RGBColor.black();
	}
	
	RGBColor sampleF(ShadeRec sr, Vector3d wo, Vector3d wi) {
		return RGBColor.black();
	}
	
	void setSampler(Sampler sampler, double exp) {
		this.sampler = sampler;
		this.sampler.mapSamplesToHemisphere(exp);
	}
	
	Sampler getSampler() {
		return this.sampler;
	}
	
}
