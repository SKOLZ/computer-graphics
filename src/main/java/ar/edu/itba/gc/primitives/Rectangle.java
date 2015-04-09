package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.materials.Material;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Vectors;

public class Rectangle extends Plane {

	private Vector3d corner;
	private Vector3d sideA;
	private Vector3d sideB;
	private double aLengthSquared;
	private double bLengthSquared;

	public Rectangle(Vector3d sideA, Vector3d sideB, double aLength,
			double bLength, Material material, RGBColor color, Vector3d point,
			Vector3d normal) {
		super(material, color, point, normal);
		this.sideA = sideA;
		this.sideB = sideB;
		this.aLengthSquared = aLength * aLength;
		this.bLengthSquared = bLength * bLength;
	}

	@Override
	public double hit(Vector3d origin, Vector3d direction) {
		double t = super.hit(origin, direction);
		if (t > 0.0) {
			Vector3d p = Vectors.plus(origin, Vectors.scale(direction, t));
			Vector3d d = Vectors.sub(p, corner);

			double ddota = d.dot(sideA);

			if (ddota < 0.0 || ddota > aLengthSquared) {
				return -1.0;
			}

			double ddotb = d.dot(sideB);

			if (ddotb < 0.0 || ddotb > bLengthSquared) {
				return -1.0;
			}
		}

		return t;
	}

}
