package ar.edu.itba.gc.tracer;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.world.World;

public class PathTracer extends Tracer {

	public PathTracer(World world) {
		super(world);
	}

	@Override
	public RGBColor traceRay(Ray ray, int depth) {
		if (depth > getWorld().vp.getMaxDepth()) {
			return RGBColor.black();
		} else {
			ShadeRec sr = ray.hit(getWorld());
			if(sr.hitsAnObject()) {
				sr.setDepth(depth);
				sr.setDirection(ray.getDirection());
				return sr.getMaterial().globalShade(sr);
			} else {
				return getWorld().background;
			}
		}
	}
}
