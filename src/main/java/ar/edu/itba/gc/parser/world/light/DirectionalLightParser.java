package ar.edu.itba.gc.parser.world.light;

import java.util.List;
import java.util.Scanner;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.light.DirectionalLight;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.util.RGBColor;

import com.google.common.collect.Lists;

class DirectionalLightParser {

	private static DirectionalLightParser instance = null;

	static DirectionalLightParser instance() {
		if (instance == null)
			instance = new DirectionalLightParser();
		return instance;
	}

	private DirectionalLightParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	DirectionalLight parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		RGBColor color = null;
		Vector3d to = null;
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "to":
				to = this.buildVectorFromStringList(Lists.newArrayList(a
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
		if (to == null) {
			throw new IllegalArgumentException(
					"No direction setted for DirectionalLight");
		}
		return new DirectionalLight(to, color != null ? color
				: RGBColor.black());
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
