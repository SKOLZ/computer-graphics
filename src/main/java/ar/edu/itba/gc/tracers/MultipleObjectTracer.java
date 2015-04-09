package ar.edu.itba.gc.tracers;

import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Ray;
import ar.edu.itba.gc.world.World;

public class MultipleObjectTracer extends Tracer {

	public MultipleObjectTracer(World world) {
		super(world);
	}

	@Override
	public RGBColor traceRay(Ray ray) {
		return this.getWorld().background;
	}

}
