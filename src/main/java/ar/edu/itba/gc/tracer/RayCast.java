package ar.edu.itba.gc.tracer;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.world.World;

public class RayCast extends Tracer {

	public RayCast(World world) {
		super(world);
	}

	@Override
	public RGBColor traceRay(Ray ray, int depth) {
		ShadeRec sr = ray.hit(this.getWorld());
		
		if (sr.hitsAnObject()) {
			return sr.getMaterial().shade(sr);
		} else {
			return this.getWorld().background;
		}
	}

}
