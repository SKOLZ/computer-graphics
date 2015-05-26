package ar.edu.itba.gc.parser.world.texture;

import java.util.List;
import java.util.Scanner;

import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.texture.Texture;

public class TextureParser {

	private static TextureParser instance = null;

	public static TextureParser instance() {
		if (instance == null)
			instance = new TextureParser();
		return instance;
	}

	private TextureParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	public Texture parse(Scanner scanner, String type) {
		List<Attribute> attributes = attributesParser.parse(scanner);
		switch (type) {
		case "constant":
			return ConstantParser.instance().parse(attributes);
		case "imagemap":
			return ImageMapParser.instance().parse(attributes);
		default:
			throw new IllegalArgumentException("Invalid Texture type");
		}
	}

}
