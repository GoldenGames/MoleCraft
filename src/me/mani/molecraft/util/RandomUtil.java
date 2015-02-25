package me.mani.molecraft.util;

public class RandomUtil {

	public static int getRandomInteger(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
	
	public static boolean getRandomBoolean() {
		return Math.random() < 0.5f;
	}
	
}
