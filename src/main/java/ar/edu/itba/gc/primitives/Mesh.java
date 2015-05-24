package ar.edu.itba.gc.primitives;

import java.util.List;

import javax.vecmath.Vector3d;

public class Mesh {

	private List<Vector3d> vertices;
	private List<Vector3d> normals;
	private List<Double> u;
	private List<Double> v;
	private List<List<Integer>> vertexFaces;
	private int numVertices;
	private int numTriangles;

	public List<Vector3d> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vector3d> vertices) {
		this.vertices = vertices;
	}

	public List<Vector3d> getNormals() {
		return normals;
	}

	public void setNormals(List<Vector3d> normals) {
		this.normals = normals;
	}

	public List<Double> getU() {
		return u;
	}

	public void setU(List<Double> u) {
		this.u = u;
	}

	public List<Double> getV() {
		return v;
	}

	public void setV(List<Double> v) {
		this.v = v;
	}

	public List<List<Integer>> getVertexFaces() {
		return vertexFaces;
	}

	public void setVertexFaces(List<List<Integer>> vertexFaces) {
		this.vertexFaces = vertexFaces;
	}

	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	public int getNumTriangles() {
		return numTriangles;
	}

	public void setNumTriangles(int numTriangles) {
		this.numTriangles = numTriangles;
	}

}
