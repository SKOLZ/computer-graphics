package ar.edu.itba.gc.texture;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.vecmath.Point2d;

import ar.edu.itba.gc.mapping.Mapping;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;

public class ImageTexture extends Texture {
	
	private int hres;
	private int vres;
	private BufferedImage image;
	private Mapping mapping;

	public ImageTexture(int hres, int vres, BufferedImage image, Mapping mapping) {
		super();
		this.hres = hres;
		this.vres = vres;
		this.image = image;
		this.mapping = mapping;
	}

	@Override
	public RGBColor getColor(ShadeRec sr) {
		int r = 0;
		int c = 0;
		
		if (mapping != null) {
			Point2d pos = mapping.getTexelCoordinates(sr.getLocalHitPoint(), hres, vres);
			r = (int)pos.y;
			c = (int)pos.x;
		} else {
			r = (int)(sr.getV() * (vres -1));
			c = (int)(sr.getU() * (hres -1));
		}
		Color color = new Color(image.getRGB(c, r));
		return new RGBColor(color.getRed(), color.getGreen(), color.getBlue());
	}

}
