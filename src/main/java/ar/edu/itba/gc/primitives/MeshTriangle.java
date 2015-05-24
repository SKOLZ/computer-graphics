//package ar.edu.itba.gc.primitives;
//
//import javax.vecmath.Vector3d;
//
//import ar.edu.itba.gc.material.Material;
//import ar.edu.itba.gc.util.Constants;
//import ar.edu.itba.gc.util.Ray;
//import ar.edu.itba.gc.util.ShadeRec;
//import ar.edu.itba.gc.util.Vectors;
//
//public class MeshTriangle extends GeometricObject {
//
//	private Mesh mesh;
//	private int index0, index1, index2;
//	private Vector3d normal;
//	private double area;
//
//	public MeshTriangle(Material material) {
//		super(material);
//	}
//
//	@Override
//	public ShadeRec hit(ShadeRec sr, Vector3d origin, Vector3d direction) {
//		Vector3d v0 = mesh.getVertices().get(index0);
//		Vector3d v1 = mesh.getVertices().get(index1);
//		Vector3d v2 = mesh.getVertices().get(index2);
//
//		double a = v0.x - v1.x, b = v0.x - v2.x, c = direction.x, d = v0.x
//				- origin.x;
//		double e = v0.y - v1.y, f = v0.y - v2.y, g = direction.y, h = v0.y
//				- origin.y;
//		double i = v0.z - v1.z, j = v0.z - v2.z, k = direction.z, l = v0.z
//				- origin.z;
//
//		double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
//		double q = g * i - e * k, s = e * j - f * i;
//
//		double inv_denom = 1.0 / (a * m + b * q + c * s);
//
//		double e1 = d * m - b * n - c * p;
//		double beta = e1 * inv_denom;
//
//		if (beta < 0.0)
//			return sr;
//
//		double r = e * l - h * i;
//		double e2 = a * n + d * q + c * r;
//		double gamma = e2 * inv_denom;
//
//		if (gamma < 0.0)
//			return sr;
//
//		if (beta + gamma > 1.0)
//			return sr;
//
//		double e3 = a * p - b * r + d * s;
//		double t = e3 * inv_denom;
//
//		if (t < Constants.KEPS)
//			return sr;
//
//		Vector3d hitPoint = Vectors.plus(origin, Vectors.scale(direction, t));
//
//		return new ShadeRec(true, sr.getWorld(), this.getMaterial(), normal, hitPoint,
//				hitPoint, direction, t, sr.getDepth());
//	}
//
//	@Override
//	public double shadowHit(Ray ray) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public BoundingBox getBoundingBox() {
//		double delta = 0.0001; // to avoid degenerate bounding boxes
//
//		Vector3d v1 = mesh.getVertices().get(index0);
//		Vector3d v2 = mesh.getVertices().get(index1);
//		Vector3d v3 = mesh.getVertices().get(index2);
//
//		return new BoundingBox(Math.min(Math.min(v1.x, v2.x), v3.x) - delta,
//				Math.max(Math.max(v1.x, v2.x), v3.x) + delta, Math.min(
//						Math.min(v1.y, v2.y), v3.y)
//						- delta, Math.max(Math.max(v1.y, v2.y), v3.y) + delta,
//				Math.min(Math.min(v1.z, v2.z), v3.z) - delta, Math.max(
//						Math.max(v1.z, v2.z), v3.z)
//						+ delta);
//	}
//
//	@Override
//	public Vector3d getCentroid() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void setMesh(Mesh mesh) {
//		this.mesh = mesh;
//	}
//
//	public void setIndex0(int index0) {
//		this.index0 = index0;
//	}
//
//	public void setIndex1(int index1) {
//		this.index1 = index1;
//	}
//
//	public void setIndex2(int index2) {
//		this.index2 = index2;
//	}
//
//	public void setNormal(Vector3d normal) {
//		this.normal = normal;
//	}
//
//	public void setArea(double area) {
//		this.area = area;
//	}
//
//}
