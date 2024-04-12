package org.exceptions;

public class ExceptionHandling {
    public static void handleException(Exception e, String identifier){
        if (e instanceof UserNotFoundException){
            System.out.println("User with username: " + identifier + " not found");
        } else if (e instanceof AccountNotFound) {
            System.out.println(e.getMessage());
        } else if (e instanceof NotEnoughPermission) {
            System.out.println(e.getMessage());
        } else {
            System.out.println("Error occurred");
        }
    }


    public static void handleException(Exception e){
        if (e instanceof UserNotFoundException){
            System.out.println(e.getMessage());
        } else if (e instanceof AccountNotFound) {
            System.out.println(e.getMessage());
        } else if (e instanceof NotEnoughPermission) {
            System.out.println(e.getMessage());
        } else {
            System.out.println("Error occurred");
        }
    }
}
