package ar.edu.itba.gc.parser.world;

import java.util.Map;
import java.util.Scanner;

import ar.edu.itba.gc.light.AmbientLight;
import ar.edu.itba.gc.light.Light;
import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.parser.world.light.LightParser;
import ar.edu.itba.gc.parser.world.material.MaterialParser;
import ar.edu.itba.gc.parser.world.shape.ShapeParser;
import ar.edu.itba.gc.parser.world.texture.TextureParser;
import ar.edu.itba.gc.parser.world.transform.TransformParser;
import ar.edu.itba.gc.parser.world.transform.Transformation;
import ar.edu.itba.gc.primitives.GeometricObject;
import ar.edu.itba.gc.primitives.Instance;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.world.World;

import com.google.common.collect.Maps;

public class WorldParser {

	private World world;
	private Material currentMaterial;
	private Transformation transform;

	private Map<String, Material> materials = Maps.newHashMap();
	private Map<String, Texture> textures = Maps.newHashMap();

	private LightParser lightParser = LightParser.instance();
	private ShapeParser shapeParser = ShapeParser.instance();
	private MaterialParser materialParser = MaterialParser.instance();
	private TextureParser textureParser = TextureParser.instance();
	private TransformParser transformParser = TransformParser.instance();

	public void parse(String s, World world) {
		this.world = world;

		Scanner scanner = new Scanner(s);
		this.parse(scanner);
		scanner.close();
	}

	public void parse(Scanner scanner) {
		while (scanner.hasNext()) {
			String line = scanner.nextLine().trim();
			String[] splittedLine = line.split(" ");
			switch (splittedLine[0]) {
			case "AttributeBegin":
				this.parse(scanner);
				break;
			case "AttributeEnd":
				return;
			case "Texture":
				parseTexture(scanner, splittedLine);
				break;
			case "MakeNamedMaterial":
				parseMaterial(scanner, splittedLine);
				break;
			case "Shape":
				parseShape(scanner, splittedLine);
				break;
			case "NamedMaterial":
				parseMaterial(splittedLine);
				break;
			case "LightSource":
				parseLight(scanner, splittedLine);
				break;
			case "Transform":
				transform = transformParser.parse(line.replace("[", "")
						.replace("]", "").split(" "));
				break;
			case "TransformBegin":
				transform = transformParser.parse(scanner);
				break;
			default:
				break;
			}
		}
	}

	private void parseLight(Scanner scanner, String[] splittedLine) {
		Light light = this.lightParser.parse(scanner,
				splittedLine[1].replaceAll("\"", ""));
		if (light instanceof AmbientLight)
			world.ambientLight = light;
		else
			world.addLight(light);
	}

	private void parseMaterial(Scanner scanner, String[] line) {
		String name = line[1].replaceAll("\"", "");
		if (materials.containsKey(name))
			throw new IllegalArgumentException("Material " + name
					+ " already exists!");
		materials.put(name, materialParser.parse(scanner, textures, world));
	}

	private void parseTexture(Scanner scanner, String[] line) {
		String name = line[1].replaceAll("\"", "");
		String type = line[3].replaceAll("\"", "");
		if (textures.containsKey(name))
			throw new IllegalArgumentException("Texture " + name
					+ " already exists!");
		textures.put(name, textureParser.parse(scanner, type));
	}

	private void parseMaterial(String[] line) {
		if (currentMaterial != null)
			throw new IllegalStateException("Material already setted");
		currentMaterial = materials.get(line[1].replaceAll("\"", ""));
	}

	private void parseShape(Scanner scanner, String[] line) {
		if (currentMaterial == null)
			throw new IllegalStateException("Material for Shape not setted");
		GeometricObject obj = shapeParser.parse(scanner,
				line[1].replaceAll("\"", ""));
		obj.setMaterial(currentMaterial);
		Instance instance = new Instance(obj);
		if (transform != null) {
			instance.transform(this.transform.getMatrix());
			transform = null;
		}
		world.addObject(instance);
		currentMaterial = null;
	}

}
