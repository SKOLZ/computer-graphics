package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class FlatMeshTriangle extends MeshTriangle {

	public FlatMeshTriangle(Material material, Mesh mesh, int index0,
			int index1, int index2, Vector3d normal, double area) {
		super(material, mesh, index0, index1, index2, normal, area);
	}

	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin,
			Vector3d direction) {
		double t = calculateT(origin, direction, sr);
		if (t == -1) return t;
		sr.setNormal(getNormal());
		return t;
	}

	@Override
	public Vector3d getCentroid() {
		// TODO joti decime que onda esto!
		return null;
	}
}
