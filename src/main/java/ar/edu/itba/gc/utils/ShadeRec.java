package ar.edu.itba.gc.utils;

public class ShadeRec {

	private boolean hitAnObject;
	private RGBColor color;
	
	public ShadeRec(RGBColor color) {
		super();
		this.color = color;
		this.hitAnObject = false;
	}

	public ShadeRec(boolean hitAnObject, RGBColor color) {
		this(color);
		this.hitAnObject = hitAnObject;
	}

	public boolean hitsAnObject() {
		return hitAnObject;
	}

	public RGBColor getColor() {
		return color;
	}

}
