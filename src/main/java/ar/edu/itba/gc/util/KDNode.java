//package ar.edu.itba.gc.util;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.vecmath.Vector3d;
//
//import ar.edu.itba.gc.primitives.BoundingBox;
//import ar.edu.itba.gc.primitives.GeometricObject;
//
//public class KDNode<T extends GeometricObject> {
//	
//	public int count;
//
//	public static <T extends GeometricObject> KDNode<T> build(List<T> objs) {
//		long start = System.currentTimeMillis();
//
//		KDNode<T> node = buildInternal(objs);
//
//		System.out.println("Tree build time: " + (System.currentTimeMillis() - start) + "ms");
//		return node;
//	}
//
//	private static <T extends GeometricObject> KDNode<T> buildInternal(
//			List<T> objs) {
//		KDNode<T> node = new KDNode<T>();
//		node.objs = objs;
//		node.left = null;
//		node.right = null;
//		node.bbox = new BoundingBox();
//
//		if (objs == null || objs.isEmpty()) {
//			return node;
//		}
//
//		if (objs.size() == 1) {
//			node.bbox = objs.get(0).getBoundingBox();
//			node.left = new KDNode<T>();
//			node.right = new KDNode<T>();
//			return node;
//		}
//
//		node.bbox = objs.get(0).getBoundingBox();
//
//		for (int i = 1; i < objs.size(); i++) {
//			node.bbox.expand(objs.get(i).getBoundingBox());
//		}
//
//		Vector3d midpt = new Vector3d(0, 0, 0);
//		for (T obj : objs) {
//			midpt = Vectors.plus(midpt,
//					Vectors.scale(obj.getCentroid(), 1 / objs.size()));
//		}
//
//		List<T> leftObjs = new LinkedList<T>();
//		List<T> rightObjs = new LinkedList<T>();
//		Axis axis = node.bbox.getLongestAxis();
//		for (T obj : objs) {
//			switch (axis) {
//			case X:
//				if (midpt.x >= obj.getCentroid().x)
//					rightObjs.add(obj);
//				else
//					leftObjs.add(obj);
//				break;
//			case Y:
//				if (midpt.y >= obj.getCentroid().y)
//					rightObjs.add(obj);
//				else
//					leftObjs.add(obj);
//				break;
//			case Z:
//				if (midpt.z >= obj.getCentroid().z)
//					rightObjs.add(obj);
//				else
//					leftObjs.add(obj);
//				break;
//			}
//		}
//
//		if (leftObjs.size() == 0 && rightObjs.size() > 0)
//			leftObjs = rightObjs;
//		if (rightObjs.size() == 0 && leftObjs.size() > 0)
//			rightObjs = leftObjs;
//
//		int matches = 0;
//		for (T l : leftObjs) {
//			for (T r : rightObjs) {
//				if (l.equals(r)) {
//					matches++;
//				}
//			}
//		}
//		if (((float) matches / leftObjs.size() < 0.5)
//				&& ((float) matches / rightObjs.size() < 0.5)) {
//			node.left = KDNode.buildInternal(leftObjs);
//			node.right = KDNode.buildInternal(rightObjs);
//		} else {
//			node.left = new KDNode<T>();
//			node.right = new KDNode<T>();
//		}
//		return node;
//	}
//
//	private BoundingBox bbox;
//	private KDNode<T> left;
//	private KDNode<T> right;
//	private List<T> objs;
//
//	private KDNode() {
//		super();
//		this.objs = new LinkedList<T>();
//	}
//
//	public ShadeRec hit(ShadeRec sr, Vector3d origin, Vector3d direction) {
//		if (this.bbox.hit(origin, direction)) {
//			if (!this.left.objs.isEmpty() || !this.right.objs.isEmpty()) {
//				ShadeRec srLeft = this.left.hit(sr, origin, direction);
//				if (srLeft.hitsAnObject())
//					return srLeft;
//				ShadeRec srRight = this.right.hit(sr, origin, direction);
//				if (srRight.hitsAnObject())
//					return srRight;
////				if (srLeft.hitsAnObject()) {
////					if (srRight.hitsAnObject()
////							&& srRight.getT() < srLeft.getT()) {
////						return srRight;
////					}
////					return srLeft;
////				} else if (srRight.hitsAnObject()) {
////					return srRight;
////				}
//			} else {
//				boolean hits = false;
//				ShadeRec objSr = sr;
//				for (T obj : this.objs) {
//					objSr = obj.hit(objSr, origin, direction);
//					if (objSr.hitsAnObject()) {
//						hits = true;
//					}
//				}
//				if (hits) {
//					return objSr;
//				}
//			}
//		}
//		return sr;
//	}
//
//	public List<T> getObjects() {
//		return this.objs;
//	}
//
//}
