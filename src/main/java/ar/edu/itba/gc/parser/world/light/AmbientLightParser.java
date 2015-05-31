package ar.edu.itba.gc.parser.world.light;

import java.util.List;
import java.util.Scanner;

import ar.edu.itba.gc.light.AmbientLight;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.util.RGBColor;

class AmbientLightParser {

	private static AmbientLightParser instance = null;

	static AmbientLightParser instance() {
		if (instance == null)
			instance = new AmbientLightParser();
		return instance;
	}

	private AmbientLightParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	AmbientLight parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		RGBColor color = null;
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "L":
				String[] values = a.getValue().split(" ");
				color = new RGBColor(Double.valueOf(values[0]),
						Double.valueOf(values[1]), Double.valueOf(values[2]));
				break;
			default:
				break;
			}
		}
		if (color == null)
			throw new IllegalArgumentException(
					"No color setted for ambient light");
		return new AmbientLight(color);
	}

}
