package com.automation;

public class TestAutomationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TestAutomationException(String message) {
        super(message);
    }

    public TestAutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}
