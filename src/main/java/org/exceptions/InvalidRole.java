package org.exceptions;

public class InvalidRole extends RuntimeException{
    public InvalidRole(String message){
        super(message);
    }
}
