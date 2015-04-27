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
import ar.edu.itba.gc.light.AmbientLight;
import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.light.PointLight;
import ar.edu.itba.gc.material.Matte;
import ar.edu.itba.gc.material.Phong;
import ar.edu.itba.gc.material.Reflective;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.primitives.Plane;
import ar.edu.itba.gc.primitives.Sphere;
import ar.edu.itba.gc.tracer.Tracer;
import ar.edu.itba.gc.tracer.WhittedTracer;
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
		this.tracer = new WhittedTracer(this);
	}

	public void addObject(GeometricObject obj) {
		this.objects.add(obj);
	}

	public void addLight(Light light) {
		this.lights.add(light);
	}

	public void build() {
		camera = new PinholeCamera(new Vector3d(0, 300, 1000), new Vector3d(0,
				0, 0), new Vector3d(0, 1, 0), 1000.0, 1, 1.0);
		camera.computeUVW();

		vp.setHorizontalRes(3000);
		vp.setVerticalRes(3000);
		vp.setPixelSize(1.0);
		vp.setGamma(1.0);
		vp.setSampleNum(1);
		vp.setMaxDepth(10);

		background = RGBColor.black();
		ambientLight = AmbientLight.black();

		// this.addLight(DirectionalLight.downWhite());
		this.addLight(new PointLight(1, new Vector3d(500, 700, 200)));

		this.addObject(new Sphere(new Phong(this, 0.25, 0.75, 1, 25,
				new RGBColor(0, 1, 0), vp.getSampler()), new Vector3d(-400.0,
				150.0, 0.0), 150.0));
		this.addObject(new Sphere(new Phong(this, 0.25, 0.75, 1, 25,
				new RGBColor(1, 0, 0), vp.getSampler()), new Vector3d(0.0,
				200.0, 0.0), 200.0));
		this.addObject(new Sphere(new Phong(this, 0.25, 0.75, 1, 25,
				new RGBColor(0, 0, 1), vp.getSampler()), new Vector3d(220.0,
				100.0, 150.0), 100.0));
		this.addObject(new Plane(new Matte(this, 0.25, 0.75, new RGBColor(1, 1,
				0), vp.getSampler()), new Vector3d(0.0, 0.0, 0.0),
				new Vector3d(0, 1, 0)));
		// this.addObject(new Triangle(new Phong(this, 0.25, 0.75, 1, 25,
		// new RGBColor(1, 0, 0)), new Vector3d(300, 0, 300), new Vector3d(
		// 400, 0, 100), new Vector3d(300, 200, 300)));
		// this.addObject(new Plane(new Matte(this, 0.25, 0.75, new RGBColor(1,
		// 1,
		// 0)), new Vector3d(0.0, 0.0, -500.0), new Vector3d(0, 1, 1)));
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
