package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public class Transparent extends Phong {

	private PerfectSpecular reflectiveBRDF;
	private PerfectTransmitter specularBTDF;

	public Transparent(World world, 
			double ka, 
			double kd, 
			double ks,
			double exp, 
			Texture cd, 
			Sampler sampler, 
			double ior, 
			double kr, 
			double kt, 
			RGBColor cr) {
		super(world, ka, kd, ks, exp, cd, sampler);
		this.reflectiveBRDF = new PerfectSpecular(kr, cr, sampler);
		this.specularBTDF = new PerfectTransmitter(ior, kt);
		
	}

	@Override
	public RGBColor shade(ShadeRec sr) {
		RGBColor l = super.shade(sr);
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		Vector3d wi = new Vector3d();
		RGBColor fr = reflectiveBRDF.sampleF(sr, wo, wi);
		Ray reflectedRay = new Ray(sr.getHitPoint(), wi);
		if (specularBTDF.tir(sr)) {
			l.sum(sr.getWorld().tracer.traceRay(reflectedRay, sr.getDepth() + 1));
		} else {
			Vector3d wt = new Vector3d();
			RGBColor ft = specularBTDF.sampleF(sr, wo, wt);
			Ray transmittedRay = new Ray(sr.getHitPoint(), wt);
			l.sum(RGBColor.mult(
					RGBColor.mult(
							fr,
							sr.getWorld().tracer.traceRay(reflectedRay,
									sr.getDepth() + 1)),
					Math.abs(sr.getNormal().dot(wi))));
			l.sum(RGBColor.mult(
					RGBColor.mult(
							ft,
							sr.getWorld().tracer.traceRay(transmittedRay,
									sr.getDepth() + 1)),
					Math.abs(sr.getNormal().dot(wt))));
		}
		return l;
	}
}
