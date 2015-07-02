package ar.edu.itba.gc.primitives;

import javax.vecmath.Vector3d;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.util.ShadeRec;

public abstract class EmisiveObject extends GeometricObject {
	
	protected EmisiveObject(Material material) {
		super(material);
	}

	public abstract Vector3d getNormal(Vector3d samplePoint);
	
	public abstract Vector3d sample();
	
	public abstract double pdf(ShadeRec sr);
}
