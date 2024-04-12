package org.exceptions;

public class RoleNotAllowed extends RuntimeException{
    public RoleNotAllowed(String message){
        super(message);
    }
}
