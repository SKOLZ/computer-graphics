package ar.edu.itba.gc.primitives;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Matrixes;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;

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
		updateMatrixes();
		
	}
	
	public Instance(GeometricObject geometricObject, Matrix4d matrix) {
		super();
		this.geometricObject = geometricObject;
		this.matrix = matrix;
		updateMatrixes();
	}
	
	@Override
	public ShadeRec hit(ShadeRec sr, Vector3d origin, Vector3d direction) {
		Ray invRay = new Ray(origin, direction);
		invRay.setOrigin(Matrixes.matrixMultPoint(invMatrix, origin));
		invRay.setDirection(Matrixes.matrixMultVector(invMatrix, direction));
		ShadeRec newSr = geometricObject.hit(sr, invRay.getOrigin(), invRay.getDirection());
		if (newSr.hitsAnObject()) {
			newSr.setNormal(Matrixes.matrixMultVector(invTransMatrix, newSr.getNormal()));
			newSr.getNormal().normalize();
			if (geometricObject.getMaterial() != null) {
				setMaterial(geometricObject.getMaterial());
			}		
//			if (!isTextureTransformed) {
//				newSr.setLocalHitPoint(Vectors.plus(origin, Vectors.scale(direction, newSr.getT())));
//			}
		}
		return newSr;
	}
	
	public void translate(double dx, double dy, double dz) {
		Matrix4d translationMatrix = Matrixes.newIdenty4d();
		translationMatrix.m03 = dx;
		translationMatrix.m13 = dy;
		translationMatrix.m23 = dz;
		translationMatrix.mul(matrix);
		matrix = translationMatrix;
		updateMatrixes();
	}
	
	public void scale(double a, double b, double c) {
		Matrix4d scaleMatrix = Matrixes.newIdenty4d();
		scaleMatrix.m00 = a;
		scaleMatrix.m11 = b;
		scaleMatrix.m22 = c;
		scaleMatrix.mul(matrix);
		matrix = scaleMatrix;
		updateMatrixes();
	}
	
	public void rotateX(double degrees) {
		Matrix4d rotationXMatrix = Matrixes.newIdenty4d();
		rotationXMatrix.m11 = Math.cos(Math.toRadians(degrees));
		rotationXMatrix.m12 = -Math.sin(Math.toRadians(degrees));
		rotationXMatrix.m21 = Math.sin(Math.toRadians(degrees));
		rotationXMatrix.m22 = Math.cos(Math.toRadians(degrees));
		rotationXMatrix.mul(matrix);
		matrix = rotationXMatrix;
		updateMatrixes();
	}
	
	public void rotateY(double degrees) {
		Matrix4d rotationYMatrix = Matrixes.newIdenty4d();
		rotationYMatrix.m00 = Math.cos(Math.toRadians(degrees));
		rotationYMatrix.m02 = Math.sin(Math.toRadians(degrees));
		rotationYMatrix.m20 = -Math.sin(Math.toRadians(degrees));
		rotationYMatrix.m22 = Math.cos(Math.toRadians(degrees));
		rotationYMatrix.mul(matrix);
		matrix = rotationYMatrix;
		updateMatrixes();
	}
	
	public void rotateZ(double degrees) {
		Matrix4d rotationZMatrix = Matrixes.newIdenty4d();
		rotationZMatrix.m00 = Math.cos(Math.toRadians(degrees));
		rotationZMatrix.m01 = -Math.sin(Math.toRadians(degrees));
		rotationZMatrix.m10 = Math.sin(Math.toRadians(degrees));
		rotationZMatrix.m11 = Math.cos(Math.toRadians(degrees));
		rotationZMatrix.mul(matrix);
		matrix = rotationZMatrix;
		updateMatrixes();
	}
	
	private void updateMatrixes() {
		this.invMatrix = Matrixes.inverse(matrix);
		this.invTransMatrix = Matrixes.transpose(invMatrix);
	}

	@Override
	public double shadowHit(Ray ray) {
		Ray invRay = new Ray(ray.getOrigin(), ray.getDirection());
		invRay.setOrigin(Matrixes.matrixMultPoint(invMatrix, ray.getOrigin()));
		invRay.setDirection(Matrixes.matrixMultVector(invMatrix, ray.getDirection()));
		return geometricObject.shadowHit(invRay);
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
