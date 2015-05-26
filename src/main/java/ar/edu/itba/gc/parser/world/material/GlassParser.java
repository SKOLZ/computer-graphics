package ar.edu.itba.gc.parser.world.material;

import java.util.List;
import java.util.Map;

import ar.edu.itba.gc.material.Transparent;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.texture.ConstantColor;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.world.World;

class GlassParser {

	private static GlassParser instance = null;

	static GlassParser instance() {
		if (instance == null)
			instance = new GlassParser();
		return instance;
	}

	private GlassParser() {
	}

	Transparent parse(List<Attribute> attributes,
			Map<String, Texture> textures, World world) {
		RGBColor kr = null;
		RGBColor kt = null;
		if (attributes != null) {
			for (Attribute a : attributes) {
				switch (a.getName()) {
				case "Kr":
					String[] krValues = a.getValue().split(" ");
					kr = new RGBColor(Double.valueOf(krValues[0]),
							Double.valueOf(krValues[1]),
							Double.valueOf(krValues[2]));
					break;
				case "Kt":
					String[] ktValues = a.getValue().split(" ");
					kt = new RGBColor(Double.valueOf(ktValues[0]),
							Double.valueOf(ktValues[1]),
							Double.valueOf(ktValues[2]));
					break;
				default:
					break;
				}
			}
		}
		if (kr == null || kt == null) {
			throw new IllegalArgumentException("Missing parameters for Glass");
		}
		return new Transparent(world, 0.25, 0.2, 0, 2000,
				new ConstantColor(kr), world.vp.getSampler(), 1.5, 0.05, 0.95,
				kt);
	}

}
