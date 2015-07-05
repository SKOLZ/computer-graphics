package ar.edu.itba.gc.primitives.mesh;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.primitives.BoundingBox;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Vectors;

public abstract class MeshTriangle extends GeometricObject {

	public Mesh mesh;

	public final int index0;
	public final int index1;
	public final int index2;

	public final Vector3d p0;
	public final Vector3d p1;
	public final Vector3d p2;

	protected Vector3d normal;
	protected double area;

	public MeshTriangle(final int index0, final int index1, final int index2, final Mesh mesh, final boolean reverse) {
		// triangles should be counter-clockwise
		this.index0 = index0;
		this.index1 = index1;
		this.index2 = index2;
		this.mesh = mesh;
		this.computeNormal(reverse);

		p0 = mesh.vertices[index0];
		p1 = mesh.vertices[index1];
		p2 = mesh.vertices[index2];
	}

	private void computeNormal(boolean reverse) {
		normal = new Vector3d();
		normal.cross(Vectors.sub(mesh.vertices[index1], mesh.vertices[index0]),
				Vectors.sub(mesh.vertices[index2], mesh.vertices[index0]));

		normal.normalize();
		// if (reverse)
		// normal = normal.
	}

	protected double interpolateU(final double beta, final double gamma) {
		return (1 - beta - gamma) * mesh.u[index0] + beta * mesh.u[index1] + gamma * mesh.u[index2];
	}

	protected double interpolateV(final double beta, final double gamma) {
		return (1 - beta - gamma) * mesh.v[index0] + beta * mesh.v[index1] + gamma * mesh.v[index2];
	}

	@Override
	public BoundingBox getBoundingBox() {
		Vector3d v0 = mesh.vertices[index0];
		Vector3d v1 = mesh.vertices[index1];
		Vector3d v2 = mesh.vertices[index2];

		double minX = Math.min(v0.x, Math.min(v1.x, v2.x)) - Constants.KEPS;
		double minY = Math.min(v0.y, Math.min(v1.y, v2.y)) - Constants.KEPS;
		double minZ = Math.min(v0.z, Math.min(v1.z, v2.z)) - Constants.KEPS;
		double maxX = Math.max(v0.x, Math.max(v1.x, v2.x)) + Constants.KEPS;
		double maxY = Math.max(v0.y, Math.max(v1.y, v2.y)) + Constants.KEPS;
		double maxZ = Math.max(v0.z, Math.max(v1.z, v2.z)) + Constants.KEPS;

		return new BoundingBox(minX, maxY, minZ, maxX, minY, maxZ);
	}

	@Override
	public Vector3d getCentroid() {
		Vector3d v0 = mesh.vertices[index0];
		Vector3d v1 = mesh.vertices[index1];
		Vector3d v2 = mesh.vertices[index2];

		double x = (v0.x + v1.x + v2.x) / 3;
		double y = (v0.y + v1.y + v2.y) / 3;
		double z = (v0.z + v1.z + v2.z) / 3;
		return new Vector3d(x, y, z);
	}

}
