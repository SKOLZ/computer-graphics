package ar.edu.itba.gc.material;

import ar.edu.itba.gc.util.RGBColor;

class PerfectSpecular extends BRDF {

	private double kr;
	private RGBColor cr;

	PerfectSpecular(double kr, RGBColor cr) {
		super();
		this.kr = kr;
		this.cr = cr;
	}

	double getKr() {
		return kr;
	}

	RGBColor getCr() {
		return cr;
	}
	
}
