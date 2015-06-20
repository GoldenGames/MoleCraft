package me.mani.molecraft.util;

public class StringUtils {
	
	public static String generateHealthString(double health, String prefix, String suffix) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		for (int i = 0; i < 10; i++) {
			if (Math.ceil(health / 2) == i)
				buffer.append(suffix);
			buffer.append("\u2764");
		}
		return buffer.toString();
	}

}
