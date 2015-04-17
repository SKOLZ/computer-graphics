package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public class Matte extends Material {

	private Lambertian ambientBRDF;
	private Lambertian diffuseBRDF;

	public Matte(World world, double ka, double kd, RGBColor cd) {
		super(world);
		this.ambientBRDF = new Lambertian(ka, cd);
		this.diffuseBRDF = new Lambertian(kd, cd);
	}

	@Override
	public RGBColor shade(ShadeRec sr) {
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		RGBColor ret = RGBColor.mult(ambientBRDF.rho(),
				getWorld().ambientLight.L());

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
							RGBColor.mult(
									RGBColor.mult(diffuseBRDF.f(sr, wo, wi),
											l.L()), ndotwi));
				}
			}
		}

		return ret;
	}

	protected Lambertian getAmbientBRDF() {
		return ambientBRDF;
	}

	protected Lambertian getDiffuseBRDF() {
		return diffuseBRDF;
	}
}
