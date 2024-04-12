package org.exceptions;

public class AccountLimitReached extends RuntimeException{
    public AccountLimitReached(String message){
        super(message);
    }
}
