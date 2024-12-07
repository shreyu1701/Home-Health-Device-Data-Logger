package com.zodiac.homehealthdevicedatalogger.Util;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class PasswordUtils {
	private static final int SALT_LENGTH = 16;

	// Validate the password by comparing the entered password with the stored hash
	public static boolean validatePassword(String password, String storedPasswordHash) {
		String[] parts = storedPasswordHash.split(":");
		if (parts.length != 2) {
			return false;  // Invalid hash format
		}
		String salt = parts[0];  // Extract salt
		String hashedPassword = parts[1];  // Extract hashed password
		String computedHash = hashPassword(password, salt);  // Hash the input password with the stored salt
		return computedHash.equals(hashedPassword);  // Compare the computed hash with the stored hash
	}

	// Hash password using a given salt
	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] saltedPassword = (salt + password).getBytes(StandardCharsets.UTF_8);
			byte[] hash = digest.digest(saltedPassword);
			return bytesToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error hashing password", e);
		}
	}

	// Convert bytes to hex string
	private static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			hexString.append(String.format("%02x", b));
		}
		return hexString.toString();
	}

	// Generate a random salt for password hashing
	public static String generateSalt() {
		byte[] salt = new byte[SALT_LENGTH];
		new SecureRandom().nextBytes(salt);
		return bytesToHex(salt);
	}
}


