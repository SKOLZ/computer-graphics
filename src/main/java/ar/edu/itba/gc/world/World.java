package ar.edu.itba.gc.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.camera.Camera;
import ar.edu.itba.gc.camera.PinholeCamera;
import ar.edu.itba.gc.light.DirectionalLight;
import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.light.PointLight;
import ar.edu.itba.gc.material.Matte;
import ar.edu.itba.gc.material.Phong;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.primitives.Sphere;
import ar.edu.itba.gc.tracer.MultipleObjectTracer;
import ar.edu.itba.gc.tracer.Tracer;
import ar.edu.itba.gc.util.RGBColor;

public class World {

	public ViewPlane vp;
	public RGBColor background;
	public List<GeometricObject> objects;
	public List<Light> lights;
	public Light ambientLight;
	public Tracer tracer;
	public Camera camera;

	public World() {
		this.vp = new ViewPlane();
		this.objects = new LinkedList<GeometricObject>();
		this.lights = new LinkedList<Light>();
		this.tracer = new MultipleObjectTracer(this);
	}

	public void addObject(GeometricObject obj) {
		this.objects.add(obj);
	}

	public void addLight(Light light) {
		this.lights.add(light);
	}

	public void build() {
		camera = new PinholeCamera(new Vector3d(0, 0, 1500), new Vector3d(0, 0,
				0), 900.0, 5);
		camera.computeUVW();

		vp.setHorizontalRes(3000);
		vp.setVerticalRes(3000);
		vp.setPixelSize(1.0);
		vp.setGamma(1.0);

		background = RGBColor.black();
		ambientLight = DirectionalLight.upWhite();

		// this.addLight(DirectionalLight.downWhite());
		this.addLight(new PointLight(3, new Vector3d(0, 0, 1300)));

		this.addObject(new Sphere(new Phong(this, 0.25, 0.75, 1, 100, new RGBColor(1,
				0, 0)), new Vector3d(-125.0, 0.0, 600.0), 100.0));
		this.addObject(new Sphere(new Matte(this, 0.25, 0.75, new RGBColor(1,
				0, 0)), new Vector3d(125.0, 0.0, 600.0), 100.0));
//		this.addObject(new Sphere(new Matte(this, 0.25, 0.75, new RGBColor(0,
//				0, 1)), new Vector3d(150.0, 0.0, 100.0), 100.0));
	}

	public void renderScene() {
		BufferedImage img = camera.renderScene(this);
		File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}