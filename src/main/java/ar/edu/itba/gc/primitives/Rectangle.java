//package ar.edu.itba.gc.primitives;
//
//import javax.vecmath.Vector3d;
//
//import ar.edu.itba.gc.material.Material;
//import ar.edu.itba.gc.util.ShadeRec;
//import ar.edu.itba.gc.util.Vectors;
//
//public class Rectangle extends Plane {
//
//	private Vector3d corner;
//	private Vector3d sideA;
//	private Vector3d sideB;
//	private double aLengthSquared;
//	private double bLengthSquared;
//
//	public Rectangle(Vector3d sideA, Vector3d sideB, double aLength,
//			double bLength, Material material, Vector3d point, Vector3d normal) {
//		super(material, point, normal);
//		this.sideA = sideA;
//		this.sideB = sideB;
//		this.aLengthSquared = aLength * aLength;
//		this.bLengthSquared = bLength * bLength;
//	}
//
//	// TODO: Arreglar viejaaa
//
//	@Override
//	public double hit(ShadeRec sr, double tmin, Vector3d origin, Vector3d direction) {
//		ShadeRec auxSr = super.hit(sr, origin, direction);
//		if (auxSr.getT() > 0.0) {
//			Vector3d p = Vectors.plus(origin,
//					Vectors.scale(direction, auxSr.getT()));
//			Vector3d d = Vectors.sub(p, corner);
//
//			double ddota = d.dot(sideA);
//
//			if (ddota < 0.0 || ddota > aLengthSquared) {
//				return sr;
//			}
//
//			double ddotb = d.dot(sideB);
//
//			if (ddotb < 0.0 || ddotb > bLengthSquared) {
//				return sr;
//			}
//		}
//
//		return auxSr;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (!super.equals(obj))
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Rectangle other = (Rectangle) obj;
//		if (Double.doubleToLongBits(aLengthSquared) != Double
//				.doubleToLongBits(other.aLengthSquared))
//			return false;
//		if (Double.doubleToLongBits(bLengthSquared) != Double
//				.doubleToLongBits(other.bLengthSquared))
//			return false;
//		if (corner == null) {
//			if (other.corner != null)
//				return false;
//		} else if (!corner.equals(other.corner))
//			return false;
//		if (sideA == null) {
//			if (other.sideA != null)
//				return false;
//		} else if (!sideA.equals(other.sideA))
//			return false;
//		if (sideB == null) {
//			if (other.sideB != null)
//				return false;
//		} else if (!sideB.equals(other.sideB))
//			return false;
//		return true;
//	}
//
//}
