package utils;

import java.util.regex.Pattern;

import static utils.Utils.*;

public class Validate {

    // regex validate
    private static final Pattern ACCEPT_STRING = Pattern.compile("^[a-zA-Z\\s]+$");

    //validate menu option
    public static String validate(String input){
        return loopUntilValid(input,
                "ONLY ACCEPT STRING",
                "=> Choose option : ");
    }
    //validate menu option
    public static String validateProductName(String input){
        return loopUntilValid(input,
                "ONLY ACCEPT STRING",
                " Enter Product Name : ");
    }

    private static String loopUntilValid (String input , String message , String prompt){
        while (!Validate.ACCEPT_STRING.matcher(input).matches()){
            System.out.println(BOLD + RED + message + RESET);
            input = Utils.getString(prompt);
        }
        return input;
    }
}
