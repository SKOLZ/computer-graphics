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
import ar.edu.itba.gc.mapping.RectangularMap;
import ar.edu.itba.gc.material.Matte;
import ar.edu.itba.gc.material.Phong;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.primitives.Instance;
import ar.edu.itba.gc.primitives.Plane;
import ar.edu.itba.gc.primitives.Sphere;
import ar.edu.itba.gc.texture.ConstantColor;
import ar.edu.itba.gc.texture.chessTexture;
import ar.edu.itba.gc.tracer.Tracer;
import ar.edu.itba.gc.tracer.WhittedTracer;
import ar.edu.itba.gc.util.KDNode;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.util.Ray;

public class World {

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
				0, 0), new Vector3d(0, 1, 0), 1000.0, 1.5, 1.0);
		camera.computeUVW();

		vp.setHorizontalRes(1000);
		vp.setVerticalRes(1000);
		vp.setPixelSize(1.0);
		vp.setGamma(1.0);
		vp.setSampleNum(1);
		vp.setMaxDepth(10);
		background = RGBColor.black();
		ambientLight = AmbientLight.white();

		// this.addLight(DirectionalLight.downWhite());

		// this.addLight(new PointLight(1, new Vector3d(100, 700, 200)));

		// List<GeometricObject> objs = new LinkedList<GeometricObject>();

		// objs.add(new Sphere(new Phong(this, 0.25, 0.75, 1, 25, new
		// RGBColor(0,
		// 0, 1), vp.getSampler()), new Vector3d(-400.0, 150.0, 0.0),
		// 150.0));
		// objs.add(new Sphere(new Reflective(this, 0.25, 0.5, 0.15, 100, 0.75,
		// new RGBColor(1, 0, 0), vp.getSampler(),
		// new RGBColor(0.65, 0, 0)), new Vector3d(0.0, 200.0, 0.0), 200.0));
		// objs.add(new Sphere(new Transparent(this, 0.25, 0.75, 1, 100,
		// new RGBColor(0, 0, 0), vp.getSampler(), 1.5, 0.1, 0.9, RGBColor
		// .white()), new Vector3d(200.0, 100.0, 300.0), 100.0));
		//
		// objs.add(new Plane(new Matte(this, 0.25, 0.75, new RGBColor(1, 1, 0),
		// vp.getSampler()), new Vector3d(0.0, 0.0, 0.0), new Vector3d(0,
		// 1, 0)));
		//
		// this.tree = KDNode.build(objs);

		// this.addObject(new Triangle(new Phong(this, 0.25, 0.75, 1, 25, new
		// RGBColor(
		// 1, 0, 0), vp.getSampler()), new Vector3d(300, 0, 300),
		// new Vector3d(400, 0, 100), new Vector3d(300, 200, 300)));
		//
		// this.addObject(new Triangle(new Phong(this, 0.25, 0.75, 1, 25, new
		// RGBColor(
		// 0, 1, 1), vp.getSampler()), new Vector3d(0, 0, 0),
		// new Vector3d(100, 100, 25), new Vector3d(30, 20, 30)));

		// objs.add(new Triangle(new Phong(this, 0.25, 0.75, 1, 25, new
		// RGBColor(
		// 1, 0, 0), vp.getSampler()), new Vector3d(300, 0, 300),
		// new Vector3d(400, 0, 100), new Vector3d(300, 200, 300)));
		//
		// objs.add(new Triangle(new Phong(this, 0.25, 0.75, 1, 25, new
		// RGBColor(
		// 0, 1, 1), vp.getSampler()), new Vector3d(0, 0, 0),
		// new Vector3d(100, 100, 25), new Vector3d(30, 20, 30)));

		this.addLight(new PointLight(1, new Vector3d(0, 800, 800)));
		//
		File f = new File("world.png");
		BufferedImage img;
		try {
			img = ImageIO.read(f);
			// Instance sphere = new Instance(new Sphere(new Phong(this, 0.25,
			// 0.75, 1, 25, new ImageTexture(img.getWidth(), img.getHeight(),
			// img, new SphericalMap()),
			// vp.getSampler())));
			// sphere.scale(100, 100, 100);
			// sphere.rotateY(250);
			// sphere.translate(0, 100, 0);
			// addObject(sphere);

			Phong p = new Phong(this, 0.25, 0.8, 0.15, 50.0, new ConstantColor(
					new RGBColor(0, 1, 0)), vp.getSampler());
			Sphere s = new Sphere(p);

			Instance sphere = new Instance(s);
			sphere.scale(100, 100, 100);
			sphere.rotateY(250);
			sphere.translate(0, 0, -400);
			// addObject(sphere);

			addObject(new Sphere(p, new Vector3d(0, 0, -400), 100));

			Instance plane = new Instance(new Plane(new Matte(this, 0.25, 0.75,
					new chessTexture(new RectangularMap()), vp.getSampler()),
					new Vector3d(0.0, 0.0, 0.0), new Vector3d(0, 1, 0)));
			plane.rotateY(45);
			addObject(plane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// trasparency test

		// addObject(new Plane(new Matte(this, 0.25, 0.75,
		// new ConstantColor(new RGBColor(1,0,0)), vp.getSampler()),
		// new Vector3d(0.0, 0.0, 0.0), new Vector3d(0, 1, 0)));
		//
		// addObject(new Sphere(new Transparent(this, 0.25,
		// 0.75, 1, 25, new ConstantColor(new RGBColor(1,0,0)),
		// vp.getSampler(), 1, 0.2, 0.8, new RGBColor(0.65)), new Vector3d(0,
		// 300, 0), 300.0));
		//
		// addObject(new Sphere(new Matte(this, 0.25, 0.75, new
		// ConstantColor(new RGBColor(0,1,0)), vp.getSampler()), new
		// Vector3d(200, 100, 500), 100.0));
	}

	public void renderScene() {
		BufferedImage img = camera.renderScene(this);
		System.out.println(Ray.count);
		// System.out.println(tree.count);
		for (GeometricObject obj : objects) {
			System.out.println(obj.count);
		}
		File f = new File("MyFile.png");
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
