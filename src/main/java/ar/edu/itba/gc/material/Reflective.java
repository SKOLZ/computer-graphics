package ar.edu.itba.gc.material;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.world.World;

public class Reflective extends Phong {

	private PerfectSpecular reflectiveBRDF;

	public Reflective(World world, double ka, double kd, double ks, double exp,
			double kr, RGBColor cd) {
		super(world, ka, kd, ks, exp, cd);
		this.reflectiveBRDF = new PerfectSpecular(kr, cd);
	}

}
