package com.db.crud.course.exception;

public class ObjectsDontMatchException extends RuntimeException {
    public ObjectsDontMatchException(String message) {
        super(message);
    }
}
