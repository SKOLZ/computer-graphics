package ar.edu.itba.gc.light;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

public abstract class Light {
	
	private boolean shadows;
	private double ls;
	private RGBColor color;
	
	protected Light(double ls, RGBColor color) {
		super();
		this.ls = ls;
		this.color = color;
	}

	public boolean isShadows() {
		return shadows;
	}

	public double getLs() {
		return ls;
	}

	public RGBColor getColor() {
		return color;
	}
	
	public RGBColor L() {
		return RGBColor.mult(getColor(), getLs());
	}

	public abstract Vector3d getDirection(ShadeRec sr);
}
