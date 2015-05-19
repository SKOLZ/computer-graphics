package ar.edu.itba.gc.texture;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

public class ConstantColor extends Texture {

	private RGBColor color;
	
	public ConstantColor(RGBColor color) {
		super();
		this.color = color;
	}

	public void setColor(RGBColor color) {
		this.color = color;
	}

	@Override
	public RGBColor getColor(ShadeRec sr) {
		return color;
	}

}
