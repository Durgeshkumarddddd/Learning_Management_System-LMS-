package utils;


import java.util.Scanner;

/**
 * Console helper centralizes input/read operations.
 */
public class ConsoleHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int readInt(String prompt, int defaultIfInvalid) {
        System.out.print(prompt);
        try {
            String line = scanner.nextLine();
            return Integer.parseInt(line.trim());
        } catch (Exception e) {
            return defaultIfInvalid;
        }
    }
}

