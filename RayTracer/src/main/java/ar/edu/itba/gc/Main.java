package ar.edu.itba.gc;

import ar.edu.itba.gc.primitives.World;

public class Main {
	
	public static void main(String[] args) {
		World w = new World();
		w.build();
		w.renderScene();
	}
	
}
