package ar.edu.itba.gc.camera;

import java.awt.image.BufferedImage;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.Vectors;
import ar.edu.itba.gc.world.ViewPlane;
import ar.edu.itba.gc.world.World;

public class PinholeCamera extends Camera {

	private double distance;
	private double zoom;

	public PinholeCamera(Vector3d eye, Vector3d lookAt) {
		this(eye, lookAt, 500.0, 1.0);
	}
	
	public PinholeCamera(Vector3d eye, Vector3d lookAt, double distance) {
		this(eye, lookAt, distance, 1.0);
	}

	public PinholeCamera(Vector3d eye, Vector3d lookAt, double distance,
			double zoom) {
		super(eye, lookAt);
		this.distance = distance;
		this.zoom = zoom;
	}
	
	public PinholeCamera(Vector3d eye, Vector3d lookAt, Vector3d up, double distance,
			double zoom, double exposureTime) {
		super(eye, lookAt, up, exposureTime);
		this.distance = distance;
		this.zoom = zoom;
	}

	public double getDistance() {
		return distance;
	}

	public double getZoom() {
		return zoom;
	}

	@Override
	public BufferedImage renderScene(World w) {
		w.vp.setPixelSize(w.vp.getPixelSize() / zoom);

		BufferedImage img = new BufferedImage(w.vp.getHorizontalRes(),
				w.vp.getVerticalRes(), BufferedImage.TYPE_INT_RGB);
		Ray ray = new Ray();
		ray.setOrigin(getEye());
		for (int r = 0; r < w.vp.getVerticalRes(); r++) {
			for (int c = 0; c < w.vp.getHorizontalRes(); c++) {
				RGBColor l = RGBColor.black();
				for (int i = 0; i < w.vp.getSampleNum(); i++) {
					Vector2d samplePoint = w.vp.getSampler().sampleUnitSquare();
					double x = w.vp.getPixelSize()
							* (c - (w.vp.getHorizontalRes() * 0.5) + samplePoint.x);
					
					double y = w.vp.getPixelSize()
							* (r - (w.vp.getVerticalRes() * 0.5) + samplePoint.y);
					ray.setDirection(getRayDirection(new Vector2d(x, y)));
					
					l.sum(w.tracer.traceRay(ray, 0));
				}
				l.divide(w.vp.getSampleNum());
				l.mult(getExposureTime());
				displayPixel(r, c, l, img, w.vp);
			}
		}

		return img;
	}

	private void displayPixel(int i, int j, final RGBColor pixelColor,
			BufferedImage img, ViewPlane vp) {
		pixelColor.correctColor();
		if (vp.getGamma() != 1.0) {
			pixelColor.applyGamma(vp.getGammaInverse());
		}
		int r = (int) (pixelColor.getR() * 255);
		int g = (int) (pixelColor.getG() * 255);
		int b = (int) (pixelColor.getB() * 255);
		int rgb = (r << 16) | (g << 8) | b;
		img.setRGB(j, vp.getVerticalRes() - i - 1, rgb);
	}

	private Vector3d getRayDirection(Vector2d pixelPoint) {
		Vector3d scaleU = Vectors.scale(getU(), pixelPoint.x);
		Vector3d scaleV = Vectors.scale(getV(), pixelPoint.y);
		Vector3d scaleW = Vectors.scale(getW(), distance);
		Vector3d direction = Vectors.sub(Vectors.plus(scaleU, scaleV), scaleW);
		direction.normalize();
		return direction;
	}

}
