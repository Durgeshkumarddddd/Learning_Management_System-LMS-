package utils;


/**
 * Very small validation utilities.
 */
public class InputValidator {
    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isValidUsername(String username) {
        if (isBlank(username)) return false;
        return username.matches("^[A-Za-z0-9_.-]{3,20}$");
    }

    public static boolean isValidPassword(String password) {
        if (isBlank(password)) return false;
        return password.length() >= 4; // simple policy for demo
    }
}

