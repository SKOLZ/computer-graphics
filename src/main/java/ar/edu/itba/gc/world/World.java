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
import ar.edu.itba.gc.mapping.SphericalMap;
import ar.edu.itba.gc.material.Matte;
import ar.edu.itba.gc.material.Phong;
import ar.edu.itba.gc.material.Reflective;
import ar.edu.itba.gc.material.Transparent;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.primitives.Instance;
import ar.edu.itba.gc.primitives.Plane;
import ar.edu.itba.gc.primitives.Sphere;
import ar.edu.itba.gc.texture.ChessTexture;
import ar.edu.itba.gc.texture.ConstantColor;
import ar.edu.itba.gc.texture.ImageTexture;
import ar.edu.itba.gc.tracer.Tracer;
import ar.edu.itba.gc.tracer.WhittedTracer;
import ar.edu.itba.gc.util.RGBColor;

public class World {

	public static final double PI = 3.1415926535897932384;
	public static final double INV_PI = 0.3183098861837906715;
	public static final double TWO_PI = 6.2831853071795864769;
	public static final double INV_TWO_PI = 0.1591549430918953358;

	public ViewPlane vp;
	public RGBColor background;
	public List<GeometricObject> objects;
	// public KDNode<GeometricObject> tree;
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
		vp.setSampleNum(10);
		vp.setMaxDepth(10);
		background = RGBColor.black();
		ambientLight = AmbientLight.black();

		// this.addLight(DirectionalLight.downWhite());

		// this.addLight(new PointLight(1, new Vector3d(100, 700, 200)));

		this.addLight(new PointLight(2, new Vector3d(0, 300, 200)));
		// this.addLight(new PointLight(8, new Vector3d(500, 700, 0)));
		File f = new File("world.png");
		BufferedImage img;
		try {
			img = ImageIO.read(f);

			Reflective reflectiveWhite = new Reflective(this, 0, 0, 0, 100,
					0.75, new ConstantColor(RGBColor.red()), vp.getSampler(),
					RGBColor.white());
			Reflective mirror = new Reflective(this, 0, 0, 0, 100, 0.75,
					new ConstantColor(RGBColor.white()), vp.getSampler(),
					RGBColor.white());
			Reflective reflectiveChess = new Reflective(this, 0.25, 0.75, 1, 1,
					1, new ChessTexture(new RectangularMap()), vp.getSampler(),
					RGBColor.white());
			Matte chessMatte = new Matte(this, 0.25, 0.75, new ChessTexture(
					new RectangularMap()), vp.getSampler());
			Matte worldMatte = new Matte(this, 0.25, 0.75, new ImageTexture(
					img.getWidth(), img.getHeight(), img, new SphericalMap()),
					vp.getSampler());
			Phong greenPhong = new Phong(this, 0.25, 0.6, 0.2, 5,
					new ConstantColor(RGBColor.green()), vp.getSampler());

			Transparent vidrio = new Transparent(this, 0.25, 0.2, 0,
					2000, new ConstantColor(RGBColor.green()), vp.getSampler(),
					1.5, 0.05, 0.95, RGBColor.white());

			// Instance triangle = new Instance(new Triangle(r), new Vector3d(0,
			// 0, 300),
			// new Vector3d(400, 0, 100), new Vector3d(300, 200, 300)));
			Instance plane = new Instance(new Plane(chessMatte, new Vector3d(0,
					0, 0), new Vector3d(0, 1, 0)));
			Instance planeMirror1 = new Instance(new Plane(mirror,
					new Vector3d(0, 0, 0), new Vector3d(0, 0, 1)));
			Instance planeMirror2 = new Instance(new Plane(mirror,
					new Vector3d(0, 0, 0), new Vector3d(0, 1, 0.01)));
			Instance planeMirror3 = new Instance(new Plane(mirror,
					new Vector3d(0, 0, 0), new Vector3d(0, 0, 1)));
			Instance sphere1 = new Instance(new Sphere(greenPhong));
			Instance sphere2 = new Instance(new Sphere(worldMatte));
			Instance sphere3 = new Instance(new Sphere(worldMatte));

			planeMirror1.translate(0, 0, -600);
			planeMirror2.translate(-600, 0, 0);
			planeMirror3.translate(600, 0, 0);

			sphere1.scale(100, 100, 100);
			sphere1.translate(0, 100, 0);
			sphere2.scale(100, 100, 100);
			sphere2.rotateZ(180);
			sphere2.translate(0, 100, -400);

			sphere3.scale(100, 100, 100);
			sphere3.rotateZ(180);
			sphere3.translate(300, 100, 200);

			// triangle.translate(-300, 100, -350);
			// triangle.rotateZ(-90);
			// triangle.scale(100, 1, 1);

//			addObject(planeMirror1);
			// addObject(planeMirror2);
			// addObject(planeMirror3);
			addObject(sphere1);
			addObject(plane);
//			addObject(sphere2);
//			addObject(sphere3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void renderScene() {
		saveImage(camera.renderScene(this), "MyFile.png");
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
