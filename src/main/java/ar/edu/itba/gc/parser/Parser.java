package ar.edu.itba.gc.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import ar.edu.itba.gc.light.AmbientLight;
import ar.edu.itba.gc.parser.global.GlobalParser;
import ar.edu.itba.gc.parser.world.WorldParser;
import ar.edu.itba.gc.tracer.PathTracer;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.world.World;

public class Parser {

	private GlobalParser globalParser = new GlobalParser();
	private WorldParser worldParser = new WorldParser();

	public void parse(String path, int aaSamples, int depth, boolean usePathTracer) throws IOException {
		if (path == null)
			throw new IllegalArgumentException("Input file not defined");

		final String cwd = path.substring(0, path.lastIndexOf("/") + 1);

		World w = World.getInstance();
		w.vp.setPixelSize(1.0);
		w.vp.setSampleNum(aaSamples);
		w.vp.setMaxDepth(depth);
		w.background = RGBColor.black();
		if (usePathTracer) {
			w.setTracer(new PathTracer(w));
		}

		Scanner scanner = new Scanner(Paths.get(path), "UTF-8");
		if (scanner.hasNext()) {
			String line = null;
			StringBuilder global = new StringBuilder();
			StringBuilder world = new StringBuilder();
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				if (line.startsWith("WorldBegin"))
					break;
				global.append(line).append("\n");
			}

			globalParser.parse(global.toString(), w);

			while (scanner.hasNext()) {
				line = scanner.nextLine();
				if (!line.startsWith(" ") && !line.startsWith("\t\t") && !line.equals("\t")) {
					if (line.startsWith("WorldEnd"))
						break;
					if (line.startsWith("Include")) {
						String includedPath = line.split(" ")[1].replaceAll("\"", "");
						if (!includedPath.startsWith("/")) {
							includedPath = new StringBuilder().append(cwd).append(includedPath).toString();
						}
						Scanner auxScanner = new Scanner(Paths.get(includedPath), "UTF-8");
						while (auxScanner.hasNext()) {
							world.append(auxScanner.nextLine()).append("\n");
						}
						auxScanner.close();
					}
					world.append(line).append("\n");
				}
			}

			worldParser.parse(world.toString(), w);
		}

		if (w.ambientLight == null)
			w.ambientLight = AmbientLight.black();
		scanner.close();
	}

}
