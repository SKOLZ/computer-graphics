package ar.edu.itba.gc.parser.world.transform;

import java.util.Scanner;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

public class TransformParser {

	private static TransformParser instance = null;

	public static TransformParser instance() {
		if (instance == null)
			instance = new TransformParser();
		return instance;
	}

	private TransformParser() {
	}

	public Transformation parse(String[] line) {
		double[] matrix = new double[16];
		for (int i = 1; i < line.length; i++) {
			matrix[i - 1] = Double.valueOf(line[i]);
		}
		return new Transformation(new Matrix4d(matrix));
	}

	public Transformation parse(Scanner scanner) {
		Transformation transform = new Transformation();
		while (scanner.hasNext()) {
			String line = scanner.nextLine().trim();
			String[] lineValues = line.split(" ");
			switch (lineValues[0]) {
			case "Identity":
				break;
			case "Transform":
				return this.parse(line.replace("[", "").replace("]", "").split(" "));
			case "Translate":
				transform.addTransaltion(new Vector3d(Double.valueOf(lineValues[1]), Double.valueOf(lineValues[2]),
						Double.valueOf(lineValues[3])));
				break;
			case "Rotate":
				transform.addRotation(new Rotation(new Vector3d(Double.valueOf(lineValues[2]),
						Double.valueOf(lineValues[3]), Double.valueOf(lineValues[4])), Double.valueOf(lineValues[1])));
				break;
			case "Scale":
				transform.addScaling(new Scaling(new Vector3d(Double.valueOf(lineValues[1].replaceAll("\\.", "")),
						Double.valueOf(lineValues[2].replaceAll("\\.", "")),
						Double.valueOf(lineValues[3].replaceAll("\\.", "")))));
				break;
			case "TransformEnd":
				return transform;
			default:
				break;
			}
		}
		throw new IllegalArgumentException("TransformEnd not found");
	}

}
