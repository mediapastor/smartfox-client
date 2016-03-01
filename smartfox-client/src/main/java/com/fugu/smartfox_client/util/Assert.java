package com.fugu.smartfox_client.util;

public class Assert {
	
	public static void isSingleChar(String str) {
		if (str.toCharArray().length != 1) {
			throw new IllegalArgumentException("String must be a single char.");
		}
	}
	
}
