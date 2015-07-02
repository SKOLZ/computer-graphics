package ar.edu.itba.gc.material;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.world.World;

public abstract class Material {
	
	private World world;

	public Material(World world) {
		super();
		this.world = world;
	}

	public RGBColor shade(ShadeRec sr) {
		return RGBColor.black();
	}
	
	public RGBColor globalShade(ShadeRec sr) {
		return RGBColor.black();
	}
	
	protected World getWorld() {
		return this.world;
	}
	
}
