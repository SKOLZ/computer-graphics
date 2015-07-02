package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public class Reflective extends Phong {

	private PerfectSpecular reflectiveBRDF;

	public Reflective(World world, double ka, double kd, double ks, double exp,
			double kr, Texture cd, Sampler sampler, RGBColor cr) {
		super(world, ka, kd, ks, exp, cd, sampler);
		this.reflectiveBRDF = new PerfectSpecular(kr, cr, sampler);
	}

	@Override
	public RGBColor shade(ShadeRec sr) {
		RGBColor l = super.shade(sr);

		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		Vector3d wi = new Vector3d();
		RGBColor fr = this.reflectiveBRDF.sampleF(sr, wo, wi);
		Ray reflectedRay = new Ray(sr.getHitPoint(), wi, sr.getDepth() + 1);

		l.sum(RGBColor.mult(
				RGBColor.mult(
						fr,
						sr.getWorld().tracer.traceRay(reflectedRay,
								sr.getDepth() + 1)), sr.getNormal().dot(wi)));
		return l;
	}
	
	public RGBColor globalShade(ShadeRec sr) {
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		Vector3d wi = new Vector3d();
		double pdf;
		RGBColor fr = reflectiveBRDF.pathtracingSampleF(sr, wo, wi);
		Ray reflectedRay = new Ray(sr.getHitPoint(), wi);
		if (sr.getDepth() == 0) {
			return RGBColor.mult(RGBColor.mult(fr, sr.getWorld().tracer.traceRay(reflectedRay, sr.getDepth() + 2)), (sr.getNormal().dot(wi)) / sr.getPdf()); //quilombo
		} else {
			return RGBColor.mult(RGBColor.mult(fr, sr.getWorld().tracer.traceRay(reflectedRay, sr.getDepth() + 1)), (sr.getNormal().dot(wi)) / sr.getPdf()); //quilombo
		}
	}

}
