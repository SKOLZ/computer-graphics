package ar.edu.itba.gc.parser.world.shape;

import java.util.List;
import java.util.Scanner;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.primitives.Plane;

import com.google.common.collect.Lists;

class PlaneParser {

	private static PlaneParser instance = null;

	static PlaneParser instance() {
		if (instance == null)
			instance = new PlaneParser();
		return instance;
	}

	private PlaneParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	Plane parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		Vector3d normal = null;
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "n":
				normal = this.buildVectorFromStringList(Lists.newArrayList(a
						.getValue().split(" ")));
				break;
			default:
				break;
			}
		}
		if (normal != null) {
			return new Plane(normal);
		}
		throw new IllegalArgumentException("No normal setted for plane");
	}

	private Vector3d buildVectorFromStringList(List<String> points) {
		if (points != null && points.size() == 3) {
			return new Vector3d(Double.valueOf(points.get(0)),
					Double.valueOf(points.get(1)),
					Double.valueOf(points.get(2)));
		}
		return null;
	}

}
