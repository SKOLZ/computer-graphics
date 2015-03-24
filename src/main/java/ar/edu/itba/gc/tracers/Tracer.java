package ar.edu.itba.gc.tracers;

import ar.edu.itba.gc.primitives.World;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Ray;

public abstract class Tracer {

	private World world;

	public Tracer(World world) {
		super();
		this.world = world;
	}

	public abstract RGBColor traceRay(Ray ray);

	public World getWorld() {
		return world;
	}
	
	
}
