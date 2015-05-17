package ar.edu.itba.gc;

import ar.edu.itba.gc.world.World;

public class Main {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		World w = new World();
		w.build();
		w.renderScene();
		System.out.println("Total time: " + (System.currentTimeMillis() - start) + "ms");
	}
	
}
