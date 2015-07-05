package ar.edu.itba.gc.primitives.mesh;

import java.util.Arrays;

import javax.vecmath.Vector3d;

public class MeshData {

	private final int[] triindices;
	private final Vector3d[] vertices;
	private final Vector3d[] normals;
	private final double[] uvMap;

	public MeshData(final int[] triindices, final Vector3d[] vertices, final Vector3d[] normals, final double[] uvMap) {
		this.triindices = triindices;
		this.vertices = vertices;
		this.normals = normals;
		this.uvMap = uvMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(normals);
		result = prime * result + Arrays.hashCode(triindices);
		result = prime * result + Arrays.hashCode(vertices);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeshData other = (MeshData) obj;
		if (!Arrays.equals(normals, other.normals))
			return false;
		if (!Arrays.equals(triindices, other.triindices))
			return false;
		if (!Arrays.equals(vertices, other.vertices))
			return false;
		return true;
	}
}