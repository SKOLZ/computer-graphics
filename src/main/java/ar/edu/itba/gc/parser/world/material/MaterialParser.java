package ar.edu.itba.gc.parser.world.material;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.world.World;

public class MaterialParser {

	private static MaterialParser instance = null;

	public static MaterialParser instance() {
		if (instance == null)
			instance = new MaterialParser();
		return instance;
	}

	private MaterialParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();
	private MatteParser matteParser = MatteParser.instance();
	private MetalParser metalParser = MetalParser.instance();
	private GlassParser glassParser = GlassParser.instance();
	private MirrorParser mirrorParser = MirrorParser.instance();

	public Material parse(Scanner scanner, Map<String, Texture> textures,
			World world) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		for (Attribute a : attributes) {
			if ("type".equals(a.getName())) {
				switch (a.getValue()) {
				case "matte":
					return matteParser.parse(attributes, textures, world);
				case "metal2":
					return metalParser.parse(attributes, textures, world);
				case "glass":
					return glassParser.parse(attributes, textures, world);
				case "mirror":
					return mirrorParser.parse(attributes, textures, world);
				default:
					throw new IllegalArgumentException("Invalid Material type");
				}
			}
		}

		throw new IllegalArgumentException("No Material type specified");
	}

}
