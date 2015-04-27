package ar.edu.itba.gc.world;

import ar.edu.itba.gc.sampler.MultiJittered;
import ar.edu.itba.gc.sampler.Regular;
import ar.edu.itba.gc.sampler.Sampler;

public class ViewPlane {
	
	private int horizontalRes;
	private int verticalRes;
	private double pixelSize;
	private double gamma;
	private double gammaInverse;
	private int sampleNum;
	private Sampler sampler;
	private int maxDepth;
	
	public Sampler getSampler() {
		return sampler;
	}
	
	public void setSampler(Sampler s) {
		this.sampler = s;
		this.sampleNum = s.getSampleNum();
	}

	public int getSampleNum() {
		return sampleNum;
	}

	public void setSampleNum(int sampleNum) {
		this.sampleNum = sampleNum;
		if (sampleNum > 1) {
			sampler = new MultiJittered(sampleNum);
		} else {
			sampler = new Regular(1);
		}
	}

	public int getHorizontalRes() {
		return horizontalRes;
	}
	
	public void setHorizontalRes(int horizontalRes) {
		this.horizontalRes = horizontalRes;
	}
	
	public int getVerticalRes() {
		return verticalRes;
	}
	
	public void setVerticalRes(int verticalRes) {
		this.verticalRes = verticalRes;
	}
	
	public double getPixelSize() {
		return pixelSize;
	}
	
	public void setPixelSize(double pixelSize) {
		this.pixelSize = pixelSize;
	}
	
	public double getGamma() {
		return gamma;
	}
	
	public void setGamma(double gamma) {
		this.gamma = gamma;
		this.gammaInverse = 1 / gamma;
	}

	public double getGammaInverse() {
		return gammaInverse;
	}
	
	public void setGammaInverse(double gammaInverse) {
		this.gammaInverse = gammaInverse;
		this.gamma = 1 / gammaInverse;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

}
