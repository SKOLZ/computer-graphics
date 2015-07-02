package ar.edu.itba.gc.parser.world.light;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.material.Emisive;
import ar.edu.itba.gc.primitives.EmisiveObject;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class AreaLight extends Light {

	private EmisiveObject object;
	private Emisive material;
	private Vector3d samplePoint;
	private Vector3d lightNormal;
	private Vector3d wi;

	protected AreaLight(boolean shadows, double ls, RGBColor color,
			EmisiveObject object, Emisive material, Vector3d samplePoint,
			Vector3d lightNormal, Vector3d wi) {
		super(shadows, ls, color);
		this.object = object;
		this.material = material;
		this.samplePoint = samplePoint;
		this.lightNormal = lightNormal;
		this.wi = wi;
	}

	@Override
	public Vector3d getDirection(ShadeRec sr) {
		samplePoint = object.sample();
		lightNormal = object.getNormal(samplePoint);
		wi = Vectors.sub(samplePoint, sr.getHitPoint());
		wi.normalize();
		return wi;
	}

	@Override
	public boolean inShadow(Ray ray, ShadeRec sr) {
		int numObjects = sr.getWorld().objects.size();
		double ts = Vectors.sub(samplePoint, ray.getOrigin()).dot(
				ray.getDirection());
		double t;
		for (int j = 0; j < numObjects; j++) {
			t = sr.getWorld().getObjects().get(j).shadowHit(ray);
			if (t != -1 && t < ts) {
				return true;
			}
		}
		return false;
	}

	@Override
	public RGBColor L(ShadeRec sr) {
		double ndotd = Vectors.scale(lightNormal, -1).dot(wi);
		if (ndotd > 0) {
			return material.getLe(sr);
		} else {
			return RGBColor.black();
		}
	}
	
	public double G(ShadeRec sr) {
		double ndotd = Vectors.scale(lightNormal, -1).dot(wi);
		Point3d p1 = new Point3d(samplePoint);
		Point3d p2 = new Point3d(sr.getHitPoint());
		double d2 = p1.distanceSquared(p2);
		return (ndotd / d2);
	}
	
	public double pdf(ShadeRec sr) {
		return object.pdf(sr);
	}
}
