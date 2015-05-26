package ar.edu.itba.gc.parser.world.material;

import java.util.List;
import java.util.Map;

import ar.edu.itba.gc.material.Reflective;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.world.World;

class MirrorParser {

	private static MirrorParser instance = null;

	static MirrorParser instance() {
		if (instance == null)
			instance = new MirrorParser();
		return instance;
	}

	private MirrorParser() {
	}

	Reflective parse(List<Attribute> attributes, Map<String, Texture> textures,
			World world) {
		Texture cd = null;
		if (attributes != null) {
			for (Attribute a : attributes) {
				switch (a.getName()) {
				case "Kr":
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
		return new Reflective(world, 0, 0, 0, 100, 0.75, cd,
				world.vp.getSampler(), RGBColor.white());
	}

}
