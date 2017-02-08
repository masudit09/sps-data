package com.javacodegeeks.examples.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Util {
	final static Integer STRENGTH = 8;
    final static PasswordEncoder ENCODER = new BCryptPasswordEncoder(STRENGTH);
	
	public static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s",
				"(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}
	
	public static PasswordEncoder getEncoder(){
		return ENCODER;
	}
}
