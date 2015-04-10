package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

class GlossySpecular extends BRDF {

	private double ks;
	private RGBColor cs;
	private double exp;

	GlossySpecular(double ks, RGBColor cs, double exp) {
		super();
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
}
