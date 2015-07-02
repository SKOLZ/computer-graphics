package ar.edu.itba.gc;

import java.io.IOException;

import ar.edu.itba.gc.parser.Parser;
import ar.edu.itba.gc.world.World;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String outputPath = null;
		String inputPath = null;
		boolean displayTime = false;
		boolean usePathTracer = false;
		int samples = -1;
		int renderAmount = 1;
		int depth = 1;
		int traceDepth = -1;
		int ptPixelSamples = -1;
		
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
					samples = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) {
					samples = -1;
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
			case "-pathtracer":
				usePathTracer = true;
				break;
			case "-tr":
				try {
					traceDepth = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) { 
					depth = -1;
				}
				break;
			case "-s":
				try {
					ptPixelSamples = Integer.parseInt(args[i + 1]);
				} catch (NumberFormatException e) { 
					ptPixelSamples = -1;
				}
				break;				
			default:
				break;
			}
		}
		if (usePathTracer && (ptPixelSamples == -1 || traceDepth == -1)) {
			System.out.println("pixel samples or tracedepth not specified for raytracer");
			return;
		}
		if (samples == -1 || inputPath == null) {
			System.out.println("Antialiasing or input path not specified");
			return;
		}
		if (outputPath == null) {
			String[] splitInput = inputPath.split("\\.");
			outputPath = splitInput[0] + ".png";
		}
		
		Parser parser = new Parser();
		if(usePathTracer) {
			samples = ptPixelSamples;
			depth = traceDepth;
		}
		parser.parse(inputPath, samples, depth, usePathTracer);
		World w = World.getInstance();
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < renderAmount; i++) {
			w.renderScene(outputPath);			
		}
		if (displayTime) {
			System.out.println("Total time: "
					+ (System.currentTimeMillis() - start) + "ms");			
		}
	}

}
