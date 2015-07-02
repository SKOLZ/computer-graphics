package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

public class CookTorrance extends BRDF {

	public CookTorrance(Sampler sampler, double exp) {
		super(sampler, exp);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public RGBColor f(ShadeRec sr, Vector3d wo, Vector3d wi) {
		// TODO Auto-generated method stub
		return super.f(sr, wo, wi);
	}
	
	@Override
	public RGBColor sampleF(ShadeRec sr, Vector3d wo, Vector3d wi) {
		// TODO Auto-generated method stub
		return super.sampleF(sr, wo, wi);
	}
	
	@Override
	public RGBColor pathtracingSampleF(ShadeRec sr, Vector3d wo, Vector3d wi) {
		// TODO Auto-generated method stub
		return super.pathtracingSampleF(sr, wo, wi);
	}
	
	

}
