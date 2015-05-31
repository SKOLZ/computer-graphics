package ar.edu.itba.gc.parser.world.shape;

import java.util.List;
import java.util.Scanner;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;
import ar.edu.itba.gc.primitives.Mesh;

class MeshParser {

	private static MeshParser instance = null;

	static MeshParser instance() {
		if (instance == null)
			instance = new MeshParser();
		return instance;
	}

	private MeshParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	Mesh parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);

		int[] triindices = null;
		Vector3d[] vertices = null;
		Vector3d[] normals = null;
		double[] u = null;
		double[] v = null;

		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "triindices":
				triindices = this.parseInteger(a.getValue().split(" "));
				break;
			case "P":
				double[] pValues = this.parseDouble(a.getValue().split(" "));
				vertices = new Vector3d[pValues.length / 3];
				for (int i = 0; i < pValues.length; i += 3) {
					vertices[i / 3] = new Vector3d(pValues[i], pValues[i + 1],
							pValues[i + 2]);
				}
				break;
			case "N":
				double[] nValues = this.parseDouble(a.getValue().split(" "));
				normals = new Vector3d[nValues.length / 3];
				for (int i = 0; i < nValues.length; i += 3) {
					normals[i / 3] = new Vector3d(nValues[i], nValues[i + 1],
							nValues[i + 2]);
				}
				break;
			case "uv":
				final double[] uvList = this.parseDouble(a.getValue()
						.split(" "));
				if (uvList != null && uvList.length > 1) {
					int items = (int) Math.ceil(uvList.length / 2);
					u = new double[items];
					v = new double[items];
					for (int i = 0; i < u.length; i++) {
						u[i] = uvList[i * 2 + 1];
						v[i] = uvList[i * 2];
					}
				} else {
					u = null;
					v = null;
				}
				break;
			default:
				break;
			}
		}
		return new Mesh(vertices, normals, triindices, u, v, true);
	}

	private double[] parseDouble(final String[] array) {
		double[] ret = new double[array.length];
		int i = 0;

		try {
			for (i = 0; i < array.length; i++) {
				ret[i] = Double.valueOf(array[i]);
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid number \"" + array[i]
					+ "\"");
		}

		return ret;
	}

	private int[] parseInteger(final String[] array) {
		int[] ret = new int[array.length];
		int i = 0;

		try {
			for (i = 0; i < array.length; i++) {
				ret[i] = Integer.valueOf(array[i]);
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid number \"" + array[i]
					+ "\"");
		}

		return ret;
	}

}
