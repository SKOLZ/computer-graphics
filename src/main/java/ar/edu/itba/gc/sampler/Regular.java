package ar.edu.itba.gc.sampler;

import javax.vecmath.Vector2d;

public class Regular extends Sampler {

	public Regular(int sampleNum) {
		super(sampleNum);
	}

	@Override
	public void generateSamples() {
		int n  = (int) Math.sqrt(getSampleNum());
		int m = 0;
		for (int i = 0; i < getNumSets(); i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					setSample(m, new Vector2d((k + 0.5) / n, (j + 0.5)/n));
					m++;
				}
			}
		}
	}

}
