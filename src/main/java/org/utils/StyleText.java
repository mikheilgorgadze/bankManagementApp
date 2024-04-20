package org.utils;

import org.structs.Color;

public class StyleText {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    public static String colorCodeString(String string, Color color){
        switch (color){
            case RED -> {
                return RED + string + RESET;
            }
            case GREEN ->  {
                return GREEN + string + RESET;
            }
            case YELLOW -> {
                return YELLOW + string + RESET;
            }
            default -> {
                return string;
            }
        }
    }
}
