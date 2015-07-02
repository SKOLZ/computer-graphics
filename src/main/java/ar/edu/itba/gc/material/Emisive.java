package ar.edu.itba.gc.material;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.World;

public class Emisive extends Material {

	private double ls;
	private RGBColor ce;
	
	public Emisive(World world) {
		super(world);
	}
	
	public double getLs() {
		return ls;
	}

	public void setLs(double ls) {
		this.ls = ls;
	}

	public RGBColor getCe() {
		return ce;
	}

	public void setCe(RGBColor ce) {
		this.ce = ce;
	}

	public RGBColor getLe(ShadeRec sr) {
		return RGBColor.mult(ce, ls);
	}
	
	public RGBColor globalShade(ShadeRec sr) {
		if (sr.getDepth() == 1) {
			return RGBColor.black();
		}
		if (Vectors.scale(sr.getNormal(), -1).dot(sr.getDirection()) > 0.0) {
			return RGBColor.mult(ce, ls);
		} else {
			return RGBColor.black();
		}
	}

}
