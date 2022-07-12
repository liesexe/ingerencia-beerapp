package org.ingerencia.beerapp.exception;

import org.springframework.http.HttpStatus;

public class RetrofitCallException extends RuntimeException {
    private HttpStatus httpStatus;

    public RetrofitCallException(){
    }

    public RetrofitCallException(String message) {super(message);}

    public RetrofitCallException(Throwable cause) {super(cause);}

    public RetrofitCallException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public RetrofitCallException(String message, Throwable cause){super(message, cause);}

    public RetrofitCallException(String message, HttpStatus httpStatus, Throwable cause){
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){ return this.httpStatus;}
}
