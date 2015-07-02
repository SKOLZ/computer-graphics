package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.parser.world.light.AreaLight;
import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public class Matte extends Material {

	private Lambertian ambientBRDF;
	private Lambertian diffuseBRDF;

	public Matte(World world, double ka, double kd, Texture cd, Sampler sampler) {
		super(world);
		this.ambientBRDF = new Lambertian(ka, cd, sampler);
		this.diffuseBRDF = new Lambertian(kd, cd, sampler);
	}

	@Override
	public RGBColor shade(ShadeRec sr) {
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		RGBColor ret = RGBColor.mult(ambientBRDF.rho(sr),
				getWorld().ambientLight.L(sr));

		for (Light l : getWorld().lights) {
			Vector3d wi = l.getDirection(sr);
			double ndotwi = sr.getNormal().dot(wi);

			if (ndotwi > 0.0) {
				boolean inShadows = false;
				if (l.castShadows()) {
					inShadows = l.inShadow(new Ray(sr.getHitPoint(), wi), sr);
				}
				if (!inShadows) {
					ret = RGBColor.sum(
							ret,
							RGBColor.mult(diffuseBRDF.f(sr, wo, wi),
									RGBColor.mult(l.L(sr), ndotwi)));
				}
			}
		}

		return ret;
	}
	
	public RGBColor globalShade(ShadeRec sr) {
		RGBColor l = RGBColor.black();
		if(sr.getDepth() == 0) {
			l = areaLightShade(sr);
		}
		Vector3d wi = new Vector3d();
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		double pdf;
		RGBColor f = getDiffuseBRDF().pathtracingSampleF(sr, wo, wi);
		double ndotwi = sr.getNormal().dot(wi);
		Ray reflectedRay = new Ray(sr.getHitPoint(), wi);
		l = RGBColor.sum(l, RGBColor.mult(RGBColor.mult(f, sr.getWorld().tracer.traceRay(reflectedRay, sr.getDepth() + 1)), ndotwi / sr.getPdf())); // TODO ver que onda el ultimo parametro del trace rayquilombo
		return l;
	}
	
	public RGBColor areaLightShade(ShadeRec sr) {
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		RGBColor l = RGBColor.mult(ambientBRDF.rho(sr), sr.getWorld().ambientLight.L(sr));
		int numLights = sr.getWorld().lights.size();
		for (int j = 0 ; j < numLights ; j++) {
			Vector3d wi = sr.getWorld().lights.get(j).getDirection(sr);
			double ndotwi = sr.getNormal().dot(wi);
			if (ndotwi > 0.0) {
				boolean inShadow = false;
				if (sr.getWorld().lights.get(j).castShadows()) {
					Ray shadowRay = new Ray(sr.getHitPoint(), wi);
					inShadow = sr.getWorld().lights.get(j).inShadow(shadowRay, sr);
				}
				if (!inShadow) {
					l = RGBColor.sum(l, RGBColor.mult(diffuseBRDF.f(sr, wo, wi), sr.getWorld().lights.get(j).L(sr)));
				}
			}
		}
		return l;
	}
	
	protected Lambertian getAmbientBRDF() {
		return ambientBRDF;
	}

	protected Lambertian getDiffuseBRDF() {
		return diffuseBRDF;
	}
}
