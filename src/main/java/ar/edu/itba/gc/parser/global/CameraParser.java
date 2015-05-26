package ar.edu.itba.gc.parser.global;

import java.util.List;
import java.util.Scanner;

import ar.edu.itba.gc.camera.Camera;
import ar.edu.itba.gc.camera.PinholeCamera;
import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;

class CameraParser {

	private static CameraParser instance = null;

	static CameraParser instance() {
		if (instance == null)
			instance = new CameraParser();
		return instance;
	}

	private CameraParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	Camera parse(Scanner scanner, String type, LookAt lookAt) {
		Double distance = null;

		List<Attribute> attributes = attributesParser.parse(scanner);
		for (Attribute attr : attributes) {
			switch (attr.getName()) {
			case "focaldistance":
				distance = Double.valueOf(attr.getValue());
			}
		}
		return new PinholeCamera(lookAt.getEye(), lookAt.getLookAt(),
				lookAt.getUp(), distance);
	}

}
