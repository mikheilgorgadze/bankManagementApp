package org.exceptions;

import org.structs.Color;
import org.utils.StyleText;

public class ErrorHandler {
    public static void handleException(Exception e, String identifier){
        if (e instanceof UserNotFoundException){
            printError("User with username: " + identifier + " not found");
        } else if (e instanceof AccountNotFound) {
            printError(e.getMessage());
        } else if (e instanceof NotEnoughPermission) {
            printError(e.getMessage());
        } else {
            printError("Error occurred");
        }
    }


    public static void handleException(Exception e){
        String errorText = StyleText.colorCodeString("Error occurred", Color.RED);
        if (e instanceof UserNotFoundException){
            printError(e.getMessage());
        } else if (e instanceof AccountNotFound) {
            printError(e.getMessage());
        } else if (e instanceof NotEnoughPermission) {
            printError(e.getMessage());
        } else {
            printError("Error occurred");
        }
    }

    public static void printError(String string){
        System.out.println(StyleText.colorCodeString(string, Color.RED));
    }

    public static void printWarning(String string){
        System.out.println(StyleText.colorCodeString(string, Color.YELLOW));
    }

    public static void printSuccess(String string){
        System.out.println(StyleText.colorCodeString(string, Color.GREEN));
    }
}
