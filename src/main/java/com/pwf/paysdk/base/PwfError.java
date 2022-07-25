package com.pwf.paysdk.base;

public class PwfError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PwfError(String message) {
        super(message);
    }
 
    public PwfError(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PwfError(Throwable cause) {
        super(cause);
    }
}