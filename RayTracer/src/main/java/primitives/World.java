package primitives;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.vecmath.Vector4d;

public class World {

	public ViewPlane vp;
	public RGBColor background;
	public Sphere sphere;
	public Tracer tracer;
	
	public void build() {
		vp = new ViewPlane();
		vp.setHorizontalRes(200);
		vp.setVerticalRes(200);
		vp.setPixelSize(1.0);
		vp.setGamma(1.0);
		background = new RGBColor(0, 0, 0);
		tracer = new SingleSphere(this);
		sphere = new Sphere(null, null, new Vector4d(0.0, 0.0, 0.0, 0.0), 85.0);
	}

	public void renderScene() {
		RGBColor pixelColor;
		Ray ray = new Ray();
		double z = 100.0;
		
		ray.setDirection(new Vector4d(0.0, 0.0, -1.0, 0.0));
		BufferedImage img = new BufferedImage(vp.getHorizontalRes(), vp.getVerticalRes(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < vp.getVerticalRes(); i++) {
			for (int j = 0; j < vp.getHorizontalRes(); j++) {
				double x = vp.getPixelSize() * (j - 0.5 * (vp.getHorizontalRes() - 1.0));				
				double y = vp.getPixelSize() * (i - 0.5 * (vp.getVerticalRes() - 1.0));
				ray.setOrigin(new Vector4d(x, y, z, 0.0));
				pixelColor = tracer.traceRay(ray);
				displayPixel(i,j,pixelColor,img);
			}
		}
		File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void displayPixel(int i, int j, final RGBColor pixelColor, BufferedImage img) {
		pixelColor.correctColor();
		if (vp.getGamma() != 1.0) {
			pixelColor.applyGamma(vp.getGammaInverse());
		}
		int r = (int) (pixelColor.getR() * 255);
		int g = (int) (pixelColor.getG() * 255);
		int b = (int) (pixelColor.getB() * 255);
		int rgb = ( r<< 16) | (g << 8) | b;
		img.setRGB(j, i, rgb);
	}
	
}
