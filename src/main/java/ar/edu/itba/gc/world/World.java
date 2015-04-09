package ar.edu.itba.gc.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.lights.AmbientLight;
import ar.edu.itba.gc.lights.Light;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.primitives.Plane;
import ar.edu.itba.gc.primitives.Sphere;
import ar.edu.itba.gc.tracers.MultipleObjectTracer;
import ar.edu.itba.gc.tracers.Tracer;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Ray;
import ar.edu.itba.gc.utils.ShadeRec;

public class World {

	public ViewPlane vp;
	public RGBColor background;
	public List<GeometricObject> objects;
	public List<Light> lights;
	public Light ambientLight;
	public Tracer tracer;

	public World() {
		this.vp = new ViewPlane();
		this.objects = new ArrayList<GeometricObject>();
		this.lights = new ArrayList<Light>();
		this.ambientLight = new AmbientLight();
		this.tracer = new MultipleObjectTracer(this);
	}

	public void addObject(GeometricObject obj) {
		this.objects.add(obj);
	}

	public void addLigt(Light light) {
		this.lights.add(light);
	}
	
	public void build() {
		vp.setHorizontalRes(3000);
		vp.setVerticalRes(3000);
		vp.setPixelSize(1.0);
		vp.setGamma(1.0);

		this.background = new RGBColor(0, 0, 0);

		this.addObject(new Sphere(null, new RGBColor(1, 1, 1), new Vector3d(
				0.0, 0.0, 800.0), 2000.0));
		// this.addObject(new Sphere(null, new RGBColor(1, 0, 0), new Vector3d(
		// 0.0, 0.0, 0.0, 0.0), 85.0));

		this.addObject(new Plane(null, new RGBColor(1, 0, 1), new Vector3d(0,
				0, 800), new Vector3d(1, 0, 1)));
		this.addObject(new Plane(null, new RGBColor(0, 0, 1), new Vector3d(0,
				0, 800), new Vector3d(-1, 0, 1)));
		this.addObject(new Plane(null, new RGBColor(0, 1, 0), new Vector3d(0.0,
				0, 800), new Vector3d(0, 1, 1)));
		this.addObject(new Plane(null, new RGBColor(1, 0, 0), new Vector3d(0.0,
				0, 800), new Vector3d(0, -1, 1)));
	}

	public void renderScene() {
		RGBColor pixelColor;
		double z = 4000000.0;

		BufferedImage img = new BufferedImage(vp.getHorizontalRes(),
				vp.getVerticalRes(), BufferedImage.TYPE_INT_RGB);
		for (int r = 0; r < vp.getVerticalRes(); r++) {
			for (int c = 0; c < vp.getHorizontalRes(); c++) {
				double x = vp.getPixelSize()
						* (c - (vp.getHorizontalRes() / 2.0 + 0.5));
				double y = vp.getPixelSize()
						* (r - (vp.getVerticalRes() / 2.0 + 0.5));
				Ray ray = new Ray(new Vector3d(x, y, z), new Vector3d(0.0,
						0.0, -1.0));
				ShadeRec sr = ray.hit(objects, this.background);
				pixelColor = sr.getColor();
				displayPixel(r, c, pixelColor, img);
			}
		}
		File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void displayPixel(int i, int j, final RGBColor pixelColor,
			BufferedImage img) {
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

}
