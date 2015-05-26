package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public class SmoothMeshTriangle extends MeshTriangle {

	public SmoothMeshTriangle(Material material, Mesh mesh, int index0,
			int index1, int index2, Vector3d normal, double area) {
		super(material, mesh, index0, index1, index2, normal, area);
	}

	@Override
	public double hit(ShadeRec sr, double tmin, Vector3d origin,
			Vector3d direction) {
		if (!getBoundingBox().hit(origin, direction)) {
			return -1;
		}
		
		Vector3d v0 = getMesh().getVertices().get(getIndex0());
		Vector3d v1 = getMesh().getVertices().get(getIndex1());
		Vector3d v2 = getMesh().getVertices().get(getIndex2());

		double a = v0.x - v1.x, b = v0.x - v2.x, c = direction.x, d = v0.x
				- origin.x;
		double e = v0.y - v1.y, f = v0.y - v2.y, g = direction.y, h = v0.y
				- origin.y;
		double i = v0.z - v1.z, j = v0.z - v2.z, k = direction.z, l = v0.z
				- origin.z;

		double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
		double q = g * i - e * k, s = e * j - f * i;

		double inv_denom = 1.0 / (a * m + b * q + c * s);

		double e1 = d * m - b * n - c * p;
		double beta = e1 * inv_denom;

		if (beta < 0.0) {
			return -1;
		}
		double r = e * l - h * i;
		double e2 = a * n + d * q + c * r;
		double gamma = e2 * inv_denom;

		if (gamma < 0.0 || beta + gamma > 1.0){
			return -1;
		}

		double e3 = a * p - b * r + d * s;
		double t = e3 * inv_denom;

		if (t < Constants.KEPS) {
			return -1;
		}
		sr.setNormal(interpolateNormal(beta, gamma));
		
		sr.setLocalHitPoint(Vectors.plus(origin, Vectors.scale(direction, t)));	
		
		if (getMesh().getU() != null && getMesh().getV() != null) {
			sr.setU(interpolateU(beta, gamma));
			sr.setV(interpolateV(beta, gamma));
		}
		return t;
	}

	private Vector3d interpolateNormal(double beta, double gamma) {
		Vector3d n1 = getMesh().getNormals().get(getIndex0());
		double n1x = n1.x * (1 - beta - gamma);
		double n1y = n1.y * (1 - beta - gamma);
		double n1z = n1.z * (1 - beta - gamma);
		
		Vector3d n2 = getMesh().getNormals().get(getIndex1());
		double n2x = n2.x * (beta);
		double n2y = n2.y * (beta);
		double n2z = n2.z * (beta);
		
		Vector3d n3 = getMesh().getNormals().get(getIndex2());
		double n3x = n3.x * (gamma);
		double n3y = n3.y * (gamma);
		double n3z = n3.z * (gamma);

		Vector3d interpolatedNormal = new Vector3d(n1x + n2x + n3x, n1y + n2y + n3y, n1z + n2z + n3z);
		interpolatedNormal.normalize();
		
		return interpolatedNormal;
	}

	@Override
	public Vector3d getCentroid() {
		// TODO joti que hacemos con estoooooooo///////////////////////////////////////////////////////////
		return null;
	}

}
