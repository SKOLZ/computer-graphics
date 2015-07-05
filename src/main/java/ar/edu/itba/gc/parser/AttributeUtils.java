package ar.edu.itba.gc.parser;

public class AttributeUtils {

	public static boolean isString(String value) {
		if (value == null)
			return false;
		return !isArray(value) && !isDouble(value);
	}

	public static boolean isDouble(String value) {
		if (value == null)
			return false;
		try {
			Double.valueOf(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isArray(String value) {
		if (value == null)
			return false;
		return value.split(" ").length > 1;
	}

	public static double[] getDoubleArray(String value) {
		if (!isArray(value)) {
			return null;
		}
		try {
			String[] rawValues = value.split(" ");
			return new double[] { Double.valueOf(rawValues[0]), Double.valueOf(rawValues[1]),
					Double.valueOf(rawValues[2]) };
		} catch (Exception e) {
			return null;
		}
	}

}
