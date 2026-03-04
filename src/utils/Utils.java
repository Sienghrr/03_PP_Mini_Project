package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Utils {
    //colors
    public static final String RESET  = "\u001B[0m";
    public static final String BOLD   = "\u001B[1m";
    public static final String CYAN   = "\u001B[36m";
    public static final String GREEN  = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED    = "\u001B[31m";
    public static final String BLUE   = "\u001B[34m";

    private static final Scanner sc = new Scanner(System.in);

    private static String prompt(String label) {
        System.out.print(BOLD + label + " : " + RESET);
        return sc.nextLine();
    }
    public static void println(String s) { System.out.println(s); }

    public static String promptOrDefault(String label, String defaultVal) {
        String input = prompt(label + " [" + defaultVal + "]").trim();
        return input.isEmpty() ? defaultVal : input;
    }

    public static double promptDoubleOrDefault(String label, double defaultVal) {
        while (true) {
            String input = prompt(label + " [" + defaultVal + "]").trim();
            if (input.isEmpty()) return defaultVal;
            try { return Double.parseDouble(input); }
            catch (NumberFormatException e) { println(RED + "  Please enter a valid number." + RESET); }
        }
    }

    public static int promptIntOrDefault(String label, int defaultVal) {
        while (true) {
            String input = prompt(label + " [" + defaultVal + "]").trim();
            if (input.isEmpty()) return defaultVal;
            try { return Integer.parseInt(input); }
            catch (NumberFormatException e) { println(RED + "  Please enter a valid integer." + RESET); }
        }
    }


    public static String getString(String label){
        System.out.print(label);
        return sc.nextLine().trim();
    }
    public static double getDouble(String label) {
        while (true) {
            try {
                return Double.parseDouble(getString(label));
            } catch (NumberFormatException e) {
                System.out.println("INVALID! ENTER A NUMBER.");
            }
        }
    }
    public static int getInt(String label) {
        while (true) {
            try {
                return Integer.parseInt(getString(label));
            } catch (NumberFormatException e) {
                System.out.println("INVALID! ENTER A WHOLE NUMBER.");
            }
        }
    }

    public static Number getNumber(String label) {
        while (true) {
            try {
                String input = getString(label);
                if (input.contains(".")) {
                    return Double.parseDouble(input);
                } else {
                    return Integer.parseInt(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("INVALID INPUT! PLEASE ENTER NUMBER ONLY.");
            }
        }
    }


}
