package ar.edu.itba.gc.parser.global;

public class Attribute {

	private String type;
	private String name;
	private String value;

	public Attribute(String type, String name, String value) {
		super();
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Attribute [type=" + type + ", name=" + name + ", value="
				+ value + "]";
	}

}
