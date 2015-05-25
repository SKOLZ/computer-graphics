package ar.edu.itba.gc.parser.global.builder;

import java.util.List;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.camera.PinholeCamera;
import ar.edu.itba.gc.parser.global.Attribute;
import ar.edu.itba.gc.parser.global.Transformation;
import ar.edu.itba.gc.world.World;

public class FilmBuilder {

	private List<Attribute> attributes;
	
	public FilmBuilder(List<Attribute> attributes) {
		super();
		this.attributes = attributes;
	}

	public void build() {
		int hres = 640;
		int vres = 480;
		double gamma = 1.0;
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "xresolution":
				hres = Integer.parseInt(a.getValue());
				break;
			case "yresolution":
				vres = Integer.parseInt(a.getValue());
				break;
			case "gamma":
				gamma = Double.parseDouble(a.getValue());
				break;
			default:
				break;
			}
		}
		World w = World.getInstance();
		w.vp.setHorizontalRes(hres);
		w.vp.setVerticalRes(vres);
		w.vp.setGamma(gamma);
	}
}
