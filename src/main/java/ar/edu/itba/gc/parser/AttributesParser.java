package ar.edu.itba.gc.parser;

import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;

public class AttributesParser {

	public static AttributesParser instance() {
		return new AttributesParser();
	}

	private AttributesParser() {
	}

	public List<Attribute> parse(Scanner scanner) {
		List<Attribute> attributes = Lists.newLinkedList();
		while (scanner.hasNext()) {
			String attr = scanner.nextLine();
			if (attr != null && attr.startsWith("\t")) {
				attr = attr.trim();
				int idx = attr.indexOf("[");
				String[] type = attr.substring(0, idx - 1).split("\"")[1].split(" ");
				attributes.add(
						new Attribute(type[0], type[1], attr.substring(idx + 1, attr.length() - 1).replace("\"", "")));
			} else {
				break;
			}
		}
		return attributes;
	}

}
