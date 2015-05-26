package ar.edu.itba.gc.parser.global;

import java.util.List;
import java.util.Scanner;

import ar.edu.itba.gc.parser.Attribute;
import ar.edu.itba.gc.parser.AttributesParser;

class FilmParser {

	private static FilmParser instance = null;

	static FilmParser instance() {
		if (instance == null)
			instance = new FilmParser();
		return instance;
	}

	private FilmParser() {
	}

	private AttributesParser attributesParser = AttributesParser.instance();

	Film parse(Scanner scanner) {
		List<Attribute> attributes = attributesParser.parse(scanner);
		Film film = new Film();
		for (Attribute a : attributes) {
			switch (a.getName()) {
			case "xresolution":
				film.setHres(Integer.parseInt(a.getValue()));
				break;
			case "yresolution":
				film.setVres(Integer.parseInt(a.getValue()));
				break;
			case "gamma":
				film.setGamma(Double.parseDouble(a.getValue()));
				break;
			default:
				break;
			}
		}
		return film;
	}

}
