package org.exceptions;

public class NoSuchUserType extends RuntimeException {
    public NoSuchUserType(String message){
        super(message);
    }
}
