package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.sampler.Sampler;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

class Lambertian extends BRDF {

	private double kd;
	private Texture cd;

	Lambertian(double kd, Texture cd, Sampler sampler) {
		super(sampler, 1);
		this.kd = kd;
		this.cd = cd;
	}

	double getKd() {
		return kd;
	}

	Texture getCd() {
		return cd;
	}
	
	@Override
	RGBColor f(ShadeRec sr, Vector3d wo, Vector3d wi) {
		return RGBColor.mult(rho(sr), World.INV_PI);
	}
	
	@Override
	RGBColor rho(ShadeRec sr) {
		return RGBColor.mult(cd.getColor(sr), kd);
	}
	
	@Override
	RGBColor sampleF(ShadeRec sr, Vector3d wo, Vector3d wi) {
		Vector3d w = sr.getNormal();
		Vector3d v = new Vector3d(0.0034, 1, 0.0071);
		v.cross(v, w);
		v.normalize();
		Vector3d u = new Vector3d();
		u.cross(v, w);
		
		Vector3d sp = this.getSampler().sampleHemisphere();
		Vector3d auxWi = Vectors.plus(
				Vectors.plus(Vectors.scale(u, sp.x), Vectors.scale(v, sp.y)),
				Vectors.scale(w, sp.z));
		wi.normalize();
		
		wi.x = auxWi.x;
		wi.y = auxWi.y;
		wi.z = auxWi.z;
		
//		pdf = sr.getNormal().dot(wi) * World.INV_PI; TODO revisar si este metodo deberia ir o llamar solo al de la superclase
		
		return RGBColor.mult(RGBColor.mult(cd.getColor(sr), kd), World.INV_PI); 
	}
	
	@Override
	RGBColor pathtracingSampleF(ShadeRec sr, Vector3d wo, Vector3d wi) {
		Vector3d w = sr.getNormal();
		Vector3d v = new Vector3d(0.0034, 1, 0.0071);
		v.cross(v, w);
		v.normalize();
		Vector3d u = new Vector3d();
		u.cross(v, w);
		
		Vector3d sp = this.getSampler().sampleHemisphere();
		Vector3d auxWi = Vectors.plus(
				Vectors.plus(Vectors.scale(u, sp.x), Vectors.scale(v, sp.y)),
				Vectors.scale(w, sp.z));
		wi.normalize();
		
		wi.x = auxWi.x;
		wi.y = auxWi.y;
		wi.z = auxWi.z;
		
		sr.setPdf(sr.getNormal().dot(wi) * World.INV_PI);
		
		return RGBColor.mult(RGBColor.mult(cd.getColor(sr), kd), World.INV_PI); 
	}

}
