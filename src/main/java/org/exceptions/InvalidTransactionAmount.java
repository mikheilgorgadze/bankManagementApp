package org.exceptions;

public class InvalidTransactionAmount extends  RuntimeException{
    public InvalidTransactionAmount(String message){
        super(message);
    }
}
