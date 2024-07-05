package com.example.demo.utils;

import org.mindrot.jbcrypt.BCrypt;
public interface Utilitarios {
	public static String extraerHash(String passwordInput) {
		return BCrypt.hashpw(passwordInput, BCrypt.gensalt());
	}
	
	
	public static boolean checkPassword(String passwordInput, String hashPassword) {
		return BCrypt.checkpw(passwordInput, hashPassword);
	}

}
