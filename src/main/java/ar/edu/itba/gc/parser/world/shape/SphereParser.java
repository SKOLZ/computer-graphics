package ar.edu.itba.gc.parser.world.shape;

import java.util.List;
import java.util.Scanner;

import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.primitives.Sphere;

class SphereParser {

	private static SphereParser instance = null;

	static SphereParser instance() {
		if (instance == null)
			instance = new SphereParser();
		return instance;
	}

	private SphereParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	Sphere parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		Double radius = null;
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "radius":
				radius = Double.valueOf(a.getValue());
				break;
			default:
				break;
			}
		}
		if (radius != null) {
			return new Sphere(radius);
		}
		throw new IllegalArgumentException("No radius setted for plane");
	}

}
