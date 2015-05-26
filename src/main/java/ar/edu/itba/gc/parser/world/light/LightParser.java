package ar.edu.itba.gc.parser.world.light;

import java.util.Scanner;

import ar.edu.itba.gc.light.Light;

public class LightParser {

	private static LightParser instance = null;

	public static LightParser instance() {
		if (instance == null)
			instance = new LightParser();
		return instance;
	}

	private LightParser() {
	}

	public Light parse(Scanner scanner, String type) {
		if (type == null)
			throw new IllegalArgumentException("No type setted for LightSource");
		switch (type) {
		case "point":
			return PointLightParser.instance().parse(scanner);
		case "distance":
			break;
		case "infinite":
			break;
		}
		throw new IllegalArgumentException("Invalid LightSource type");
	}

}
