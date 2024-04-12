package org.exceptions;

public class NotEnoughPermission extends RuntimeException{
    public NotEnoughPermission(String message) {
        super(message);
    }
}
