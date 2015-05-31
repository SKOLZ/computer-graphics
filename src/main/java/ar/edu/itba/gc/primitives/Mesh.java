package ar.edu.itba.gc.primitives;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.KDNode;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Mesh extends GeometricObject {

	private Vector3d[] vertices;
	private Vector3d[] normals;
	private double[] u;
	private double[] v;
	private boolean smooth;

	private Map<Integer, List<MeshTriangle>> faces;

	private List<MeshTriangle> triangles;

	private KDNode<MeshTriangle> tree;

	public Mesh(final Vector3d[] vertices, final Vector3d[] normals,
			final int[] indices, final double[] u, final double[] v,
			final boolean smooth) {
		this.vertices = vertices;
		this.normals = normals;
		this.u = u;
		this.v = v;
		this.smooth = smooth;

		this.triangles = Lists.newArrayList();
		this.faces = Maps.newHashMap();

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
			if (smooth) {
				t = new SmoothMeshTriangle(this.getMaterial(), this, v1, v2, v3);
			} else {
				t = new FlatMeshTriangle(this.getMaterial(), this, v1, v2, v3);
			}

			facesV1.add(t);
			facesV2.add(t);
			facesV3.add(t);

			this.addTriangle(t);
		}

		this.preprocess();
	}

	@Override
	public void setMaterial(Material material) {
		super.setMaterial(material);
		for (MeshTriangle t : triangles) {
			t.setMaterial(material);
		}
	}

	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin,
			Vector3d direction) {
		return tree.hit(sr, tmin, origin, direction);
	}

	@Override
	public double shadowHit(Ray ray) {
		// return tree.shadowHit(ray);
		return 0;
	}

	@Override
	public BoundingBox getBoundingBox() {
		return tree.getBoundingBox();
	}

	@Override
	public Vector3d getCentroid() {
		return null;
	}

	public Vector3d[] getVertices() {
		return vertices;
	}

	public double[] getU() {
		return u;
	}

	public double[] getV() {
		return v;
	}

	public Vector3d[] getNormals() {
		return normals;
	}

	private void preprocess() {
		tree = KDNode.build(this.triangles);

		if (smooth) {
			this.computeMeshNormals();
		}
	}

	private void addTriangle(MeshTriangle t) {
		this.triangles.add(t);
	}

	private void computeMeshNormals() {
		normals = new Vector3d[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			Vector3d normal = new Vector3d(0, 0, 0);
			List<MeshTriangle> ts = this.faces.get(i);
			if (ts != null) {
				for (MeshTriangle meshTriangle : ts) {
					Vector3d n = meshTriangle.getNormal();
					normal = Vectors.plus(normal, n);
				}
			}
			normals[i] = normal;
		}
	}

}
