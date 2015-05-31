package ar.edu.itba.gc.parser.world.shape;

import java.util.Scanner;

import ar.edu.itba.gc.primitives.GeometricObject;

public class ShapeParser {

	private static ShapeParser instance = null;

	public static ShapeParser instance() {
		if (instance == null)
			instance = new ShapeParser();
		return instance;
	}

	private ShapeParser() {
	}

	public GeometricObject parse(Scanner scanner, String type) {
		if (type == null)
			throw new IllegalArgumentException("No type setted for shape");
		switch (type) {
		case "plane":
			return PlaneParser.instance().parse(scanner);
		case "sphere":
			return SphereParser.instance().parse(scanner);
		case "mesh":
			return MeshParser.instance().parse(scanner);
		default:
			throw new IllegalArgumentException("Invalid shape type");
		}
	}

}
