package org.exceptions;

public class PermissionNotFound extends RuntimeException {
    public PermissionNotFound(String message) {
        super(message);
    }
}
