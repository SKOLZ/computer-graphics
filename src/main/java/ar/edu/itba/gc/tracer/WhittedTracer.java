package ar.edu.itba.gc.tracer;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.world.World;

public class WhittedTracer extends Tracer {

	public WhittedTracer(World world) {
		super(world);
	}
	
	@Override
	public RGBColor traceRay(Ray ray, int depth) {
		if (depth > this.getWorld().vp.getMaxDepth()) {
			return RGBColor.black();
		} else {
			ShadeRec sr = ray.hit(this.getWorld());
			if (sr.hitsAnObject()) {
				sr.setDepth(depth);
				sr.setDirection(ray.getDirection());
				return sr.getMaterial().shade(sr);
			}
			return this.getWorld().background;
		}
	}

}
