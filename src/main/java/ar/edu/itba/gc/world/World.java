package ar.edu.itba.gc.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import ar.edu.itba.gc.camera.Camera;
import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.tracer.Tracer;
import ar.edu.itba.gc.tracer.WhittedTracer;
import ar.edu.itba.gc.util.KDNode;
import ar.edu.itba.gc.util.RGBColor;

public class World {

	private static World instance;

	public static World getInstance() {
		if (instance == null)
			instance = new World();
		return instance;
	}

	public static final double PI = 3.1415926535897932384;
	public static final double INV_PI = 0.3183098861837906715;
	public static final double TWO_PI = 6.2831853071795864769;
	public static final double INV_TWO_PI = 0.1591549430918953358;

	public ViewPlane vp;
	public RGBColor background;
	public List<GeometricObject> objects;
	public KDNode<GeometricObject> tree;
	public List<Light> lights;
	public Light ambientLight;
	public Tracer tracer;
	public Camera camera;

	private World() {
		this.vp = new ViewPlane();
		this.objects = new LinkedList<GeometricObject>();
		this.lights = new LinkedList<Light>();
		this.tracer = new WhittedTracer(this);
	}

	public void addObject(GeometricObject obj) {
		this.objects.add(obj);
	}

	public void addLight(Light light) {
		this.lights.add(light);
	}

	public void renderScene(String outputPath) {
//		tree = KDNode.build(this.objects);
		saveImage(camera.renderScene(this), outputPath);
	}

	public void saveImage(BufferedImage img, String fileName) {
		File f = new File(fileName);
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<GeometricObject> getObjects() {
		return this.objects;
	}

}
