package ar.edu.itba.gc.primitives;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Matrixes;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class Instance extends GeometricObject {

	private GeometricObject geometricObject;
	private Matrix4d invMatrix;
	private Matrix4d invTransMatrix;
	private Matrix4d matrix;
	private boolean isTextureTransformed;

	public Instance(Material material) {
		super(material);
	}
	
	public Instance(GeometricObject geometricObject) {
		super();
		this.geometricObject = geometricObject;
		this.matrix = Matrixes.newIdenty4d();
		this.invMatrix = Matrixes.inverse(matrix);
		this.invTransMatrix = Matrixes.transpose(invMatrix);
		
	}
	
	public Instance(GeometricObject geometricObject, Matrix4d matrix) {
		super();
		this.geometricObject = geometricObject;
		this.matrix = matrix;
		this.invMatrix = Matrixes.inverse(matrix);
		this.invTransMatrix = Matrixes.transpose(invMatrix);
		
	}
	
	@Override
	public ShadeRec hit(ShadeRec sr, Vector3d origin, Vector3d direction) {
		Ray invRay = new Ray(origin, direction);
		invRay.setOrigin(Matrixes.matrixMultVector(invMatrix, origin));
		invRay.setDirection(Matrixes.matrixMultVector(invMatrix, direction));
		ShadeRec newSr = geometricObject.hit(sr, invRay.getOrigin(), invRay.getDirection());
		if (newSr.hitsAnObject()) {
			newSr.setNormal(Matrixes.matrixMultVector(invTransMatrix, newSr.getNormal()));
			newSr.getNormal().normalize();
			if (geometricObject.getMaterial() != null) {
				setMaterial(geometricObject.getMaterial());
			}		
			if (!isTextureTransformed) {
				newSr.setLocalHitPoint(Vectors.plus(origin, Vectors.scale(direction, newSr.getT())));
			}
		}
		return newSr;
	}
	
	public void translate(double dx, double dy, double dz) {
		Matrix4d invTranslationMatrix = Matrixes.newIdenty4d();
		invTranslationMatrix.m03 = -dx;
		invTranslationMatrix.m13 = -dy;
		invTranslationMatrix.m23 = -dz;
		invMatrix.mul(invTranslationMatrix);
	}
	
	public void scale(double a, double b, double c) {
		Matrix4d invScaleMatrix = Matrixes.newIdenty4d();
		invScaleMatrix.m00 = 1 / a;
		invScaleMatrix.m11 = 1 / b;
		invScaleMatrix.m22 = 1 / c;
		invMatrix.mul(invScaleMatrix);
	}
	
	public void rotateX(double degrees) {
		Matrix4d invRotationXMatrix = Matrixes.newIdenty4d();
		invRotationXMatrix.m11 = Math.cos(Math.toRadians(degrees));
		invRotationXMatrix.m12 = Math.sin(Math.toRadians(degrees));
		invRotationXMatrix.m21 = -Math.sin(Math.toRadians(degrees));
		invRotationXMatrix.m22 = Math.cos(Math.toRadians(degrees));
		invMatrix.mul(invRotationXMatrix);
	}
	
	public void rotateY(double degrees) {
		Matrix4d invRotationXMatrix = Matrixes.newIdenty4d();
		invRotationXMatrix.m00 = Math.cos(Math.toRadians(degrees));
		invRotationXMatrix.m02 = -Math.sin(Math.toRadians(degrees));
		invRotationXMatrix.m20 = Math.sin(Math.toRadians(degrees));
		invRotationXMatrix.m22 = Math.cos(Math.toRadians(degrees));
		invMatrix.mul(invRotationXMatrix);
	}
	
	public void rotateZ(double degrees) {
		Matrix4d invRotationXMatrix = Matrixes.newIdenty4d();
		invRotationXMatrix.m00 = Math.cos(Math.toRadians(degrees));
		invRotationXMatrix.m01 = Math.sin(Math.toRadians(degrees));
		invRotationXMatrix.m10 = -Math.sin(Math.toRadians(degrees));
		invRotationXMatrix.m11 = Math.cos(Math.toRadians(degrees));
		invMatrix.mul(invRotationXMatrix);
	}

	@Override
	public double shadowHit(Ray ray) {
		// DO NOTHING
		return -1;
	}

	@Override
	public BoundingBox getBoundingBox() {
		return this.geometricObject.getBoundingBox();
	}

	@Override
	public Vector3d getCentroid() {
		return this.geometricObject.getCentroid();
	}
	
}
