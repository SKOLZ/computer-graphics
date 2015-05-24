package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.Ray;
import ar.edu.itba.gc.util.ShadeRec;

public abstract class GeometricObject {

	public int count;
	private Material material;

	public GeometricObject(Material material) {
		this.material = material;
	}

	public GeometricObject() {
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public abstract ShadeRec hit(ShadeRec sr, Vector3d origin,
			Vector3d direction);

	public abstract double shadowHit(Ray ray);
	
	public abstract BoundingBox getBoundingBox();
	
	public abstract Vector3d getCentroid();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeometricObject other = (GeometricObject) obj;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		return true;
	}

}
