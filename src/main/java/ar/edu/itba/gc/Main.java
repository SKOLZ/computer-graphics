package ar.edu.itba.gc;

import java.io.IOException;

import ar.edu.itba.gc.parser.Parser;
import ar.edu.itba.gc.world.World;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String outputPath = null;
		String inputPath = null;
		boolean displayTime = false;
		int aaSamples = -1;
		int renderAmount = 1;
		int depth = 1;
		
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-o":
				outputPath = args[i + 1];
				break;
			case "-i":
				inputPath = args[i + 1];
				break;
			case "-time":
				displayTime = true;
				break;
			case "-aa":
				try {
					aaSamples = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) {
					aaSamples = -1;
				}
				break;
			case "-benchmark":
				try {
					renderAmount = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) { 
					renderAmount = -1;
				}
			case "-d":
				try {
					depth = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) { 
					depth = -1;
				}
				break;
			default:
				break;
			}
		}
		if (aaSamples == -1 || inputPath == null) {
			System.out.println("Antialiasing or input path not specified");
			return;
		}
		if (outputPath == null) {
			String[] splitInput = inputPath.split("\\.");
			outputPath = splitInput[0] + ".png";
		}
		
		Parser parser = new Parser();
		parser.parse(inputPath, aaSamples, depth);
		World w = World.getInstance();
		long start = System.currentTimeMillis();
		for (int i = 0; i < renderAmount; i++) {
			w.renderScene(outputPath);			
		}
		if (displayTime) {
			System.out.println("Total time: "
					+ (System.currentTimeMillis() - start) + "ms");			
		}
		
		
//		-- WITHOUT PARSER --
//		long start = System.currentTimeMillis();
//		World w = World.getInstance();
//		w.build();
//		w.renderScene();
//		System.out.println("Total time: "
//				+ (System.currentTimeMillis() - start) + "ms");
	}

}
