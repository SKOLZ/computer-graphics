package ar.edu.itba.gc.parser.world.light;

import java.util.List;
import java.util.Scanner;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.light.PointLight;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.util.RGBColor;

import com.google.common.collect.Lists;

class PointLightParser {

	private static PointLightParser instance = null;

	static PointLightParser instance() {
		if (instance == null)
			instance = new PointLightParser();
		return instance;
	}

	private PointLightParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	PointLight parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		Double ls = null;
		RGBColor color = null;
		Vector3d position = null;
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "gain":
				ls = Double.valueOf(a.getValue());
				break;
			case "from":
				position = this.buildVectorFromStringList(Lists.newArrayList(a
						.getValue().split(" ")));
				break;
			case "L":
				String[] values = a.getValue().split(" ");
				color = new RGBColor(Double.valueOf(values[0]),
						Double.valueOf(values[1]), Double.valueOf(values[2]));
				break;
			default:
				break;
			}
		}
		if (ls != null && position != null) {
			return new PointLight(ls, color != null ? color : RGBColor.white(),
					position);
		}
		throw new IllegalArgumentException(
				"No gain (ls) or position setted for PointLight");
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
