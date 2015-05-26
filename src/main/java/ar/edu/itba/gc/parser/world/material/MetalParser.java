package ar.edu.itba.gc.parser.world.material;

import java.util.List;
import java.util.Map;

import ar.edu.itba.gc.material.Phong;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.world.World;

class MetalParser {

	private static MetalParser instance = null;

	static MetalParser instance() {
		if (instance == null)
			instance = new MetalParser();
		return instance;
	}

	private MetalParser() {
	}

	Phong parse(List<Attribute> attributes, Map<String, Texture> textures,
			World world) {

		Double uroughness = null;
		Texture texture = null;
		if (attributes != null) {
			for (Attribute a : attributes) {
				switch (a.getName()) {
				case "Kr":
					texture = textures.get(a.getValue());
					break;
				case "uroughness":
					uroughness = Double.valueOf(a.getValue());
					break;
				default:
					break;
				}
			}
		}

		if (uroughness == null || texture == null) {
			throw new IllegalArgumentException("Missing parameters for Metal2");
		}

		return new Phong(world, 0.25, 0.6, 0.2, 1 / uroughness, texture,
				world.vp.getSampler());
	}

}
