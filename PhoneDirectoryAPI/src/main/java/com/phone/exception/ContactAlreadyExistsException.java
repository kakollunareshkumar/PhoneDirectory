package com.phone.exception;

public class ContactAlreadyExistsException extends RuntimeException{

    public ContactAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
