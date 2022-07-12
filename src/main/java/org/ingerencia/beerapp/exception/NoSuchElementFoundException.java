package org.ingerencia.beerapp.exception;

import org.springframework.http.HttpStatus;

public class NoSuchElementFoundException extends RuntimeException{
    private HttpStatus httpStatus;

    public NoSuchElementFoundException(){
    }

    public NoSuchElementFoundException(String message) {super(message);}

    public NoSuchElementFoundException(Throwable cause) {super(cause);}

    public NoSuchElementFoundException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public NoSuchElementFoundException(String message, Throwable cause){super(message, cause);}

    public NoSuchElementFoundException(String message, HttpStatus httpStatus, Throwable cause){
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){ return this.httpStatus;}
}
