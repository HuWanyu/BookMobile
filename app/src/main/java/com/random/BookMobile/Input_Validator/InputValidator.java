package com.random.BookMobile.Input_Validator;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{5,15}$");

    public static boolean ValidatePasswordInput(String str){
        return str.matches("\\S");
    }

    public static boolean ValidateUserNameInput(String str){
      //  return str.matches("[a-zA-Z0-9]*");
        return USERNAME_PATTERN.matcher(str).matches();
    }

    public static boolean ValidateEmailInput(String str){
       // return str.matches("\"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\"");
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches();
    }
}
