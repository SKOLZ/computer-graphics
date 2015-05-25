package ar.edu.itba.gc.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.vecmath.Color3f;

import ar.edu.itba.gc.material.Material;
import ar.edu.itba.gc.material.Matte;
import ar.edu.itba.gc.material.Phong;
import ar.edu.itba.gc.material.Reflective;
import ar.edu.itba.gc.material.Transparent;
import ar.edu.itba.gc.texture.ConstantColor;
import ar.edu.itba.gc.texture.Texture;
import ar.edu.itba.gc.util.RGBColor;

public class LuxParser {

	private Path materialPath;
	private Path geometryPath;
	private Path cameraLightsPath;
	
	private Map<String, Material> materials; 

	public LuxParser(String materialPath, String geometryPath, String cameraLightsPath) {
		this.materialPath = Paths.get(materialPath);
		this.geometryPath = Paths.get(materialPath);
		this.cameraLightsPath = Paths.get(materialPath);
		materials = new HashMap<>();
	}

	public final void processLines() throws IOException {
	    try {
	    	Scanner scanner =  new Scanner(materialPath, "UTF-8");
	    	processMaterials(scanner);
	    	scanner =  new Scanner(geometryPath, "UTF-8");
	    	processGeometry();
	    	scanner =  new Scanner(cameraLightsPath, "UTF-8");
	    	processLights();
	    	scanner =  new Scanner(cameraLightsPath, "UTF-8");
	    	processCamera();
	    }
	}
	
	private void getArgument(String line, Map<String,Object> arguments) {
		String[] splitedLine = line.split("\"");
		String type = splitedLine[1].split(" ")[0];
		String name = splitedLine[1].split(" ")[1];
		String val = line.substring(6 + type.length() + name.length(),line.length()-1);
		
		switch (type) {
			case "bool":
				if(val.equals("true"))
					arguments.put(name, true);
				else
					arguments.put(name, false);
				break;
			case "float":
				arguments.put(name, Double.parseDouble(val));
				break;
			case "string":	
				arguments.put(name, val.substring(1, val.length()-1));
				break;
			case "color":
				String[] rgb = val.split(" ");
				arguments.put(name, new RGBColor(Double.parseDouble(rgb[0]), Double.parseDouble(rgb[1]), Double.parseDouble(rgb[2])));
				break;
			case "integer":
				List<Integer> ints = new ArrayList<Integer>();
				for (String s : val.split(" ")) {
					ints.add(Integer.parseInt(s));
				}
				arguments.put(name, ints);
				break;
			case "point":
				List<Double> point = new ArrayList<Double>();
				for (String s : val.split(" ")) {
					point.add(Double.parseDouble(s));
				}
				arguments.put(name, point);
				break;
			case "normal":
				List<Double> normal = new ArrayList<Double>();
				for (String s : val.split(" ")) {
					normal.add(Double.parseDouble(s));
				}
				arguments.put(name, normal);
				break;
			default:
				break;
		}
	}
	
	private void processMaterials(Scanner scanner) {
		String materialName = null;
		Map<String, Object> arguments = new HashMap<>();
		
		while (scanner.hasNextLine()){
			String line = scanner.nextLine();
			
			while ((materialName == null && line.isEmpty()) || line.startsWith("#") ) {
				line = scanner.nextLine();;
			}
			
			if (line.startsWith("MakeNamedMaterial")){
				int firstQuote= line.indexOf('"') + 1;
				int lastQuote= line.lastIndexOf('"');
				materialName = line.substring(firstQuote,lastQuote);
			} 
			else if (line.startsWith("\t")) {
				getArgument(line, arguments);
			} 
			else if (line.startsWith("Texture")) {
				// TODO
			} 
			else if (line.isEmpty() && !materialName.isEmpty()) {
					materials.put(materialName, getMaterial(arguments));
					materialName = "";
					arguments.clear();
			}
		}
	}
	
	private Material getMaterial(Map<String, Object> arguments) {
		Material material = null;
		String type = (String)arguments.get("type");
		switch (type) {
			case "matte":
				Object cd = arguments.get("Kd");
				if (cd instanceof RGBColor) {
					material = new Matte(null, 0 , 1, new ConstantColor((RGBColor)cd), null);
				} else {
					material = new Matte(null, 0 , 1, (Texture)cd, null);
				}
				break;
			case "mirror":
				cd = arguments.get("Kr");
				if (cd instanceof RGBColor) {
					material = new Reflective(null, 0, 0, 0, 100, 0.75, new ConstantColor((RGBColor)cd), null, (RGBColor)cd);
				} else {
					material = new Reflective(null, 0, 0, 0, 100, 0.75, (Texture)cd, null, RGBColor.white());
				}
				break;
			case "glass":
				RGBColor kr = (RGBColor)arguments.get("Kr");
				RGBColor kt = (RGBColor)arguments.get("Kt");
				material = new Transparent(null, 0.25, 0.2, 0, 2000, new ConstantColor(kr), null, 1.5, 0.05, 0.95, kt);
				break;
			case "metal2":
				float uroughness = (float)arguments.get("uroughness");
				Texture fresnel = (Texture)arguments.get("fresnel");
				material = new Phong(null,0.25, 0.6, 0.2, 1/uroughness, fresnel, null);
				break;
			default:
				break;
		}
		return material;
	}
	
	
}
