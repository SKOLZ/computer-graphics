package ar.edu.itba.gc.parser.world;

import java.util.List;
import java.util.Scanner;

import ar.edu.itba.gc.light.AreaLight;
import ar.edu.itba.gc.material.Emisive;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.world.World;

class AreaLightParser {

	private static AreaLightParser instance = null;

	static AreaLightParser instance() {
		if (instance == null)
			instance = new AreaLightParser();
		return instance;
	}

	private AreaLightParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	AreaLight parse(Scanner scanner, World world) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		boolean shadows = true;
		Double ls = null;
		RGBColor color = null;
		Emisive material = new Emisive(world);

		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "gain":
				ls = Double.valueOf(a.getValue());
				break;
			case "L":
				String[] values = a.getValue().split(" ");
				color = new RGBColor(Double.valueOf(values[0]), Double.valueOf(values[1]), Double.valueOf(values[2]));
				material.setCe(color);
				break;
			case "power":
				material.setLs(Double.valueOf(a.getValue()));
				break;
			default:
				break;
			}
		}
		if (ls != null) {
			return new AreaLight(shadows, ls, color, material);
		}
		throw new IllegalArgumentException("No gain (ls) or position setted for PointLight");
	}

}
