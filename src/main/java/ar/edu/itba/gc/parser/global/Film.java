package ar.edu.itba.gc.parser.global;

class Film {

	private int hres = 640;
	private int vres = 480;
	private double gamma = 1.0;

	int getHres() {
		return hres;
	}

	void setHres(int hres) {
		this.hres = hres;
	}

	int getVres() {
		return vres;
	}

	void setVres(int vres) {
		this.vres = vres;
	}

	double getGamma() {
		return gamma;
	}

	void setGamma(double gamma) {
		this.gamma = gamma;
	}

}
