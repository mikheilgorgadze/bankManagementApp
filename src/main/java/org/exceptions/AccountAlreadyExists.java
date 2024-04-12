package org.exceptions;

public class AccountAlreadyExists extends RuntimeException{
    public AccountAlreadyExists(String message) {
        super(message);
    }
}
