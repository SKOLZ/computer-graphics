package ar.edu.itba.gc.parser.world.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import ar.edu.itba.gc.mapping.Mapping;
import ar.edu.itba.gc.mapping.RectangularMap;
import ar.edu.itba.gc.mapping.SphericalMap;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.texture.ImageTexture;

class ImageMapParser {

	private static ImageMapParser instance = null;

	static ImageMapParser instance() {
		if (instance == null)
			instance = new ImageMapParser();
		return instance;
	}

	private ImageMapParser() {
	}

	ImageTexture parse(List<Attribute> attributes) {
		int hres = 0;
		int vres = 0;
		BufferedImage image = null;
		Mapping mapping = null;

		if (attributes != null) {
			for (Attribute a : attributes) {
				switch (a.getName()) {
				case "filename":
					try {
						File file = new File(a.getValue());
						image = ImageIO.read(file);
						hres = image.getWidth();
						vres = image.getHeight();
					} catch (Exception e) {
						throw new IllegalArgumentException(
								"Cannot read image from " + a.getValue());
					}
					break;
				case "mapping":
					if (a.getValue().equals("uv"))
						mapping = null;
					else if (a.getValue().equals("spherical"))
						mapping = new SphericalMap();
					else if (a.getValue().equals("planar"))
						mapping = new RectangularMap();
					break;
				default:
					break;
				}
			}
		}

		if (image == null) {
			throw new IllegalArgumentException(
					"Missing parameteres for ImageMap Texture");
		}
		return new ImageTexture(hres, vres, image, mapping);
	}

}
