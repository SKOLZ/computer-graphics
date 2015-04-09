package ar.edu.itba.gc.cameras;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Ray;
import ar.edu.itba.gc.utils.ShadeRec;
import ar.edu.itba.gc.utils.Vectors;
import ar.edu.itba.gc.world.ViewPlane;
import ar.edu.itba.gc.world.World;

public class PinholeCamera extends Camera {
	
	private double distance;
	private double zoom;
	
	
	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	public double getZoom() {
		return zoom;
	}


	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	@Override
	public void renderScene(World w) {
		RGBColor l;
		int n = (int)Math.sqrt(w.vp.getSampleNum());
		w.vp.setPixelSize(w.vp.getPixelSize()/zoom);
		for (int r = 0; r < w.vp.getVerticalRes(); r++) {
			for (int c = 0; c < w.vp.getHorizontalRes(); c++) {
				double x = w.vp.getPixelSize()
						* (c - (w.vp.getHorizontalRes() / 2.0 + 0.5));
				double y = w.vp.getPixelSize()
						* (r - (w.vp.getVerticalRes() / 2.0 + 0.5));
				Ray ray = new Ray(getEye(), getRayDirection(new Vector2d(x, y)));
				ShadeRec sr = ray.hit(w.objects, w.background);
				
				pixelColor = sr.getColor();
			}
		}
	}
	
	private Vector3d getRayDirection(Vector2d pixelPoint) {
		Vector3d scaleU = Vectors.scale(getU(), pixelPoint.x);
		Vector3d scaleV = Vectors.scale(getV(), pixelPoint.y);
		Vector3d scaleW = Vectors.scale(getW(), distance);
		Vector3d direction = Vectors.sub(Vectors.plus(scaleU,scaleV), scaleW);
		direction.normalize();
		return direction;
	}
	
}
