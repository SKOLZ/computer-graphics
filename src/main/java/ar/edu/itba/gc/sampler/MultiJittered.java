package ar.edu.itba.gc.sampler;

import javax.vecmath.Vector2d;

public class MultiJittered extends Sampler {

	public MultiJittered(int numSamples) {
		super(numSamples);
	}

	@Override
	public void generateSamples() {
		int n = (int) Math.sqrt(getSampleNum());
		double subcellWidth = 1.0 / ((double)getSampleNum());
		
		for (int i = 0; i < getSampleNum() * getNumSets(); i++) {
			setSample(i, new Vector2d());
		}
		
		for (int i = 0; i < getNumSets(); i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					getSamples()[j * n + k + i * getSampleNum()].x = (j * n + k) * subcellWidth + Math.random() * subcellWidth;
					getSamples()[j * n + k + i * getSampleNum()].y = (k * n + j) * subcellWidth + Math.random() * subcellWidth;
				}
			}
		}
		
		for (int i = 0; i < getNumSets(); i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					int m = (int) (Math.random() * (n-1-j) + j);
					double t = getSamples()[j * n + k + i * getSampleNum()].x;
					getSamples()[j * n + k + i * getSampleNum()].x = getSamples()[j * n + m + i * getSampleNum()].x;
					getSamples()[j * n + m + i * getSampleNum()].x = t;
				}
			}
		}

		for (int i = 0; i < getNumSets(); i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					int m = (int) (Math.random() * (n-1-j) + j);
					double t = getSamples()[k * n + j + i * getSampleNum()].y;
					getSamples()[k * n + j + i * getSampleNum()].y = getSamples()[m * n + j + i * getSampleNum()].y;
					getSamples()[m * n + j + i * getSampleNum()].y = t;
				}
			}
		}
		
	}

}
