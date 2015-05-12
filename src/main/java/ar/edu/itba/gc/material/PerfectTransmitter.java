package ar.edu.itba.gc.material;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class PerfectTransmitter extends BTDF {

	private double ior;
	private double kt;
	
	
	
	public PerfectTransmitter(double ior, double kt) {
		super();
		this.ior = ior;
		this.kt = kt;
	}

	@Override
	public RGBColor sampleF(ShadeRec sr, Vector3d wo, Vector3d wt) {
		Vector3d n = new Vector3d(sr.getNormal());
		double cosThetai = n.dot(wo);
		double eta = ior;
		
		if (cosThetai < 0) {
			cosThetai *= -1;
			n.scale(-1);
			eta = 1 / eta;
		}
		
		double temp = 1.0 - (1.0 - cosThetai * cosThetai) / (eta * eta);
		double cosTheta2 = Math.sqrt(temp);
		Vector3d auxWt = Vectors.sub(Vectors.scale(Vectors.scale(wo, -1), 1 / eta), Vectors.scale(n, cosTheta2 - cosThetai / eta)); 
		wt.x = auxWt.x;
		wt.y = auxWt.y;
		wt.z = auxWt.z;
		
		RGBColor resultColor = RGBColor.white();
		resultColor.mult(kt / (eta * eta));
		resultColor.divide(Math.abs(sr.getNormal().dot(wt)));
		return resultColor;
	}

	@Override
	public boolean tir(ShadeRec sr) {
		Vector3d wo = Vectors.scale(sr.getDirection(), -1);
		double cosThetai = wo.dot(sr.getNormal());
		double eta = ior;
		
		if (cosThetai < 0) {
			eta = 1 / eta;
		}
		
		return (1.0 - ((1.0 - cosThetai * cosThetai) / (eta * eta))) < 0;
	}

}
