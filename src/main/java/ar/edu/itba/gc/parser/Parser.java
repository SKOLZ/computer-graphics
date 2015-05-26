package ar.edu.itba.gc.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import ar.edu.itba.gc.light.AmbientLight;
import ar.edu.itba.gc.parser.global.GlobalParser;
import ar.edu.itba.gc.parser.world.WorldParser;
import ar.edu.itba.gc.util.RGBColor;
import ar.edu.itba.gc.world.World;

public class Parser {

	private GlobalParser globalParser = new GlobalParser();
	private WorldParser worldParser = new WorldParser();

	public static void main(String[] args) throws IOException {
		new Parser().parse("gilada.txt");
	}

	public void parse(String path) throws IOException {
		World w = World.getInstance();

		w.vp.setPixelSize(1.0);
		w.vp.setSampleNum(10);
		w.vp.setMaxDepth(10);
		w.background = RGBColor.black();
		w.ambientLight = AmbientLight.black();

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
				if (line.startsWith("WorldEnd"))
					break;
				world.append(line).append("\n");
			}

			worldParser.parse(world.toString(), w);
		}

		w.renderScene();

		scanner.close();
	}

}
