package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class Rectangle extends EmisiveObject {


	private Vector3d p0;
	private Vector3d a;
	private Vector3d b;
	private Vector3d normal;
	private Sampler sampler;
	private double invArea;

	public Rectangle(Material material, Vector3d p0, Vector3d a, Vector3d b, Vector3d normal, Sampler sampler) {
		super(material);
		this.p0 = p0;
		this.a = a;
		this.b = b;
		this.normal = normal;
		this.sampler = sampler;
		this.invArea = 1 / (a.length() * b.length());
	}
	
	public Vector3d sample() {
		Vector2d samplePoint = sampler.sampleUnitSquare();
		return Vectors.plus(
				Vectors.plus(p0, Vectors.scale(a, samplePoint.x)), 
				Vectors.scale(b, samplePoint.y));
	}
	
	public double pdf(ShadeRec sr) {
		return invArea;
	}
	
	public Vector3d getNormal(Vector3d p) {
		return normal;
	}
	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin,
			Vector3d direction) {
		double t = Vectors.sub(p0, origin).dot(normal) / direction.dot(normal);
		if (t < Constants.KEPS) {
			return -1;
		}
		Vector3d p = Vectors.plus(origin, Vectors.scale(direction, t));
		Vector3d d = Vectors.sub(p, p0);
		double ddota = d.dot(a);
		if (ddota < 0 || ddota > a.lengthSquared()) {
			return -1;
		}
		double ddotb = d.dot(b);
		if (ddotb < 0 || ddotb > b.lengthSquared()) {
			return -1;			
		}
		tmin = t;
		sr.setNormal(normal);
		sr.setLocalHitPoint(p);
		sr.setHitPoint(p);
		sr.setHitsAnObject(true);
		return tmin;
	}

	@Override
	public double shadowHit(Ray ray) {
		double t = Vectors.sub(p0, ray.getOrigin()).dot(normal) / ray.getDirection().dot(normal);
		if (t < Constants.KEPS) {
			return -1;
		}
		Vector3d p = Vectors.plus(ray.getOrigin(), Vectors.scale(ray.getDirection(), t));
		Vector3d d = Vectors.sub(p, p0);
		double ddota = d.dot(a);
		if (ddota < 0 || ddota > a.lengthSquared()) {
			return -1;
		}
		double ddotb = d.dot(b);
		if (ddotb < 0 || ddotb > b.lengthSquared()) {
			return -1;			
		}
		return t;
	}

	@Override
	public BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3d getCentroid() {
		// TODO Auto-generated method stub
		return null;
	}

}
