package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.sampler.MultiJittered;
import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

class GlossySpecular extends BRDF {

	private double ks;
	private RGBColor cs;
	private double exp;

	GlossySpecular(double ks, RGBColor cs, double exp, Sampler sampler) {
		super(sampler, exp);
		this.ks = ks;
		this.cs = cs;
		this.exp = exp;
	}

	@Override
	RGBColor f(ShadeRec sr, Vector3d wo, Vector3d wi) {
		double ndotwi = sr.getNormal().dot(wi);
		Vector3d r = Vectors.plus(Vectors.scale(wi, -1),
				Vectors.scale(Vectors.scale(sr.getNormal(), ndotwi), 2.0));
		double rdotwo = r.dot(wo);

		if (rdotwo > 0.0)
			return RGBColor.mult(RGBColor.mult(cs, ks), Math.pow(rdotwo, exp));
		return super.f(sr, wo, wi);
	}

	@Override
	RGBColor sampleF(ShadeRec sr, Vector3d wo, Vector3d wi) {
		double ndotwo = sr.getNormal().dot(wo);
		Vector3d r = Vectors.plus(Vectors.scale(wo, -1),
				Vectors.scale(Vectors.scale(sr.getNormal(), ndotwo), 2.0));

		Vector3d w = r;
		Vector3d u = new Vector3d(0.00424, 1, 0.00764);
		u.cross(u, w);
		u.normalize();

		Vector3d v = new Vector3d();
		v.cross(u, w);

		Vector3d sp = this.getSampler().sampleHemisphere();
		Vector3d auxWi = Vectors.plus(Vectors.scale(u, sp.x),
				Vectors.plus(Vectors.scale(v, sp.y), Vectors.scale(w, sp.z)));

		wi.x = auxWi.x;
		wi.y = auxWi.y;
		wi.z = auxWi.z;

		if (sr.getNormal().dot(wi) < 0.0)
			wi = Vectors.plus(Vectors.sub(Vectors.scale(u, -sp.x),
					Vectors.scale(v, sp.y)), Vectors.scale(w, sp.z));

		double phong_lobe = Math.pow(r.dot(wi), exp);
		// pdf = phong_lobe * (sr.getNormal() * wi);

		return (RGBColor.mult(RGBColor.mult(cs, ks), phong_lobe));
	}
	
	public void setSamples(int sampleNum, double exp) {
		this.setSampler(new MultiJittered(sampleNum));
		this.getSampler().mapSamplesToHemisphere(exp);
	}
	
}
