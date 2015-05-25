package ar.edu.itba.gc.parser.global;

import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;

public class GlobalParser {

	public void parse(String s) {
		Transformation currentTransformation = null;
		Scanner scanner = new Scanner(s);

		while (scanner.hasNext()) {
			String nextLine;
			do {
				nextLine = scanner.nextLine();
			} while (nextLine.startsWith("#") || nextLine.isEmpty());
			String[] commands = nextLine.split(" ");

			if (Transformation.isTransformation(commands[0])) {
				List<String> values = Lists.newLinkedList();
				for (int i = 1; i < commands.length; i++) {
					values.add(commands[i]);
				}
				currentTransformation = new Transformation(commands[0], values);
			} else {
				Identifier identifier = new Identifier(commands[0],
						commands[1].replace("\"", ""));
				// Parse attributes
				if (scanner.hasNext()) {
					while (scanner.hasNext()) {
						String att = scanner.nextLine();
						if (att != null && att.startsWith("\t")) {
							att = att.trim();
							int idx = att.indexOf("[");
							String[] type = att.substring(0, idx - 1).split(
									"\"")[1].split(" ");
							identifier
									.addAttribute(new Attribute(type[0],
											type[1], att.substring(idx + 1,
													att.length() - 1).replace(
													"\"", "")));
						} else {
							break;
						}
					}
				}
				// Set transformation for identifier (if exists)
				if (currentTransformation != null) {
					identifier.setTransformation(currentTransformation);
					currentTransformation = null;
				}
				identifier.buildIdentifier();
			}
		}
		scanner.close();
	}

}
