package ar.edu.itba.gc.parser.global;

import java.util.List;

import javax.vecmath.Vector3d;

import com.google.common.collect.Lists;

class LookAtParser {

	private static LookAtParser instance = null;

	static LookAtParser instance() {
		if (instance == null)
			instance = new LookAtParser();
		return instance;
	}

	private LookAtParser() {
	}

	LookAt parse(String[] line) {
		if (line == null)
			return null;
		List<String> values = Lists.newLinkedList();
		for (int i = 1; i < line.length; i++) {
			values.add(line[i]);
		}
		return new LookAt(this.buildVectorFromStringList(values.subList(0, 3)),
				this.buildVectorFromStringList(values.subList(3, 6)),
				this.buildVectorFromStringList(values.subList(6, 9)));
	}

	private Vector3d buildVectorFromStringList(List<String> points) {
		if (points != null && points.size() == 3) {
			return new Vector3d(Double.valueOf(points.get(0)),
					Double.valueOf(points.get(1)),
					Double.valueOf(points.get(2)));
		}
		return null;
	}

}
