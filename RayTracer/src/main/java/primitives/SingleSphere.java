package primitives;

public class SingleSphere extends Tracer {
	
	public SingleSphere(World world) {
		super(world);
		
	}
	
	@Override
	public RGBColor traceRay(Ray ray) {
		if(getWorld().sphere.hit(ray, Double.MAX_VALUE)) {
			return new RGBColor(1.0, 0.0, 0.0);
		} else {
			return new RGBColor(0.0, 0.0, 0.0);
		}
	}

}
