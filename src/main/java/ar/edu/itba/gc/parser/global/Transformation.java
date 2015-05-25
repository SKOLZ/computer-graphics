package ar.edu.itba.gc.parser.global;

import java.util.List;

public class Transformation {

	private String name;
	private List<String> values;

	public Transformation(String name, List<String> values) {
		super();
		this.name = name;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public List<String> getValues() {
		return values;
	}

	public static boolean isTransformation(String string) {
		if (string != null && string.equals("LookAt")) {
			return true;
		}
		return false;
	}

}
