package ar.edu.itba.gc.texture;

import javax.vecmath.Point2d;

import ar.edu.itba.gc.mapping.Mapping;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

public class ChessTexture extends Texture {

	private Mapping mapping;

	
	public ChessTexture(Mapping mapping) {
		super();
		this.mapping = mapping;
	}


	@Override
	public RGBColor getColor(ShadeRec sr) {
		Point2d pos = mapping.getTexelCoordinates(sr.getLocalHitPoint(), 100, 100);
		int r = (int)pos.y;
		int c = (int)pos.x;		
		if(r % 20 < 10) {
			if(c % 20 < 10) {
				return RGBColor.black();
			} else {
				return RGBColor.white();
			}
		} else {
			if(c % 20 < 10) {
				return RGBColor.white();
			} else {
				return RGBColor.black();
			}
		}
	}

}
