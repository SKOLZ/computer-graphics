package ar.edu.itba.gc.parser.world.texture;

import java.util.List;

import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.texture.ConstantColor;
import ar.edu.itba.gc.util.RGBColor;

class ConstantParser {

	private static ConstantParser instance = null;

	static ConstantParser instance() {
		if (instance == null)
			instance = new ConstantParser();
		return instance;
	}

	private ConstantParser() {
	}

	ConstantColor parse(List<Attribute> attributes) {
		RGBColor color = null;
		if (attributes != null) {
			for (Attribute a : attributes) {
				switch (a.getName()) {
				case "value":
					String[] values = a.getValue().split(" ");
					color = new RGBColor(Double.valueOf(values[0]),
							Double.valueOf(values[1]),
							Double.valueOf(values[2]));
				default:
					break;
				}
			}
		}
		if (color == null) {
			throw new IllegalArgumentException(
					"No color for ConstantTexture setted");
		}
		return new ConstantColor(color);
	}

}
