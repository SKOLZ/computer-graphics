package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public class Phong extends Matte {

	private GlossySpecular specularBRDF;

	public Phong(World world, double ka, double kd, double ks, RGBColor cd) {
		this(world, ka, kd, ks, 1, cd);
	}

	public Phong(World world, double ka, double kd, double ks, double exp,
			RGBColor cd) {
		super(world, ka, kd, cd);
		this.specularBRDF = new GlossySpecular(ks, cd, exp);
	}

	@Override
	public RGBColor shade(ShadeRec sr) {
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		RGBColor ret = RGBColor.mult(getAmbientBRDF().rho(),
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
					RGBColor brdfColor = RGBColor.sum(
							getDiffuseBRDF().f(sr, wo, wi),
							this.specularBRDF.f(sr, wo, wi));
					ret = RGBColor.sum(ret, RGBColor.mult(
							RGBColor.mult(brdfColor, l.L()), ndotwi));
				}
			}
		}

		return ret;
	}

}
