package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Constants;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;
import ar.edu.itba.gc.util.Vectors;

public abstract class MeshTriangle extends GeometricObject {

	private Mesh mesh;
	private int index0, index1, index2;
	private Vector3d normal;
	private double area;

	public MeshTriangle(Material material, Mesh mesh, int index0, int index1,
			int index2, Vector3d normal, double area) {
		super(material);
		this.mesh = mesh;
		this.index0 = index0;
		this.index1 = index1;
		this.index2 = index2;
		this.normal = normal;
		this.area = area;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public void setIndex0(int index0) {
		this.index0 = index0;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public void setIndex2(int index2) {
		this.index2 = index2;
	}

	public void setNormal(Vector3d normal) {
		this.normal = normal;
	}

	public void setArea(double area) {
		this.area = area;
	}

	protected Mesh getMesh() {
		return mesh;
	}

	protected int getIndex0() {
		return index0;
	}

	protected int getIndex1() {
		return index1;
	}

	protected int getIndex2() {
		return index2;
	}

	protected Vector3d getNormal() {
		return normal;
	}

	protected double getArea() {
		return area;
	}
	
	@Override
	public double shadowHit(Ray ray) {
		return calculateT(ray.getOrigin(), ray.getDirection(), null);
	}
	
	@Override
	public BoundingBox getBoundingBox() {
		Vector3d v0 = mesh.getVertices().get(getIndex0());
		Vector3d v1 = mesh.getVertices().get(getIndex1());
		Vector3d v2 = mesh.getVertices().get(getIndex2());

		double minX = Math.min(v0.x, Math.min(v1.x, v2.x)) - Constants.KEPS;
		double minY = Math.min(v0.y, Math.min(v1.y, v2.y)) - Constants.KEPS;
		double minZ = Math.min(v0.z, Math.min(v1.z, v2.z)) - Constants.KEPS;
		double maxX = Math.max(v0.x, Math.max(v1.x, v2.x)) + Constants.KEPS;
		double maxY = Math.max(v0.y, Math.max(v1.y, v2.y)) + Constants.KEPS;
		double maxZ = Math.max(v0.z, Math.max(v1.z, v2.z)) + Constants.KEPS;

		return new BoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
	}
	
	protected double calculateT(Vector3d origin, Vector3d direction, ShadeRec sr) {
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
		if (sr != null) {
			sr.setLocalHitPoint(Vectors.plus(origin, Vectors.scale(direction, t)));
			if (getMesh().getU() != null && getMesh().getV() != null) {
				sr.setU(interpolateU(beta, gamma));
				sr.setV(interpolateV(beta, gamma));
			}			
		}
		return t;
	}

	protected double interpolateV(double beta, double gamma) {
		return (1 - beta - gamma) * getMesh().getV().get(getIndex0()) + beta
				* getMesh().getV().get(getIndex1()) + gamma * getMesh().getV().get(getIndex2());
	}

	protected double interpolateU(double beta, double gamma) {
		return (1 - beta - gamma) * getMesh().getU().get(getIndex0()) + beta
				* getMesh().getU().get(getIndex1()) + gamma * getMesh().getU().get(getIndex2());
	}
	
	
	
	
}
