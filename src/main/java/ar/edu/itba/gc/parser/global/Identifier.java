package ar.edu.itba.gc.parser.global;

import java.util.List;

import ar.edu.itba.gc.parser.global.builder.CameraBuilder;
import ar.edu.itba.gc.parser.global.builder.FilmBuilder;

import com.google.common.collect.Lists;

public class Identifier {

	private String name;
	private String type;
	private List<Attribute> attributes;
	private Transformation transformation;

	public Identifier(String name, String type) {
		super();
		this.name = name;
		this.type = type;
		this.attributes = Lists.newLinkedList();
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public Transformation getTransformation() {
		return transformation;
	}

	public void setTransformation(Transformation currentTransformation) {
		this.transformation = currentTransformation;
	}

	public void addAttribute(Attribute attr) {
		this.attributes.add(attr);
	}

	public void buildIdentifier() {
		switch (name) {
		case "Camera":
			new CameraBuilder(attributes, transformation).build();
		case "Film":
			new FilmBuilder(attributes).build();
		default:
			break;
		}
	}

}
