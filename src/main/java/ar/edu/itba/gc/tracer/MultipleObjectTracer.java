package ar.edu.itba.gc.tracer;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
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
