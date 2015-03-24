package primitives;

import javax.vecmath.Vector4d;

public class World {

	public ViewPlane vp;
	public RGBColor background;
	public Sphere sphere;
	public Tracer tracer;
	
	public void build() {
		vp.setHorizontalRes(200);
		vp.setVerticalRes(200);
		vp.setPixelSize(1.0);
		vp.setGamma(1.0);
		background = new RGBColor(0, 0, 0);
		tracer = new SingleSphere(this);
		
		sphere.setCenter(new Vector4d(0.0, 0.0, 0.0, 0.0));
		sphere.setRadius(85.0);
		
	}

	public void renderScene() {
		RGBColor pixelColor;
		Ray ray = new Ray();
		double z = 100.0;
		
		ray.setDirection(new Vector4d(0.0, 0.0, -1.0, 0.0));
		//TODO write image!
		for (int i = 0; i < vp.getVerticalRes(); i++) {
			for (int j = 0; j < vp.getHorizontalRes(); j++) {
				double x = vp.getPixelSize() * (j - 0.5 * (vp.getHorizontalRes() - 1.0));				
				double y = vp.getPixelSize() * (i - 0.5 * (vp.getVerticalRes() - 1.0));
				ray.setOrigin(new Vector4d(x, y, z, 0.0));
				pixelColor = tracer.traceRay(ray);
				displayPixel(i,j,pixelColor);
			}
			
		}
		
	}

	private void displayPixel(int i, int j, RGBColor pixelColor) {
		if (vp.getGamma() != 1.0) {
			pixelColor = pixelColor.applyGamma(vp.getGammaInverse());
		}
		
	}
	
}
