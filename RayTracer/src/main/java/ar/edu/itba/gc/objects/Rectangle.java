package ar.edu.itba.gc.objects;

import javax.vecmath.Vector4d;

import ar.edu.itba.gc.materials.Material;
import ar.edu.itba.gc.utils.RGBColor;
import ar.edu.itba.gc.utils.Vectors;

public class Rectangle extends Plane {

	private Vector4d corner;
	private Vector4d sideA;
	private Vector4d sideB;
	private double aLengthSquared;
	private double bLengthSquared;

	public Rectangle(Vector4d sideA, Vector4d sideB, double aLength,
			double bLength, Material material, RGBColor color, Vector4d point,
			Vector4d normal) {
		super(material, color, point, normal);
		this.sideA = sideA;
		this.sideB = sideB;
		this.aLengthSquared = aLength * aLength;
		this.bLengthSquared = bLength * bLength;
	}

	@Override
	public double hit(Vector4d origin, Vector4d direction) {
		double t = super.hit(origin, direction);
		if (t > 0.0) {
			Vector4d p = Vectors.plus(origin, Vectors.scale(direction, t));
			Vector4d d = Vectors.sub(p, corner);

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
