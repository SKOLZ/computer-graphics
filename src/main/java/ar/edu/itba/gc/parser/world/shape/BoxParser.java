package ar.edu.itba.gc.parser.world.shape;

import java.util.List;
import java.util.Scanner;

import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.primitives.Box;

class BoxParser {

	private static BoxParser instance = null;

	static BoxParser instance() {
		if (instance == null)
			instance = new BoxParser();
		return instance;
	}

	private BoxParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	Box parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		Double width = null, height = null, depth = null;
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "width":
				width = Double.valueOf(a.getValue());
				break;
			case "height":
				height = Double.valueOf(a.getValue());
				break;
			case "depth":
				depth = Double.valueOf(a.getValue());
				break;
			default:
				break;
			}
		}
		if (width != null && height != null && depth != null) {
			return new Box(height, width, depth);
		}
		throw new IllegalArgumentException("Missing arguments for Box");
	}

}
