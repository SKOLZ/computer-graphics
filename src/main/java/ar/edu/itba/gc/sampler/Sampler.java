package ar.edu.itba.gc.sampler;

import java.util.Random;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

public abstract class Sampler {

	private int sampleNum;
	private long count;
	private int numSets;
	private int jump;
	private Vector2d[] samples;
	private int[] shuffledIndices;

	public Sampler() {
		this(1, 83);
	}

	public Sampler(int sampleNum) {
		this(sampleNum, 83);
	}
	
	public Sampler(int sampleNum, int numSets) {
		this.sampleNum = sampleNum;
		this.numSets = numSets;
		this.count = 0;
		this.jump = 0;
		this.samples = new Vector2d[sampleNum * numSets];
		generateSamples();
		setupShuffledIndices();
	}

	public int getSampleNum() {
		return sampleNum;
	}

	public void setSampleNum(int sampleNum) {
		this.sampleNum = sampleNum;
	}

	public Vector2d sampleUnitSquare() {
		if (count % sampleNum == 0) {
			jump = (int) ((new Random().nextInt(32767) % numSets) * sampleNum);
		}
		return samples[jump
				+ shuffledIndices[(int) (jump + count++ % sampleNum)]];
	}

	public void setupShuffledIndices() {
		shuffledIndices = new int[sampleNum * numSets];
		int[] indices = new int[sampleNum];
		int j = 0;
		for (int i = 0; i < sampleNum; i++) {
			indices[i] = i;
		}
		for (int i = 0; i < numSets; i++) {
			ShuffleArray(indices);
			while (j < numSets * sampleNum) {
				shuffledIndices[j]  = indices[j % sampleNum];
				j++;
			}
		}
	}
	
	private void ShuffleArray(int[] array) {
		int index, temp;
		Random random = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
	}
	
	protected void setSample(int index, Vector2d value) {
		samples[index] = value;
	}
	
	public long getCount() {
		return count;
	}

	public int getNumSets() {
		return numSets;
	}

	public int getJump() {
		return jump;
	}

	public Vector2d[] getSamples() {
		return samples;
	}

	public int[] getShuffledIndices() {
		return shuffledIndices;
	}

	public abstract void generateSamples();

	public Vector3d sampleHemisphere() {
		// TODO MAMUUUUU
		return null;
	}

	public void mapSamplesToHemisphere(double i) {
		// TODO Mamuuuuuuu
	}

}