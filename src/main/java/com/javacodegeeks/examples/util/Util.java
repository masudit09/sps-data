package com.javacodegeeks.examples.util;

import com.javacodegeeks.examples.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

	public static String getCurrentUsername() {
		String username = null;
		if(getCurrentPrincipal() !=null){
			Object principal = getCurrentPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
		}
		return username;
	}

	public static User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}


	private static Object getCurrentPrincipal() {
		return getAuthentication() == null ? null : getAuthentication().getPrincipal();
	}

	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
