package ar.edu.itba.gc.primitives.mesh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.primitives.BoundingBox;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.util.KDNode;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class Mesh extends GeometricObject {

	public Vector3d[] vertices;
	public double[] u;
	public double[] v;
	public int[] indices;
	public Vector3d[] normals; // average normal at each vertex
	public Map<Integer, List<MeshTriangle>> faces; // the faces shared by each
													// vertex, we need it for
													// smooth mesh
	public List<MeshTriangle> triangles;

	public int numVertices;
	public int numTriangles;
	private KDNode<MeshTriangle> tree;

	private double minX = Double.POSITIVE_INFINITY;
	private double maxX = Double.NEGATIVE_INFINITY;
	private double minY = Double.POSITIVE_INFINITY;
	private double maxY = Double.NEGATIVE_INFINITY;
	private double minZ = Double.POSITIVE_INFINITY;
	private double maxZ = Double.NEGATIVE_INFINITY;
	private boolean needsSmoothing;

	int verticesAmount;
	int trianglesAmount;

	private boolean preprocessed = false;

	public Mesh(final Vector3d[] vertices, final Vector3d[] normals, final int[] indices, final double[] u,
			final double[] v, final boolean needsSmoothing) {
		this.u = u;
		this.v = v;

		this.vertices = vertices;
		this.normals = normals;
		this.indices = indices;
		this.needsSmoothing = needsSmoothing;
		this.triangles = new ArrayList<MeshTriangle>();
		this.faces = new HashMap<Integer, List<MeshTriangle>>();

		final int iterations = indices.length / 3;
		for (int i = 0; i < iterations; i++) {
			Integer v1 = indices[i * 3 + 2];
			Integer v2 = indices[i * 3 + 1];
			Integer v3 = indices[i * 3];

			List<MeshTriangle> facesV1 = this.faces.get(v1);
			List<MeshTriangle> facesV2 = this.faces.get(v2);
			List<MeshTriangle> facesV3 = this.faces.get(v3);

			if (facesV1 == null) {
				facesV1 = new ArrayList<MeshTriangle>();
				this.faces.put(v1, facesV1);
			}
			if (facesV2 == null) {
				facesV2 = new ArrayList<MeshTriangle>();
				this.faces.put(v2, facesV2);
			}
			if (facesV3 == null) {
				facesV3 = new ArrayList<MeshTriangle>();
				this.faces.put(v3, facesV3);
			}
			MeshTriangle t;
			if (needsSmoothing) {
				t = new SmoothMeshTriangle(v1, v2, v3, this, true);
			} else {
				t = new FlatMeshTriangle(v1, v2, v3, this, true);
			}

			facesV1.add(t);
			facesV2.add(t);
			facesV3.add(t);

			this.addTriangle(t);
		}
		preprocess();
	}

	public void preprocess() {
		if (!preprocessed) {
			numTriangles += triangles.size();
			this.numVertices = vertices.length;

			tree = KDNode.build(this.triangles);

			if (needsSmoothing) {
				this.computeMeshNormals();
			}

			preprocessed = true;
		}
	}

	private void addTriangle(final MeshTriangle t) {
		BoundingBox b = t.getBoundingBox();

		minX = Math.min(minX, b.x0);
		minZ = Math.min(minZ, b.z0);
		minY = Math.min(minY, b.y1);
		maxX = Math.max(maxX, b.x1);
		maxY = Math.max(maxY, b.y0);
		maxZ = Math.max(maxZ, b.z1);

		this.triangles.add(t);
	}

	private void computeMeshNormals() {
		if (normals == null) {
			normals = new Vector3d[vertices.length];
			for (int i = 0; i < vertices.length; i++) {
				Vector3d normal = new Vector3d(0, 0, 0);
				List<MeshTriangle> ts = this.faces.get(i);
				if (ts != null) {
					for (MeshTriangle meshTriangle : ts) {
						Vector3d n = meshTriangle.normal;
						normal = Vectors.plus(normal, n);
					}
				}
				normals[i] = normal;
			}
		}
	}

	@Override
	public void setMaterial(Material material) {
		super.setMaterial(material);

		for (GeometricObject t : triangles) {
			t.setMaterial(material);
		}
	}

	@Override
	public BoundingBox getBoundingBox() {
		return new BoundingBox(minX, maxY, minZ, maxX, minY, maxZ);
	}

	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin, Vector3d direction) {
		return tree.hit(sr, tmin, origin, direction);
	}

	@Override
	public double shadowHit(Ray ray) {
		return tree.shadowHit(ray);
	}

	@Override
	public Vector3d getCentroid() {
		// TODO Auto-generated method stub
		return null;
	}

}
