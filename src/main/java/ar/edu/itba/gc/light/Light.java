package ar.edu.itba.gc.light;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;

public abstract class Light {

	private boolean shadows;
	private double ls;
	private RGBColor color;

	protected Light(boolean shadows, double ls, RGBColor color) {
		super();
		this.shadows = shadows;
		this.ls = ls;
		this.color = color;
	}

	public boolean castShadows() {
		return shadows;
	}

	public double getLs() {
		return ls;
	}

	public RGBColor getColor() {
		return color;
	}

	public RGBColor L(ShadeRec sr) {
		return RGBColor.mult(getColor(), getLs());
	}

	public abstract Vector3d getDirection(ShadeRec sr);

	public abstract boolean inShadow(Ray ray, ShadeRec sr);
}
