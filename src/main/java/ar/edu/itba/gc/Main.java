package ar.edu.itba.gc;

import ar.edu.itba.gc.world.World;

public class Main {
	
	public static void main(String[] args) {
		World w = new World();
		w.build();
		w.renderScene();
	}
	
}
