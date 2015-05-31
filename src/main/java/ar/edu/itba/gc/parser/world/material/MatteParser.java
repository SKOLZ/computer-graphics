package ar.edu.itba.gc.parser.world.material;

import java.util.List;
import java.util.Map;

import ar.edu.itba.gc.material.Matte;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.world.World;

class MatteParser {

	private static MatteParser instance = null;

	static MatteParser instance() {
		if (instance == null)
			instance = new MatteParser();
		return instance;
	}

	private MatteParser() {
	}

	Matte parse(List<Attribute> attributes, Map<String, Texture> textures,
			World world) {
		Texture cd = null;
		if (attributes != null) {
			for (Attribute a : attributes) {
				switch (a.getName()) {
				case "Kd":
					cd = textures.get(a.getValue());
					break;
				default:
					break;
				}
			}
		}
		if (cd == null) {
			throw new IllegalArgumentException("Inexistent texture");
		}
		return new Matte(world, 0.25, 1.0, cd, world.vp.getSampler());
	}

}
