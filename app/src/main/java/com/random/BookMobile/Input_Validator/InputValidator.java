package com.random.BookMobile.Input_Validator;

public class InputValidator {
    public static boolean ValidatePasswordInput(String str){
        return str.matches("\\S");
    }

    public static boolean ValidateUserNameInput(String str){
        return str.matches("[a-zA-Z0-9]*");
    }

    public static boolean ValidateEmailInput(String str){
        return str.matches("\"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\"");
    }
}
