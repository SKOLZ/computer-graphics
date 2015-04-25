package ar.edu.itba.gc.sampler;

import javax.vecmath.Vector2d;

public class Sampler {

	private int sampleNum;
	private long count;
	private int numSets;
	private int jump;
	private Vector2d[] samples;

	public Sampler(int sampleNum) {
		this.sampleNum = sampleNum;
	}

	public int getSampleNum() {
		return sampleNum;
	}

	public void setSampleNum(int sampleNum) {
		this.sampleNum = sampleNum;
	}
	
	public Vector2d sampleUnitSquare() {	
		if (count % sampleNum == 0) {
			jump = (int) ((Math.random() % numSets) * sampleNum);
		}
		return samples[(int) (jump + count++ % sampleNum)];
	}
	
	
}
