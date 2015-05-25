package ar.edu.itba.gc.parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import ar.edu.itba.gc.parser.global.GlobalParser;
import ar.edu.itba.gc.parser.world.WorldParser;
import ar.edu.itba.gc.world.World;

public class Parser {

	public static void main(String[] args) throws IOException {
		parse("gilada.txt");
	}

	public static void parse(String path) throws IOException {
		GlobalParser globalParser = new GlobalParser();
		WorldParser worldParser = new WorldParser();
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

			globalParser.parse(global.toString());

			while (scanner.hasNext()) {
				line = scanner.nextLine();
				if (line.startsWith("WorldEnd"))
					break;
				world.append(line).append("\n");
			}
			
			worldParser.parse(world.toString());
		}

		World w = World.getInstance();

		scanner.close();
	}

}
