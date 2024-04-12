package org.exceptions;

public class SalaryNotInRange extends RuntimeException{
    public SalaryNotInRange(String message) {
        super(message);
    }
}
