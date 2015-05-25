package ar.edu.itba.gc.parser.global.builder;

import java.util.List;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.camera.PinholeCamera;
import ar.edu.itba.gc.parser.global.Attribute;
import ar.edu.itba.gc.parser.global.Transformation;
import ar.edu.itba.gc.world.World;

public class CameraBuilder {

	private List<Attribute> attributes;
	private Transformation transformation;

	public CameraBuilder(List<Attribute> attributes,
			Transformation transformation) {
		super();
		this.attributes = attributes;
		this.transformation = transformation;
	}

	public void build() {
		Double distance = null;
		if (transformation != null && transformation.getName().equals("LookAt")) {
			for (Attribute a : attributes) {
				switch (a.getName()) {
				case "focaldistance":
					distance = Double.valueOf(a.getValue());
					break;
				default:
					break;
				}
			}
			List<String> values = transformation.getValues();
			if (values != null && values.size() == 9) {
				Vector3d eye = buildVectorFromStringList(values.subList(0, 3));
				Vector3d lookAt = buildVectorFromStringList(values
						.subList(3, 6));
				Vector3d up = buildVectorFromStringList(values.subList(6, 9));

				World.getInstance().camera = new PinholeCamera(eye, lookAt, up,
						distance);
			}
		}
	}

	private Vector3d buildVectorFromStringList(List<String> points) {
		if (points != null && points.size() == 3) {
			return new Vector3d(Double.valueOf(points.get(0)),
					Double.valueOf(points.get(1)),
					Double.valueOf(points.get(2)));
		}
		return null;
	}

}
