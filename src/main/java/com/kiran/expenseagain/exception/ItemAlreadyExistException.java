package com.kiran.expenseagain.exception;

public class ItemAlreadyExistException extends RuntimeException {

    public ItemAlreadyExistException(String message) {
        super(message);
    }
}
