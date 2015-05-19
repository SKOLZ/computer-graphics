package ar.edu.itba.gc.texture;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

public abstract class Texture {

	public abstract RGBColor getColor(ShadeRec sr);
	
}
