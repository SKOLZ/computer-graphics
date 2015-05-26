package ar.edu.itba.gc.parser.world.transform;

import java.util.List;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

import com.google.common.collect.Lists;

import ar.edu.itba.gc.util.Matrixes;

public class Transformation {
	private Matrix4d matrix;

	private List<Scaling> scalings;
	private List<Rotation> rotations;
	private List<Vector3d> translations;

	Transformation(Matrix4d matrix) {
		this.matrix = matrix;
	}

	Transformation() {
		this.scalings = Lists.newLinkedList();
		this.rotations = Lists.newLinkedList();
		this.translations = Lists.newLinkedList();
	}

	public Matrix4d getMatrix() {
		if (matrix == null) {
			matrix = Matrixes.newIdenty4d();
			for (Scaling s : scalings) {
				matrix = this.scale(matrix, s.getX(), s.getY(), s.getZ());
			}
			for (Rotation r : rotations) {
				switch (r.getAxis()) {
				case "X":
					matrix = this.rotateX(matrix, r.getDegrees());
					break;
				case "Y":
					matrix = this.rotateY(matrix, r.getDegrees());
					break;
				case "Z":
					matrix = this.rotateZ(matrix, r.getDegrees());
					break;
				default:
					break;
				}
			}
			for (Vector3d t : translations) {
				matrix = this.translate(matrix, t.x, t.y, t.z);
			}
		}
		return matrix;
	}

	void addScaling(Scaling scale) {
		this.scalings.add(scale);
	}

	void addRotation(Rotation rotation) {
		this.rotations.add(rotation);
	}

	void addTransaltion(Vector3d translation) {
		this.translations.add(translation);
	}

	private Matrix4d translate(Matrix4d matrix, double dx, double dy, double dz) {
		Matrix4d translationMatrix = Matrixes.newIdenty4d();
		translationMatrix.m03 = dx;
		translationMatrix.m13 = dy;
		translationMatrix.m23 = dz;
		translationMatrix.mul(matrix);
		return translationMatrix;
	}

	private Matrix4d scale(Matrix4d matrix, double a, double b, double c) {
		Matrix4d scaleMatrix = Matrixes.newIdenty4d();
		scaleMatrix.m00 = a;
		scaleMatrix.m11 = b;
		scaleMatrix.m22 = c;
		scaleMatrix.mul(matrix);
		return scaleMatrix;
	}

	private Matrix4d rotateX(Matrix4d matrix, double degrees) {
		Matrix4d rotationXMatrix = Matrixes.newIdenty4d();
		rotationXMatrix.m11 = Math.cos(Math.toRadians(degrees));
		rotationXMatrix.m12 = -Math.sin(Math.toRadians(degrees));
		rotationXMatrix.m21 = Math.sin(Math.toRadians(degrees));
		rotationXMatrix.m22 = Math.cos(Math.toRadians(degrees));
		rotationXMatrix.mul(matrix);
		return rotationXMatrix;
	}

	private Matrix4d rotateY(Matrix4d matrix, double degrees) {
		Matrix4d rotationYMatrix = Matrixes.newIdenty4d();
		rotationYMatrix.m00 = Math.cos(Math.toRadians(degrees));
		rotationYMatrix.m02 = Math.sin(Math.toRadians(degrees));
		rotationYMatrix.m20 = -Math.sin(Math.toRadians(degrees));
		rotationYMatrix.m22 = Math.cos(Math.toRadians(degrees));
		rotationYMatrix.mul(matrix);
		return rotationYMatrix;
	}

	private Matrix4d rotateZ(Matrix4d matrix, double degrees) {
		Matrix4d rotationZMatrix = Matrixes.newIdenty4d();
		rotationZMatrix.m00 = Math.cos(Math.toRadians(degrees));
		rotationZMatrix.m01 = -Math.sin(Math.toRadians(degrees));
		rotationZMatrix.m10 = Math.sin(Math.toRadians(degrees));
		rotationZMatrix.m11 = Math.cos(Math.toRadians(degrees));
		rotationZMatrix.mul(matrix);
		return rotationZMatrix;
	}

}
